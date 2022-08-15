package com.student.service.repository;

import com.student.service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {

    List<Student> findBySpeciality(String speciality);

    Student findByFacultyNumber(String facultyNumber);
}
