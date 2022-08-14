package com.student.service.repository;

import com.student.service.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, String> {

    Speciality findByName(String name);

}
