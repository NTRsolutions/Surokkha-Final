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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.AlarmHandler;
import com.example.user.surokkha.db.DBHelper;
import com.example.user.surokkha.model.AppointmentData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class EditAppointment extends AppCompatActivity {
    EditText etDoctor, etLocation, etNote;
    Button btnUpdate, btnDate, btnTime,btnDelete;
    Switch swActive;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    String doctorName,date,newDate,time, active;
    Calendar calendar;
    int code;
    int mYear, mMonth, mDay, mHour, mMinute;
    Bundle extras;
    ArrayList<AppointmentData> appointmentData = new ArrayList<>();
    public static long dateInMilis;
    public static long timeInMilis;
    DBHelper dbHelper;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(getApplicationContext());

        etDoctor = (EditText) findViewById(R.id.etEADoctor);
        etLocation = (EditText) findViewById(R.id.etEALocation);
        etNote = (EditText) findViewById(R.id.etEANote);
        //btnUpdate = (Button) findViewById(R.id.btnUpdateAppointment);
        //btnDelete=findViewById(R.id.btnDeleteAppointment);
        btnDate = (Button) findViewById(R.id.btnEADate);
        btnTime = findViewById(R.id.btnEATime);
        swActive=findViewById(R.id.swActiveAppointment);

        extras = getIntent().getExtras();
        // if (extras != null) {
        doctorName = extras.getString("doctorName");
        date = extras.getString("date");
        code=extras.getInt("code");
        active=extras.getString("active");

        //getting pill Info by Name
        appointmentData = dbHelper.getAppointmentInfo(code);
        Log.d("Obj Data Check", appointmentData.get(0).getCode() + " " + appointmentData.get(0).getDoctorName() +" "+ appointmentData.get(0).getDate() + " " + appointmentData.get(0).getTime());

        //set switch value
        swActive.setChecked(Boolean.parseBoolean(appointmentData.get(0).getActive()));
        swActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    active = "true";
                } else active = "false";
            }
        });
        //set values in fields
        setValues();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ShowPill Date dialog
                showDialog(Date_id);
            }
        });

