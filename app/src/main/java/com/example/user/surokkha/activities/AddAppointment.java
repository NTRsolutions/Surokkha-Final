package com.example.user.surokkha.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.AlarmHandler;
import com.example.user.surokkha.db.DBHelper;
import com.example.user.surokkha.model.AppointmentData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAppointment extends AppCompatActivity {
    EditText etDoctor, etLocation, etNote;
    Button btnInsert, btnDate, btnTime;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    String date,newDate, time, active;
    Calendar calendar;
    int mYear, mMonth, mDay, mHour, mMinute;
    DBHelper dbHelper;
    Toolbar toolbar;
    public static long dateInMilis;
    public static long timeInMilis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(getApplicationContext());

        etDoctor = (EditText) findViewById(R.id.etAppointmentDoctor);
        etLocation = (EditText) findViewById(R.id.etAppointmentLocation);
        etNote = (EditText) findViewById(R.id.etAppointmentNote);
        //         btnInsert = (Button) findViewById(R.id.btnAddAppointment);
        btnDate = (Button) findViewById(R.id.btnAppointmentDate);
        btnTime = findViewById(R.id.btnAppointmentTime);

        //set date in button
        getDateTime();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // ShowPill Date dialog
                showDialog(Date_id);
            }
        });


    }

    public void timeDialogue(View view) {
        // ShowPill Date dialog
        showDialog(Time_id);
    }

    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(AddAppointment.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(AddAppointment.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            calendar = Calendar.getInstance();
            mYear = year;
            mMonth = month;
            mDay = day;
            calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
            dateInMilis = calendar.getTimeInMillis();

            timeInMilis = calendar.getTimeInMillis();
            date = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
            //dialogue returns month number less 1 but gives right milis
            newDate= String.valueOf(mDay) + "-" + String.valueOf(mMonth+1) + "-" + String.valueOf(mYear);
            btnDate.setText(formatDate());
            Log.d("new Date Check", date);
            Log.d("new Date milis Check", String.valueOf(dateInMilis));
            Log.d("new time milis Check", String.valueOf(timeInMilis));
        }
    };

    // Time picker dialog
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            calendar = Calendar.getInstance();
            mHour = hour;
            mMinute = minute;

            calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);

            timeInMilis = calendar.getTimeInMillis();

            if (minute < 10) {
                time = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
            } else {
                time = String.valueOf(hour) + ":" + String.valueOf(minute);
            }

            btnTime.setText(formatTime(time));

            //String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            //set_time.setText(time1);
            Log.d("new Time milis Check", String.valueOf(timeInMilis));
            Log.d("Date Check", time);
        }
    };

    //For Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for toolbar arrow
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "back Button Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuSave:
                insertAppointment();
                //Toast.makeText(getApplicationContext(), "Save Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    //method for adding appointment
    public void insertAppointment() {

        if(etDoctor.getText().toString().isEmpty()){
            etDoctor.setError("Enter Doctor name");
            return;
        }
        if(etLocation.getText().toString().isEmpty()){
            etLocation.setError("Enter Chamber name");
            return;
        }

        String doctorName = etDoctor.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        active = "true";
        int code;

        //AddPill into Database
        AppointmentData da = new AppointmentData(doctorName, location, newDate, time, note, active);
        Log.d("Appoint Data Check", doctorName + " " + location + " " + date + " " + time + " " + active);
        code = dbHelper.insertAppointmentData(da);
        Toast.makeText(getApplicationContext(), "Appointment " + code + " inserted for " + time, Toast.LENGTH_SHORT).show();
        Log.d("Appoint Code Check", String.valueOf(code));

        //Trigger Alarm
        AlarmHandler alarmHandler = new AlarmHandler();
        alarmHandler.startAppointmentAlarm(AddAppointment.this, doctorName,location,time, timeInMilis, (code + 10000));
        Log.d("Code Check for alarm", String.valueOf(code + 10000));
        Log.d("Time for alarm", String.valueOf(timeInMilis));
        Intent intent = new Intent(AddAppointment.this, ShowAppointment.class);
        startActivity(intent);
        finish();
    }

    ////set Date, and times
    public void getDateTime() {
        // Get the calander
       calendar = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);;

        calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);

        timeInMilis = calendar.getTimeInMillis();
        time = String.valueOf(mHour) + ":" + String.valueOf(mMinute);
        btnTime.setText(formatTime(time));

        newDate = mDay + "-" + (mMonth+1) + "-" + mYear;
        btnDate.setText(formatDate());
    }

    //formate time with AM,PM for button
    public String formatTime(String time) {
        String format, formattedTime, minutes;
        String[] dateParts = time.split(":");
        int hour = Integer.parseInt(dateParts[0]);
        int minute = Integer.parseInt(dateParts[1]);
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        if (minute < 10)
            minutes = "0" + minute;
        else
            minutes = String.valueOf(minute);
        formattedTime = hour + ":" + minutes + " " + format;

        return formattedTime;
    }

    //formate date for button
    public String formatDate() {
        String formattedDate;
        SimpleDateFormat sdtf = new SimpleDateFormat("EEE, dd MMM yyyy");

        Calendar c = Calendar.getInstance();
        c.set(mYear,mMonth,mDay);
        Date now = c.getTime();
        formattedDate = sdtf.format(now);
        return formattedDate;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ShowAppointment.class));
        finish();
    }
}
