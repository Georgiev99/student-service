package com.student.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "specialities")
public class Speciality {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "speciality_degree")
    private String degree;

    @Column(name = "semesters")
    private int semesters;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "plan")
    public int plan;

    public Speciality(){

    }

    public Speciality(String name, String degree, int semesters, String faculty, int capacity) {
        this.name = name;
        this.degree = degree;
        this.semesters = semesters;
        this.faculty = faculty;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getSemesters() {
        return semesters;
    }

    public void setSemesters(int semesters) {
        this.semesters = semesters;
    }

    public String getFaculty() {
        return faculty;
}

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }




}
