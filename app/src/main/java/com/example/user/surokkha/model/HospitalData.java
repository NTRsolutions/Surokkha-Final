package com.example.user.surokkha.model;

public class HospitalData {
    private int code;
    private String hospitalName;
    private String location;
    private String phone1;
    private String phone2;

    public HospitalData(int code, String hospitalName, String location, String phone1) {
        this.code = code;
        this.hospitalName = hospitalName;
        this.location = location;
        this.phone1 = phone1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
