package com.example.user.surokkha.model;

/**
 * Created by User on 3/21/2018.
 */

public class AppointmentData {
    private String doctorName;
    private String location;
    private String date;
    private String time;
    private String note;
    private String active;
    private int code;

    public AppointmentData(String doctorName, String location, String date, String time, String note, String active) {
        this.doctorName = doctorName;
        this.location = location;
        this.date = date;
        this.time = time;
        this.note = note;
        this.active=active;
    }
    public AppointmentData(int code, String doctorName, String location, String date, String time, String note, String active) {
        this.doctorName = doctorName;
        this.location = location;
        this.date = date;
        this.time = time;
        this.note = note;
        this.active=active;
        this.code=code;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
