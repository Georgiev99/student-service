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

        public Map<String, Object> getStudentInformation (String EGN, String facultyNumber){
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

        public List<Admin> getAdmins () {
            return adminRepository.findAll();
        }
    }
