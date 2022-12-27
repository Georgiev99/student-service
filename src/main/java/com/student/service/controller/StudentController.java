package com.student.service.controller;

import com.student.service.repository.SpecialityRepository;
import com.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @Autowired
    private SpecialityRepository specialityRepository;



    @GetMapping("/info")
    public Map<String,Object> getStudentData(@RequestParam String EGN, @RequestParam String facultyNumber) {
        if (EGN != null && facultyNumber != null) {
            return studentService.getStudentInformation(EGN,facultyNumber);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create")
    public String createStudent(@RequestBody Map<String,Object> json){
        return studentService.createStudent(json);
    }

}
