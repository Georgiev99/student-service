package com.student.service.controller;

import com.student.service.entity.Admin;
import com.student.service.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class AdminPaswordHash {
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Scheduled(cron = "0 * * * * *")
//    public void hashPasswords() {
//        List<Admin> listOfAdmins = adminRepository.findAll();
//        System.out.println(new Date());
//        for (Admin admin : listOfAdmins) {
//            if (!admin.getPass().contains("$")) {
//                String generatedSecuredPasswordHash =
//                        BCrypt.hashpw(admin.getPass(), BCrypt.gensalt(12));
//                admin.setPass(generatedSecuredPasswordHash);
//                adminRepository.save(admin);
//            }
//        }
//    }
//}
//
