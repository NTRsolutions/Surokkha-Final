package com.example.user.surokkha.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.user.surokkha.db.DBHelper;
import com.example.user.surokkha.model.PillData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.ContentValues.TAG;

/**
 * Created by User on 3/11/2018.
 */

public class AlarmHandler {
    ArrayList<PillData> data = new ArrayList<>();
    long timeInMilliseconds;

    public void startAlarm(Context context, String pillName,String alarmTime,String date,int duration, long time, int code,int qty,String unit) {
        Intent aIntent = new Intent(context, AlarmReceiver.class);
        aIntent.putExtra("code", code);
        aIntent.putExtra("pillName", pillName);;
        aIntent.putExtra("unit",unit);
        aIntent.putExtra("qty",qty);
        aIntent.putExtra("time", alarmTime);
        aIntent.putExtra("date", date);
        aIntent.putExtra("duration", duration);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, code, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pIntent);
        //pIntent.cancel();
        Log.d("Code from setAlarm", String.valueOf(code));
        Log.d("time from setAlarm", String.valueOf(time));
    }

    public void cancelAlarm(Context context, int code) {
        Intent aIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, code, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
        pIntent.cancel();

        Log.d("Code from CancelAlarm", String.valueOf(code));
    }
    public void startAppointmentAlarm(Context context, String doctorName,String location,String alarmTime, long time, int code) {
        Intent aIntent = new Intent(context, AlarmReceiver.class);
        aIntent.putExtra("doctorName", doctorName);
        aIntent.putExtra("location",location);
        aIntent.putExtra("time", alarmTime);
        aIntent.putExtra("code", code);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, code, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pIntent);
        //pIntent.cancel();
        Log.d("Code from setAppoint", String.valueOf(code));
        Log.d("time from setAppoint", String.valueOf(time));
    }
    public void cancelAppointment(Context context, int code) {
        Intent aIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, code, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
        pIntent.cancel();

        Log.d("Code from CancelAlarm", String.valueOf(code));
    }

    public void startNextAlarm(Context context, String pillName) {
        DBHelper dbHelper = new DBHelper(context);
        //data=dbHelper.getNextPill(pillName,0);
        String givenDateString = "12-3-2018 20:30";
        int code = data.get(0).getCode();
        String date = data.get(0).getDate() + " " + data.get(0).getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();
            Log.d(TAG, "startNextAlarm: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent aIntent = new Intent(context, AlarmReceiver.class);
        aIntent.putExtra("pillName", pillName);
        aIntent.putExtra("code", code);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, code, aIntent, FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pIntent);

        Log.d("milis from startNextAla", String.valueOf(timeInMilliseconds));
        Log.d("date from startNextAla", date);
        Log.d("code from startNextAla", String.valueOf(code));

    }

}
