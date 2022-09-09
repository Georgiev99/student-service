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

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> addStudent(@RequestHeader("Authorization") String authentication,
                                                          @RequestBody Map<String, String> json) {
        Map<String, Object> map = new HashMap<>();
        map.put("Error ", "Unauthorized");
        if (baseConfig.authorize(authentication)) {
            Map<String, Object> mapOfResultFromCreation = studentService.createStudent(json);
            if (mapOfResultFromCreation.containsKey("Error")) {
                return ResponseEntity.badRequest().body(mapOfResultFromCreation);
            }
            return ResponseEntity.ok(mapOfResultFromCreation);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getAllStudentsByDegree(@RequestHeader("Authorization") String authentication,
                                                                @RequestParam String degree) {
        Map<String, Object> map = new HashMap<>();
        if (baseConfig.authorize(authentication)) {
            map.put("Студенти ",studentService.getAllStudents(degree));
            return ResponseEntity.ok(map);
        }
        map.put("Error ", "Unauthorized");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);

    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getStudentData(@RequestParam String EGN, @RequestParam String facultyNumber) {
        if (EGN != null && facultyNumber != null) {
            return ResponseEntity.ok(studentService.getStudentInformation(EGN, facultyNumber));
        }
        return ResponseEntity.badRequest().body(null);
    }


}
