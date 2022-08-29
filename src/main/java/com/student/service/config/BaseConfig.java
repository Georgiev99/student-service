package com.student.service.config;


import com.student.service.entity.Admin;
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<Admin> admins = studentService.getAdmins();
        for (Admin  admin : admins) {
            auth.inMemoryAuthentication()
                    .withUser(admin.getEmail()).password(passwordEncoder().encode(admin
            .getPass()))
                    .authorities("ROLE_USER");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/securityNone").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
