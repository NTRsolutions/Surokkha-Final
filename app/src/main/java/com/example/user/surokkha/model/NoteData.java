package com.example.user.surokkha.model;

/**
 * Created by User on 3/22/2018.
 */

public class NoteData {
    private int id;
    private String title;
    private  String note;
    private String date;
    private String time;

    public NoteData(int id, String title, String note, String date, String time) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.time = time;
    }
    public NoteData(String title, String note, String date, String time) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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




}
