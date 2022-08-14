package com.student.service.controller;

import com.student.service.entity.Speciality;
import com.student.service.entity.SpecialityPlan;
import com.student.service.entity.Student;
import com.student.service.repository.SpecialityPlanRepository;
import com.student.service.repository.SpecialityRepository;
import com.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public Student addStudent(@RequestBody Map<String,String> json){
       return studentService.createStudent(json);
    }


    @GetMapping("/test")
    public List<Speciality> test(){
        return specialityRepository.findAll();
    }
}
