package com.student.service.service;

import com.student.service.entity.Grades;
import com.student.service.entity.Speciality;
import com.student.service.entity.Student;
import com.student.service.repository.GradeRepository;
import com.student.service.repository.SpecialityRepository;
import com.student.service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Struct;
import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    public Map<String,Object> createStudent(Map<String, String> mapOfStudentData) {
        Map<String, Object> responseForBadRequest = new HashMap<>();
        if (mapOfStudentData.containsKey("firstName") && mapOfStudentData.containsKey("lastName")
                && mapOfStudentData.containsKey("speciality") && mapOfStudentData.containsKey("TIN")
        ) {
            List<Student> listOfStudents = studentRepository.findAll();
            for (int i = 0; i < listOfStudents.size(); i++) {
                if (listOfStudents.get(i).getTIN().equals(mapOfStudentData.get("TIN"))) {
                    responseForBadRequest.put("Error", "Student exists");
                    return responseForBadRequest;
                }
            }
            Student student = new Student();
            student.setFirstName(mapOfStudentData.get("firstName"));
            student.setLastName(mapOfStudentData.get("lastName"));
            student.setSpeciality(mapOfStudentData.get("speciality"));
            student.setTIN((mapOfStudentData.get("TIN")));
            Speciality speciality = specialityRepository.findByName(mapOfStudentData.get("speciality"));
            if (speciality == null) {
                responseForBadRequest.put("Error", "Speciality not found");
                return responseForBadRequest;
            }
            if (studentRepository.findBySpeciality(speciality.getName()).size() >= speciality.getCapacity()) {
                responseForBadRequest.put("Error", "Speciality capacity reached");
                return responseForBadRequest;
            }
            return createEmailAndFacultyNumber(speciality, student);

            // TODO  generate faculty number based on random numbers check if it exists already if not,use it
            //TODO save the record in the database


        }
        responseForBadRequest.put("Error", "Wrong input");
        return responseForBadRequest;
    }

    private Map<String,Object> createEmailAndFacultyNumber(Speciality speciality, Student student) {
        int numberForEmail = studentRepository.findBySpeciality(speciality.getName()).size() + 1;
        String email = student.getFirstName().toLowerCase() + "." + student.getLastName()
                .toLowerCase() + numberForEmail + "@" + "technicaluni.com";
        student.setEmail(email);
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        String facultyNumberDigits = "0123456789";
        while (true) {
            for (int i = 0; i < 8; i++) {
                sb.append(facultyNumberDigits.charAt(rnd.nextInt(facultyNumberDigits.length())));
            }
            Student studentByFacultyNumber = studentRepository.findByFacultyNumber(sb.toString());
            if (studentByFacultyNumber == null) {
                break;
            }
            sb = new StringBuilder();
        }
        student.setFacultyNumber(sb.toString());
        studentRepository.save(student);
        Map<String,Object> result  = new HashMap<>();
        result.put("Created",student.toString());
        return result;

    }

    public List<Student> getAllStudents(String degree) {
        List<Student> listOfStudents  = new ArrayList<>();
        List<Student> listWithAll = studentRepository.findAll();
        for (int i =0; i < listWithAll.size();i++){
            Speciality speciality = specialityRepository.findByName(listWithAll.get(i).getSpeciality());
            if (speciality.getDegree().equals(degree)){
                listOfStudents.add(listWithAll.get(i));
            }
        }
        return listOfStudents;
    }

    public Map<String,Object> getStudentInformation(String TIN, String facultyNumber){
        Student student =  studentRepository.findByFacultyNumber(facultyNumber);
        Map<String,Object> errorResponse  = new HashMap<>();
        if (student == null){
            errorResponse.put("Грешка", "не е намерен студент с този факултетен номер");
            return errorResponse;
        }
        if (student.getTIN().equals(TIN)){
            Map<String,Object> response  = new HashMap<>();
            Map<String,Object> mapOfSubjectAndGrade = new HashMap<>();
            List<Grades> listOfGrades  = gradeRepository.findByStudentFacultyNumber(facultyNumber);
            for (int i = 0; i< listOfGrades.size();i++){
                mapOfSubjectAndGrade.put(listOfGrades.get(i).getSubject(),
                        listOfGrades.get(i).getGrade());
            }
            mapOfSubjectAndGrade.put("Специалност",student.getSpeciality());
            Speciality speciality = specialityRepository.findByName(student.getSpeciality());
            mapOfSubjectAndGrade.put("Степен на образование",speciality.getDegree());
            response.put("Студент с факултетен номер "+facultyNumber,mapOfSubjectAndGrade);
            return response;
        } else {
            errorResponse.put("Грешка","Грешно ЕГН");
            return errorResponse;
        }

    }
    
}
