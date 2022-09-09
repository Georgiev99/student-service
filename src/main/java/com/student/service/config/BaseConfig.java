package com.student.service.config;

import com.student.service.entity.Admin;
import com.student.service.service.StudentService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseConfig {

    @Autowired
    private StudentService studentService;

    public boolean authorize(String authenticationHeader){
        if (authenticationHeader.isEmpty() || authenticationHeader == null || authenticationHeader.length()< 6){
            return false;
        }
        String pair = new String(Base64.decodeBase64(authenticationHeader.substring(6)));
        System.out.println(pair);
        String userName = pair.split(":")[0];
        String password = pair.split(":")[1];
        List<Admin> list = studentService.getAdmins();
        for (Admin admin : list) {
            if (admin.getEmail().equals(userName) && admin.getPass().equals(password)){
                return true;
            }
    }
        return false;
}
}
