package com.example.user.surokkha.model;

import com.example.user.surokkha.activities.ShowDoctor;

import java.util.ArrayList;

/**
 * Created by User on 3/26/2018.
 */

public class DoctorData {
    private int code;
    private String doctorName;
    private String speciality;
    private String degree;
    private String chamber;
    private String location;
    private String visitingTime;
    private String phone1;
    private String phone2;

    public DoctorData(int code, String doctorName, String speciality, String degree, String chamber, String location, String visitingTime, String phone1, String phone2) {
        this.code = code;
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.degree = degree;
        this.chamber = chamber;
        this.location = location;
        this.visitingTime = visitingTime;
        this.phone1 = phone1;
        this.phone2 = phone2;
    }

    public DoctorData(int code, String doctorName, String speciality, String degree, String chamber, String location) {
        this.code = code;
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.degree = degree;
        this.chamber = chamber;
        this.location = location;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVisitingTime() {
        return visitingTime;
    }

    public void setVisitingTime(String visitingTime) {
        this.visitingTime = visitingTime;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

}
