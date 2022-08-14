package com.student.service.repository;

import com.student.service.entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository  extends JpaRepository<Grades,Integer> {
}
