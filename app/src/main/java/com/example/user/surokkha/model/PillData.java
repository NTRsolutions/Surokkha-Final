package com.example.user.surokkha.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class PillData {

    private String pillName;
    private int qty;
    private String unit;
    private String date;
    private int duration;
    private String time;
    private String day;
    private String repeat;
    private int repeatNo;
    private String active;
    private int code;

    public PillData(String pillName, String date, String time, int code) {
        this.pillName = pillName;
        this.date = date;
        this.time = time;
        this.code = code;
    }
    public PillData(String pillName, int qty, String unit, int duration, String day, String date, String time, int repeatNo, String active) {
        this.pillName = pillName;
        this.qty = qty;
        this.unit = unit;
        this.date = date;
        this.duration = duration;
        this.time = time;
        this.day = day;
        this.repeatNo = repeatNo;
        this.active = active;
    }

    public PillData(int code, String pillName, int qty, String unit, int duration, String day, String date, String time, int repeatNo, String active) {
        this.code=code;
        this.pillName = pillName;
        this.qty = qty;
        this.unit = unit;
        this.date = date;
        this.duration = duration;
        this.time = time;
        this.day = day;
        this.repeatNo = repeatNo;
        this.active = active;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public int getRepeatNo() {
        return repeatNo;
    }

    public void setRepeatNo(int repeatNo) {
        this.repeatNo = repeatNo;
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