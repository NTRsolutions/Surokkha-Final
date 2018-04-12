package com.example.user.surokkha.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.surokkha.model.AppointmentData;
import com.example.user.surokkha.model.NoteData;
import com.example.user.surokkha.model.PillData;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 6/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "mydb.db";
    private static String TABLE_NAME_Pill = "pill";
    private static String TABLE_NAME_Appointment = "appointment";
    private static String TABLE_NAME_Note= "note";
    private static String ID = "id";
    private static String PillName = "pillName";
    private static String Qty = "qty";
    private static String Unit = "unit";
    private static String Duration = "duration";
    private static String Day = "day";
    private static String Date = "date";
    private static String Time = "time";
    private static String RepeatNo = "repeatNo";
    private static String Active = "active";

    private static String Appointment_DoctorName = "doctorName";
    private static String Appointment_Location = "location";
    private static String Appointment_Note = "appointmentNote";

    private static String Note_Title = "noteTitle";
    private static String Note_Body = "noteBody";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME_Pill + " ( " + ID + " integer primary key, " + PillName + " text not null, " + Qty + " int not null, " + Unit + " text not null, " + Duration + " int not null," + Day + " text not null," + Date + " date not null, " + Time + " time not null, " + RepeatNo + " int not null, " + Active + " text not null)";
        db.execSQL(query);
        String query2 = "create table " + TABLE_NAME_Appointment + " ( " + ID + " integer primary key, " + Appointment_DoctorName + " text not null, " + Appointment_Location + " text not null, " + Date + " date not null, " + Time + " time not null," + Appointment_Note + " text not null," + Active + " text not null)";
        db.execSQL(query2);
        String query3 = "create table " + TABLE_NAME_Note + " ( " + ID + " integer primary key, " + Note_Title + " text not null, " + Note_Body + " text not null, " + Date + " date not null, " + Time + " time not null)";
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = " Drop table if exists " + TABLE_NAME_Pill;
        db.execSQL(query);
        String query2 = " Drop table if exists " + TABLE_NAME_Appointment;
        db.execSQL(query2);
        String query3 = " Drop table if exists " + TABLE_NAME_Note;
        db.execSQL(query3);
        onCreate(db);
    }

    public int insertPillData(PillData ds) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PillName, ds.getPillName());
        values.put(Qty, ds.getQty());
        values.put(Unit, ds.getUnit());
        values.put(Duration, ds.getDuration());
        values.put(Day, ds.getDay());
        values.put(Date, ds.getDate());
        values.put(Time, ds.getTime());
        values.put(RepeatNo, ds.getRepeatNo());
        values.put(Active, ds.getActive());

        long id = sd.insert(TABLE_NAME_Pill, null, values);
        sd.close();
        Log.d(TAG, String.valueOf(id));
        return (int) id;
    }

    public void updatePillData(PillData ds) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();
        int code=ds.getCode();
        values.put(PillName, ds.getPillName());
        values.put(Qty, ds.getQty());
        values.put(Unit, ds.getUnit());
        values.put(Duration, ds.getDuration());
        values.put(Day, ds.getDay());
        values.put(Date, ds.getDate());
        values.put(Time, ds.getTime());
        values.put(RepeatNo, ds.getRepeatNo());
        values.put(Active, ds.getActive());

        sd.update(TABLE_NAME_Pill, values,"id=" + code, null );
        sd.close();
        Log.d(TAG, String.valueOf(code));
    }

    public ArrayList<PillData> showPill() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_Pill + " ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<PillData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String pillName = cur.getString(1);
                int qty = cur.getInt(2);
                String unit = cur.getString(3);
                int duration = cur.getInt(4);
                String day = cur.getString(5);
                String date = cur.getString(6);
                String time = cur.getString(7);
                int repeatNo = cur.getInt(8);
                String active = cur.getString(9);
                //int code = cur.getInt(8);
                data.add(new PillData(code,pillName, qty, unit, duration, day, date,time,repeatNo,active));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }
   public ArrayList<PillData> getPillInfo(String pillName){
        ArrayList<PillData> data=new ArrayList<>();

        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_Pill + " where pillName='"+pillName+"' order by id";
        Cursor cur = sd.rawQuery(query, null);
        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                pillName = cur.getString(1);
                int qty = cur.getInt(2);
                String unit = cur.getString(3);
                int duration = cur.getInt(4);
                String day = cur.getString(5);
                String date = cur.getString(6);
                String time = cur.getString(7);
                int repeatNo = cur.getInt(8);
                String active = cur.getString(9);
                data.add(new PillData(code,pillName, qty, unit, duration, day, date,time,repeatNo,active));

                Log.d(TAG, String.valueOf(code));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    public ArrayList<PillData> getPillInfoById(int id){
        ArrayList<PillData> data=new ArrayList<>();

        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_Pill + " where id='"+id+"'";
        Cursor cur = sd.rawQuery(query, null);
        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String pillName = cur.getString(1);
                int qty = cur.getInt(2);
                String unit = cur.getString(3);
                int duration = cur.getInt(4);
                String day = cur.getString(5);
                String date = cur.getString(6);
                String time = cur.getString(7);
                int repeatNo = cur.getInt(8);
                String active = cur.getString(9);
                data.add(new PillData(code,pillName, qty, unit, duration, day, date,time,repeatNo,active));

                Log.d(TAG, String.valueOf(code));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    public void deleteAlarm(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = " Delete from " + TABLE_NAME_Pill + " where " + ID + " = '" + id + "'";
        db.execSQL(query);
        db.close();
    }

    public int insertAppointmentData(AppointmentData da) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Appointment_DoctorName, da.getDoctorName());
        values.put(Appointment_Location, da.getLocation());
        values.put(Date, da.getDate());
        values.put(Time, da.getTime());
        values.put(Appointment_Note, da.getNote());
        values.put(Active, da.getActive());

        long id = sd.insert(TABLE_NAME_Appointment, null, values);
        sd.close();
        Log.d(TAG, String.valueOf(id));
        return (int) id;
    }
    public ArrayList<AppointmentData> showAppointment() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_Appointment + " ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<AppointmentData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String doctorName = cur.getString(1);
                String location = cur.getString(2);
                String date = cur.getString(3);
                String time = cur.getString(4);
                String note = cur.getString(5);
                String active = cur.getString(6);
                data.add(new AppointmentData(code,doctorName,location,date,time,note,active));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }
    public ArrayList<AppointmentData> getAppointmentInfo(int id) {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_Appointment + " where id='"+id+"' ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<AppointmentData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String doctorName = cur.getString(1);
                String location = cur.getString(2);
                String date = cur.getString(3);
                String time = cur.getString(4);
                String note = cur.getString(5);
                String active = cur.getString(6);
                data.add(new AppointmentData(code,doctorName,location,date,time,note,active));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }
    public void updateAppointment(AppointmentData da) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();
        int code=da.getCode();
        values.put(Appointment_DoctorName, da.getDoctorName());
        values.put(Appointment_Location, da.getLocation());
        values.put(Date, da.getDate());
        values.put(Time, da.getTime());
        values.put(Appointment_Note, da.getNote());
        values.put(Active, da.getActive());

        long id = sd.update(TABLE_NAME_Appointment, values,"id=" + code, null );
        sd.close();
        Log.d(TAG, String.valueOf(id));
    }

    public void deleteAppointment(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = " Delete from " + TABLE_NAME_Appointment + " where " + ID + " = '" + id + "'";
        db.execSQL(query);
        db.close();
    }

    public int addNote(NoteData dn) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note_Title, dn.getTitle());
        values.put(Note_Body, dn.getNote());
        values.put(Date, dn.getDate());
        values.put(Time, dn.getTime());

        long id = sd.insert(TABLE_NAME_Note, null, values);
        sd.close();
        Log.d(TAG, String.valueOf(id));
        return (int) id;
    }

    public ArrayList<NoteData> showNote() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_Note + " ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<NoteData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String title = cur.getString(1);
                String note = cur.getString(2);
                String date = cur.getString(3);
                String time = cur.getString(4);
                data.add(new NoteData(code,title,note,date,time));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    public void updateNote(NoteData dn) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();
        int id=dn.getId();
        values.put(Note_Title, dn.getTitle());
        values.put(Note_Body, dn.getNote());
        values.put(Date, dn.getDate());
        values.put(Time, dn.getTime());

        sd.update(TABLE_NAME_Note, values,"id=" + id, null );
        sd.close();
        Log.d(TAG, "ide from update note: "+String.valueOf(id));
    }
    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = " Delete from " + TABLE_NAME_Note + " where " + ID + " = '" + id + "'";
        db.execSQL(query);
        db.close();
    }
    public void deleteData(String date) {
        SQLiteDatabase db = getWritableDatabase();
        String query = " Delete from " + TABLE_NAME_Pill + " where " + Date + " < '" + date + "'";
        db.execSQL(query);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        String query = " Delete from " + TABLE_NAME_Pill;
        db.execSQL(query);
        db.close();
    }

    public int getPillCode() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select count(distinct pillName) from " + TABLE_NAME_Pill + " ";
        Cursor cur = sd.rawQuery(query, null);
        cur.moveToFirst();
        int code = 0;
        code = Integer.parseInt(cur.getString(0));

        cur.close();
        Log.d(TAG, String.valueOf(code));
        return code+1;
    }

}
