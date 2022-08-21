package com.student.service.repository;

import com.student.service.entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository  extends JpaRepository<Grades,Integer> {

    List<Grades> findByStudentFacultyNumber(String facultyNumber);
}
