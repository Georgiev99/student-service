package com.student.service.controller;

import com.student.service.entity.Speciality;
import com.student.service.entity.SpecialityPlan;
import com.student.service.entity.Student;
import com.student.service.repository.SpecialityPlanRepository;
import com.student.service.repository.SpecialityRepository;
import com.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String,Object>> addStudent(@RequestBody Map<String,String> json){
       Map<String,Object> mapOfResultFromCreation = studentService.createStudent(json);
       if (mapOfResultFromCreation.containsKey("Error")){
           return ResponseEntity.badRequest().body(mapOfResultFromCreation);
       }
       return ResponseEntity.ok(mapOfResultFromCreation);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Student>>getAllStudentsByDegree(@RequestParam String degree){
        return ResponseEntity.ok(studentService.getAllStudents(degree));
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String,Object>> getStudentData(@RequestParam String TIN,@RequestParam String facultyNumber){
        if (TIN != null && facultyNumber != null){
           return ResponseEntity.ok(studentService.getStudentInformation(TIN,facultyNumber));
        }
        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteStudent(@RequestParam String facultyNumber){
        Map<String,String> response = studentService.deleteStudent(facultyNumber);
        if (response.containsKey("Грешка")){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

}
