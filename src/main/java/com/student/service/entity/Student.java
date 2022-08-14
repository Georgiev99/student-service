package com.student.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student {


    @Id
    @Column(name = "faculty_number")
    private String facultyNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "email")
    private String email;

    @Column(name = "tin")
    private String TIN;


    public Student(){

    }



    public Student(String facultyNumber, String firstName, String lastName, String speciality,String email,String TIN) {
        this.facultyNumber = facultyNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.email = email;
        this.TIN = TIN;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
