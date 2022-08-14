package com.student.service.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "speciality_plan")
public class SpecialityPlan {

    @Id
    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "subjects")
    private String subjects;

    public SpecialityPlan() {

    }

    public SpecialityPlan(String facultyName, String degree, String subjects) {
        this.facultyName = facultyName;
        this.degree = degree;
        this.subjects = subjects;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

}
