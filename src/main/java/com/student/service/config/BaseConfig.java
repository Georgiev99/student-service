package com.student.service.config;


import com.student.service.entity.Admin;
import com.student.service.entity.Student;
import com.student.service.repository.StudentRepository;
import com.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.swing.text.AbstractDocument;
import java.util.List;

@Configuration
@EnableWebSecurity
public class BaseConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<Admin> admins = studentService.getAdmins();
        for (Admin  admin : admins) {
            auth.inMemoryAuthentication()
                    .withUser(admin.getEmail()).password(passwordEncoder().encode(admin
            .getPass()))
                    .authorities("ROLE_ADMIN");
        }
        List<Student> studentList = studentRepository.findAll();
        for (Student student : studentList){
            auth.inMemoryAuthentication()
                    .withUser(student.getFacultyNumber()).password(passwordEncoder().encode(student
                    .getTIN()))
                    .authorities("ROLE_USER");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/student/info").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/student/all").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/api/student/create").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/api/student/delete").access("hasRole('ROLE_ADMIN')")
    .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
  public  PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
