package com.student.service.service;

import com.student.service.entity.Admin;
import com.student.service.entity.Grades;
import com.student.service.entity.Speciality;
import com.student.service.entity.Student;
import com.student.service.repository.AdminRepository;
import com.student.service.repository.GradeRepository;
import com.student.service.repository.SpecialityRepository;
import com.student.service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Map<String, Object> createStudent(Map<String, String> mapOfStudentData) {
        Map<String, Object> responseForBadRequest = new HashMap<>();
        if (mapOfStudentData.containsKey("firstName") && mapOfStudentData.containsKey("lastName")
                && mapOfStudentData.containsKey("speciality") && mapOfStudentData.containsKey("EGN")
        ) {
            List<Student> listOfStudents = studentRepository.findAll();
            for (Student student : listOfStudents){
                if (student.getEGN().equals("EGN")) {
                    responseForBadRequest.put("Грешка", "Студента вече е записан");
                    return responseForBadRequest;
                }
            }
            Student student = new Student();
            student.setFirstName(mapOfStudentData.get("firstName"));
            student.setLastName(mapOfStudentData.get("lastName"));
            student.setSpeciality(mapOfStudentData.get("speciality"));
            student.setEGN((mapOfStudentData.get("EGN")));
            Speciality speciality = specialityRepository.findByName(mapOfStudentData.get("speciality"));
            if (speciality == null) {
                responseForBadRequest.put("Грешка", "Не е намерена такава специалност");
                return responseForBadRequest;
            }
            if (studentRepository.findBySpeciality(speciality.getName()).size() >= speciality.getCapacity()) {
                responseForBadRequest.put("Грешка", "Капацитета на специалността е достигнат");
                return responseForBadRequest;
            }
            return createEmailAndFacultyNumber(speciality, student);

        }
        responseForBadRequest.put("Грешка", "Грешни входни данни");
        return responseForBadRequest;
    }

    private Map<String, Object> createEmailAndFacultyNumber(Speciality speciality, Student student) {
        int numberForEmail = studentRepository.findBySpeciality(speciality.getName()).size() + 1;
        String email = student.getFirstName().toLowerCase() + "." + student.getLastName()
                .toLowerCase() + numberForEmail + "@" + "tu-sofia.com";
        student.setEmail(email);
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        String facultyNumberDigits = "0123456789";
        while (true) {
            if (sb.length() < 8) {
                sb.append(facultyNumberDigits.charAt(rnd.nextInt(facultyNumberDigits.length())));
            } else {
                Student studentByFacultyNumber = studentRepository.findByFacultyNumber(sb.toString());
                if (studentByFacultyNumber == null) {
                    break;
                }
                sb = new StringBuilder();
            }
        }
        student.setFacultyNumber(sb.toString());
        studentRepository.save(student);
        Map<String, Object> result = new HashMap<>();
        result.put("Записан успешно", student.getEmail() + " "+ student.getEGN());
        return result;

    }

    public List<Student> getAllStudents(String degree) {
        List<Student> listOfStudents = new ArrayList<>();
        List<Student> listWithAll = studentRepository.findAll();
        for (Student student : listWithAll){
            Speciality studentSpeciality = specialityRepository.findByName(student.getSpeciality());
            if (studentSpeciality.getDegree().equals(degree)){
                listOfStudents.add(student);
        }
        }
        return listOfStudents;
    }

    public Map<String, Object> getStudentInformation(String EGN, String facultyNumber) {
        Student student = studentRepository.findByFacultyNumber(facultyNumber);
        Map<String, Object> errorResponse = new HashMap<>();
        if (student == null) {
            errorResponse.put("Грешка", "не е намерен студент с този факултетен номер");
            return errorResponse;
        }
        if (student.getEGN().equals(EGN)) {
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> mapOfSubjectAndGrade = new HashMap<>();
            List<Grades> listOfGrades = gradeRepository.findByStudentFacultyNumber(facultyNumber);
            for (Grades i : listOfGrades) {
                mapOfSubjectAndGrade.put(i.getSubject(),
                        i.getGrade());
            }
            mapOfSubjectAndGrade.put("Специалност", student.getSpeciality());
            mapOfSubjectAndGrade.put("Мейл", student.getEmail());
            Speciality speciality = specialityRepository.findByName(student.getSpeciality());
            mapOfSubjectAndGrade.put("Степен на образование", speciality.getDegree());
            response.put("Студент с факултетен номер " + facultyNumber, mapOfSubjectAndGrade);
            return response;
        } else {
            errorResponse.put("Грешка", "Грешно ЕГН");
            return errorResponse;
        }

    }

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }
}
