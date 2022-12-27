package com.student.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "subject_certification")
public class SubjectCertification {


    public SubjectCertification(){

    }

    public SubjectCertification(Long id, String name, boolean certification, String studentFacultyNumber) {
        this.id = id;
        this.name = name;
        this.certification = certification;
        this.studentFacultyNumber = studentFacultyNumber;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name")
    private String name;

    @Column(name = "certification")
    private boolean certification;

    @Column(name = "student_faculty_number")
    private String studentFacultyNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCertification() {
        return certification;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public String getStudentFacultyNumber() {
        return studentFacultyNumber;
    }

    public void setStudentFacultyNumber(String studentFacultyNumber) {
        this.studentFacultyNumber = studentFacultyNumber;
    }
}