/*
        //AddPill Button Click
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName = etDoctor.getText().toString();
                String  location= etLocation.getText().toString();
                String  note= etNote.getText().toString();

                //AddPill into Database
                AppointmentData da = new AppointmentData(code,doctorName,location,date,time,note,active);
                Log.d("Appoint Data Check", doctorName + " " + location +" "+ date + " " + time+" "+active);
                dbHelper.updateAppointment(da);
                Toast.makeText(getApplicationContext(), "Appointment "+code+" updated for "+ time, Toast.LENGTH_SHORT).show();
                Log.d("Code Check", String.valueOf(code));


                //Trigger Alarm
                if (active.equals("true")) {
                    AlarmHandler alarmHandler = new AlarmHandler();
                    alarmHandler.startAppointmentAlarm(EditAppointment.this, doctorName, timeInMilis, (code + 10000));
                    Log.d("Code Check for alarm", String.valueOf(code + 10000));
                    Log.d("Time for alarm", String.valueOf(timeInMilis));
                }
                if (active.equals("false")) {
                    AlarmHandler alarmHandler = new AlarmHandler();
                    alarmHandler.cancelAppointment(EditAppointment.this,(code + 10000));
                    Log.d("Code Check for alarm", String.valueOf(code + 10000));
                    Log.d("Time for alarm", String.valueOf(timeInMilis));
                }

                Intent intent=new Intent(EditAppointment.this,ShowAppointment.class);
                startActivity(intent);
            }
        });
        //AddPill Button Click
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AddPill into Database
                dbHelper.deleteAppointment(code);
                Toast.makeText(getApplicationContext(), "Appointment "+code+" deleted for "+ time, Toast.LENGTH_SHORT).show();
                Log.d("Code Check", String.valueOf(code));

                //Trigger Alarm
                AlarmHandler alarmHandler = new AlarmHandler();
                alarmHandler.cancelAppointment(EditAppointment.this, (code+10000));
                Log.d("Code Check for alarm", String.valueOf(code+10000));
                Log.d("Time for alarm", String.valueOf(timeInMilis));
                Intent intent=new Intent(EditAppointment.this,ShowAppointment.class);
                startActivity(intent);
            }
        });
        */
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
                return new DatePickerDialog(EditAppointment.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(EditAppointment.this, time_listener, hour,
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
            calendar.set(mYear, mMonth, mDay, mHour, mMinute,0);
            dateInMilis = calendar.getTimeInMillis();
            date = String.valueOf(mDay) + "-" + String.valueOf(mMonth)
                    + "-" + String.valueOf(mYear);
            newDate=String.valueOf(mDay) + "-" + String.valueOf(mMonth+1)
                    + "-" + String.valueOf(mYear);
            btnDate.setText(formatDate());
            Log.d("new Date Check", newDate);
            Log.d("new Date milis Check", String.valueOf(dateInMilis));
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

            calendar.set(mYear, mMonth, mDay, mHour, mMinute,0);

            timeInMilis= calendar.getTimeInMillis();

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

    public void setValues(){
        //splitting date into parts
        String[] dateParts = appointmentData.get(0).getDate().split("-");
        mDay = Integer.parseInt(dateParts[0]);
        //month is one decreased because month was 1 increased in db
        mMonth = (Integer.parseInt(dateParts[1]))-1;
        mYear = Integer.parseInt(dateParts[2]);

        //set edit text value
        etDoctor.setText(appointmentData.get(0).getDoctorName());
        etLocation.setText(appointmentData.get(0).getLocation());
        etNote.setText(appointmentData.get(0).getNote());
        date=mDay + "-" + mMonth + "-" + mYear;
        newDate = mDay + "-" + (mMonth+1) + "-" + mYear;
        btnDate.setText(formatDate());
        //get alarm time
        time = appointmentData.get(0).getTime();
        btnTime.setText(formatTime(time));

        //get time in milis
        String datetime = newDate + " " + time;
        SimpleDateFormat sdtf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            java.util.Date mDate = sdtf.parse(datetime);
            timeInMilis = mDate.getTime();
            Log.d(TAG, "date: " + datetime);
            Log.d(TAG, "NextAlarm Milis: "+timeInMilis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //For Action Bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //for toolbar arrow
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "back Button Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuSave:
                updateAppointment();
                //Toast.makeText(getApplicationContext(), "Save Button Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDelete:
                deleteAppointment();
                //Toast.makeText(getApplicationContext(), "Delete Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return  true;
    }

    public void updateAppointment() {
        String doctorName = etDoctor.getText().toString();
        String  location= etLocation.getText().toString();
        String  note= etNote.getText().toString();

        //AddPill into Database
        AppointmentData da = new AppointmentData(code,doctorName,location,newDate,time,note,active);
        Log.d("Appoint Data Check", doctorName + " " + location +" "+ newDate + " " + time+" "+active);
        dbHelper.updateAppointment(da);
        Toast.makeText(getApplicationContext(), "Appointment "+code+" updated for "+ time, Toast.LENGTH_SHORT).show();
        Log.d("Code Check", String.valueOf(code));


        //Trigger Alarm
        if (active.equals("true")) {
            AlarmHandler alarmHandler = new AlarmHandler();
            alarmHandler.startAppointmentAlarm(EditAppointment.this, doctorName,location,time, timeInMilis, (code + 10000));
            Log.d("Code Check for alarm", String.valueOf(code + 10000));
            Log.d("Time for alarm", String.valueOf(timeInMilis));
        }
        if (active.equals("false")) {
            AlarmHandler alarmHandler = new AlarmHandler();
            alarmHandler.cancelAppointment(EditAppointment.this,(code + 10000));
            Log.d("Code Check for alarm", String.valueOf(code + 10000));
            Log.d("Time for alarm", String.valueOf(timeInMilis));
        }

        Intent intent=new Intent(EditAppointment.this,ShowAppointment.class);
        startActivity(intent);
        finish();
    }

    public void deleteAppointment() {
        //AddPill into Database
        dbHelper.deleteAppointment(code);
        Toast.makeText(getApplicationContext(), "Appointment "+code+" deleted for "+ time, Toast.LENGTH_SHORT).show();
        Log.d("Code Check", String.valueOf(code));

        //Trigger Alarm
        AlarmHandler alarmHandler = new AlarmHandler();
        alarmHandler.cancelAppointment(EditAppointment.this, (code+10000));
        Log.d("Code Check for alarm", String.valueOf(code+10000));
        Log.d("Time for alarm", String.valueOf(timeInMilis));
        Intent intent=new Intent(EditAppointment.this,ShowAppointment.class);
        startActivity(intent);
        finish();
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

