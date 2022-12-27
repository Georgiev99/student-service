package com.student.service.service;


import com.student.service.entity.Grades;
import com.student.service.entity.Speciality;
import com.student.service.entity.Student;
import com.student.service.entity.SubjectCertification;
import com.student.service.repository.GradeRepository;
import com.student.service.repository.SpecialityRepository;
import com.student.service.repository.StudentRepository;
import com.student.service.repository.SubjectCertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    SubjectCertificationRepository subjectCertificationRepository;




        public Map<String, Object> getStudentInformation(String EGN, String facultyNumber){
            Student student = studentRepository.findByFacultyNumber(facultyNumber);
            Map<String, Object> errorResponse = new HashMap<>();
            if (student == null) {
                errorResponse.put("Грешка", "не е намерен студент с този факултетен номер");
                return errorResponse;
            }
            if (student.getEGN().equals(EGN)) {
                Map<String,Object> subjectsGradesAndCertifications = getSubjectCertificationsAndGrades(facultyNumber);
                subjectsGradesAndCertifications.put("Специалност", student.getSpeciality());
                subjectsGradesAndCertifications.put("Мейл", student.getEmail());
                Speciality speciality = specialityRepository.findByName(student.getSpeciality());
                subjectsGradesAndCertifications.put("Степен на образование", speciality.getDegree());
                return subjectsGradesAndCertifications;
            } else {
                errorResponse.put("Грешка", "Грешно ЕГН");
                return errorResponse;
            }

        }

        private Map<String, Object> getSubjectCertificationsAndGrades(String facultyNumber){
            Map<String, Object> mapOfSubjectAndGrade = new HashMap<>();
            List<Grades> listOfGrades = gradeRepository.findByStudentFacultyNumber(facultyNumber);
            for (Grades i : listOfGrades) {
                mapOfSubjectAndGrade.put(i.getSubject(),
                        i.getGrade());
            }
            Map<String,Object> subjectCertifications = new HashMap<>();
            List<SubjectCertification> subjectCertification = subjectCertificationRepository.findByStudentFacultyNumber(facultyNumber);
            for (int i =0 ; i < subjectCertification.size();i++){
                subjectCertifications.put(subjectCertification.get(i).getName(),subjectCertification.get(i).isCertification());
            }
            Map<String,Object> map = new HashMap<>();
            map.put("заверки",subjectCertifications);
            map.put("оценки",mapOfSubjectAndGrade);
            return map;
        }

        public String createStudent(Map<String,Object> studentData){
            if (studentData.containsKey("first_name") && studentData.containsKey("last_name")
                    &&studentData.containsKey("speciality")
                    && studentData.containsKey("email")
                    &&studentData.containsKey("egn")){
                Student findByEgn = studentRepository.findByEGN((String) studentData.get("egn"));
                if (findByEgn != null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student with "+findByEgn.getEGN());
                }
                Student student = new Student();
                student.setEmail((String) studentData.get("email"));
                student.setFirstName((String) studentData.get("first_name"));
                student.setLastName((String) studentData.get("last_name"));
                student.setEGN((String) studentData.get("egn"));
                student.setSpeciality((String) studentData.get("speciality"));
                String digits  = "0123456789";
                while (true) {
                    StringBuilder sb = new StringBuilder();
                    Random random = new Random();
                    for (int i = 0; i < 9; i++) {
                        sb.append(digits.charAt(random.nextInt(digits
                                .length())));
                    }
                    Student findStudent  = studentRepository.findByFacultyNumber(sb.toString());
                    if (findStudent == null){
                        student.setFacultyNumber(sb.toString());
                        break;
                    }
                }
                studentRepository.save(student);
            }
            return "saved";
        }

    }
