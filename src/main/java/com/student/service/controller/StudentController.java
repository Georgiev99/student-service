package com.student.service.controller;

import com.student.service.config.BaseConfig;
import com.student.service.entity.Admin;
import com.student.service.entity.Speciality;
import com.student.service.entity.SpecialityPlan;
import com.student.service.entity.Student;
import com.student.service.repository.SpecialityPlanRepository;
import com.student.service.repository.SpecialityRepository;
import com.student.service.service.StudentService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SpecialityPlanRepository specialityPlanRepository;

    @Autowired
    private BaseConfig baseConfig;


    @GetMapping("/info")
    public Map<String,Object> getStudentData(@RequestParam String EGN, @RequestParam String facultyNumber) {
        if (EGN != null && facultyNumber != null) {
            return studentService.getStudentInformation(EGN,facultyNumber);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }


}
