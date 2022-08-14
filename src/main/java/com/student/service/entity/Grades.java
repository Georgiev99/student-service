package com.student.service.entity;


import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grades {



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_faculty_number")
    private String studentFacultyNumber;

    @Column(name = "grade")
    private int grade;

    @Column(name = "subject")
    private String subject;


    public String getStudentFacultyNumber() {
        return studentFacultyNumber;
    }

    public void setStudentFacultyNumber(String studentFacultyNumber) {
        this.studentFacultyNumber = studentFacultyNumber;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
