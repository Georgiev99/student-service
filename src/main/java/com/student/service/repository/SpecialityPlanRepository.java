package com.student.service.repository;

import com.student.service.entity.SpecialityPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityPlanRepository  extends JpaRepository<SpecialityPlan,String> {
}
