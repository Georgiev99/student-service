package com.student.service.repository;

import com.student.service.entity.SubjectCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectCertificationRepository extends JpaRepository<SubjectCertification,Long> {
    List<SubjectCertification> findByStudentFacultyNumber(String facultyNumber);
}
