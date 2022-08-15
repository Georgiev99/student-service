package com.student.service.service;

import com.student.service.entity.Speciality;
import com.student.service.entity.Student;
import com.student.service.repository.SpecialityRepository;
import com.student.service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    public Student createStudent(Map<String,String> mapOfStudentData){
        if (mapOfStudentData.containsKey("firstName") && mapOfStudentData.containsKey("lastName")
        && mapOfStudentData.containsKey("speciality") && mapOfStudentData.containsKey("TIN")
        ){
            Student student = new Student();
            student.setFirstName(mapOfStudentData.get("firstName"));
            student.setLastName(mapOfStudentData.get("lastName"));
            student.setSpeciality( mapOfStudentData.get("speciality"));
            student.setTIN((mapOfStudentData.get("TIN")));
            Speciality speciality = specialityRepository.findByName(mapOfStudentData.get("speciality"));
            if (speciality == null){
              throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Speciality not found");
            }
            if (studentRepository.findBySpeciality(speciality.getName()).size() >= speciality.getCapacity()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Speciality capacity is reached!");
            }
            return createEmailAndFacultyNumber(speciality,student);

            // TODO  generate faculty number based on random numbers check if it exists already if not,use it
            //TODO save the record in the database


        }
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Input");
    }

    private Student createEmailAndFacultyNumber(Speciality speciality , Student student){
        int numberForEmail = studentRepository.findBySpeciality(speciality.getName()).size()+1;
        String email = student.getFirstName().toLowerCase()+"."+student.getLastName()
                .toLowerCase()+numberForEmail+"@"+"technicaluni.com";
        student.setEmail(email);
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        String facultyNumberDigits  = "0123456789";
        while (true) {
            for (int i = 0; i < 8; i++) {
                sb.append(facultyNumberDigits.charAt(rnd.nextInt(facultyNumberDigits.length())));
            }
          Student  studentByFacultyNumber = studentRepository.findByFacultyNumber(sb.toString());
            if (studentByFacultyNumber == null){
                break;
            }
            sb = new StringBuilder();
        }
        student.setFacultyNumber(sb.toString());
        studentRepository.save(student);
        return student;

   }

    public List<Student> getAllStudents(){

        // TODO  extend functionality and add different functions (filter for degree(bachelor/master), speciality etc.
        return studentRepository.findAll();
    }

    // add function to show all grades and subjects that a student has based on his faculty number and TIN

}
