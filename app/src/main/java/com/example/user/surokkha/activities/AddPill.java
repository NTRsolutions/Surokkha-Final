package com.example.user.surokkha.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.AlarmHandler;
import com.example.user.surokkha.db.DBHelper;
import com.example.user.surokkha.model.PillData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddPill extends AppCompatActivity {
    EditText etPill, etQty, etUnit, etDuration;
    Button insert, btnDate, time1, time2, time3, time4;
    Spinner spNo, spFrequency;
    Switch swActive;
    private static final int Date_id = 0;
    private static final int Time_id = 1;
    String[] frequency;
    String[] reminderNo;
    String date, active = "true", days = "Everyday";
    public static String[] time = new String[4];
    int repeatNo = 1;
    int timeBtnId;
    Calendar calendar;
    int mYear, mMonth, mDay, mHour, mMinute, hour, minute;
    DBHelper dbHelper;
    public static long dateInMilis, presentTimeMillis;
    public static long[] timeInMilis = new long[4];
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(getApplicationContext());

        swActive = findViewById(R.id.swActive_add);
        etPill = (EditText) findViewById(R.id.etPN);
        etQty = (EditText) findViewById(R.id.etQty);
        etUnit = (EditText) findViewById(R.id.etUnit);
        //etDuration = (EditText) findViewById(R.id.etDuration);
        //insert = (Button) findViewById(R.id.Insert);
        btnDate = (Button) findViewById(R.id.btnStartDate);
        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);
        time4 = findViewById(R.id.time4);
        spNo = findViewById(R.id.spNo);
        //spFrequency = findViewById(R.id.spFrequency);

        //getting date time
        getDateTime();

        //Spinner Adapter
        reminderNo = getResources().getStringArray(R.array.reminderNo);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spText, reminderNo);
        spNo.setAdapter(adapter2);

        //frequency = getResources().getStringArray(R.array.frequency);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spText, frequency);
        //spFrequency.setAdapter(adapter);

        //set switch value
        swActive.setChecked(Boolean.parseBoolean(active));
        swActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    active = "true";
                } else active = "false";
            }
        });

        //Spinner onItemSelect
        spNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                repeatNo = Integer.parseInt(spNo.getSelectedItem().toString());
                switch (repeatNo) {
                    case 1:
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.GONE);
                        time3.setVisibility(View.GONE);
                        time4.setVisibility(View.GONE);
                        break;
                    case 2:
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.VISIBLE);
                        time3.setVisibility(View.GONE);
                        time4.setVisibility(View.GONE);
                        break;
                    case 3:
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.VISIBLE);
                        time3.setVisibility(View.VISIBLE);
                        time4.setVisibility(View.GONE);
                        break;
                    case 4:
                        time1.setVisibility(View.VISIBLE);
                        time2.setVisibility(View.VISIBLE);
                        time3.setVisibility(View.VISIBLE);
                        time4.setVisibility(View.VISIBLE);
                        break;
                }

                getDateTime();
                Log.d("Count Check", String.valueOf(repeatNo));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
/*
        //Spinner onItemSelect
        spFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selected = spFrequency.getSelectedItemPosition();
                switch (selected) {
                    case 0:
                        days = "Everyday";
                        break;
                    case 1:
                        break;
                }
                Log.d("Day Check", String.valueOf(selected));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
        //Date button click
        btnDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // ShowPill Date dialog
                showDialog(Date_id);
            }
        });
    }

    //Time Button click
    public void timeDialogue(View view) {
        timeBtnId = view.getId();

        // ShowPill Date dialog
        showDialog(Time_id);
    }

    //Method for showing Dialogue
    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(AddPill.this, date_listener, mYear,
                        mMonth, mDay);

            case Time_id:
                if (timeBtnId == R.id.time1) {
                    //splitting time into parts
                    String[] timeParts = time[0].split(":");
                    hour = Integer.parseInt(timeParts[0]);
                    minute = Integer.parseInt(timeParts[1]);
                } else if (timeBtnId == R.id.time2) {
                    //splitting time into parts

                    if (repeatNo >= 2) {
                        String[] timeParts = time[1].split(":");
                        hour = Integer.parseInt(timeParts[0]);
                        minute = Integer.parseInt(timeParts[1]);
                    }
                } else if (timeBtnId == R.id.time3) {

                    if (repeatNo >= 3) {
                        //splitting time into parts
                        String[] timeParts = time[2].split(":");
                        hour = Integer.parseInt(timeParts[0]);
                        minute = Integer.parseInt(timeParts[1]);
                    }

                } else if (timeBtnId == R.id.time4) {
                    if (repeatNo == 4) {
                        //splitting time into parts
                        String[] timeParts = time[3].split(":");
                        hour = Integer.parseInt(timeParts[0]);
                        minute = Integer.parseInt(timeParts[1]);
                    }
                }
                // Open the timepicker dialog
                return new TimePickerDialog(AddPill.this, time_listener, hour,
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

            date = String.valueOf(mDay) + "-" + String.valueOf(mMonth + 1)
                    + "-" + String.valueOf(mYear);
            btnDate.setText(formatDate());
            Log.d("Date Check", date);
            Log.d("Date milis Check", String.valueOf(dateInMilis));
        }
    };
    // Time picker dialog
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            calendar = Calendar.getInstance();
            Log.d("Time Button Check", timeBtnId + " " + R.id.time1);
            switch (timeBtnId) {
                case R.id.time1:
                    mHour = hour;
                    mMinute = minute;

                    calendar.set(mYear, mMonth, mDay, hour, minute, 0);
                    Log.d("timeInMilis length", String.valueOf(timeInMilis.length));
                    timeInMilis[0] = calendar.getTimeInMillis();

                    if (minute < 10) {
                        time[0] = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                    } else {
                        time[0] = String.valueOf(hour) + ":" + String.valueOf(minute);
                    }

                    time1.setText(formatTime(mHour, mMinute));
                    break;

                case R.id.time2:
                    mHour = hour;
                    mMinute = minute;

                    calendar.set(mYear, mMonth, mDay, hour, minute, 0);
                    timeInMilis[1] = calendar.getTimeInMillis();

                    if (minute < 10) {
                        time[1] = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                    } else {
                        time[1] = String.valueOf(hour) + ":" + String.valueOf(minute);
                    }

                    time2.setText(formatTime(mHour, mMinute));
                    break;
                case R.id.time3:
                    mHour = hour;
                    mMinute = minute;

                    calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
                    timeInMilis[2] = calendar.getTimeInMillis();

                    if (minute < 10) {
                        time[2] = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                    } else {
                        time[2] = String.valueOf(hour) + ":" + String.valueOf(minute);
                    }
                    time3.setText(formatTime(mHour, mMinute));
                    break;
                case R.id.time4:
                    mHour = hour;
                    mMinute = minute;

                    calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
                    timeInMilis[3] = calendar.getTimeInMillis();

                    if (minute < 10) {
                        time[3] = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                    } else {
                        time[3] = String.valueOf(hour) + ":" + String.valueOf(minute);
                    }
                    time4.setText(formatTime(mHour, mMinute));
                    break;
            }
            //String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            //set_time.setText(time1);
            Log.d("Time milis Check", String.valueOf(timeInMilis[0]));
            Log.d("Date Check", time[0] + " " + time[1] + " " + time[2] + " " + time[3]);
        }
    };

    //For Action Bar menu
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
                addPill();
                //Toast.makeText(getApplicationContext(), "Save Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    //method for add pill
    public void addPill() {
        String pillName = etPill.getText().toString();
        int qty = Integer.parseInt(etQty.getText().toString());
        String unit = etUnit.getText().toString();
        //int duration = Integer.valueOf(etDuration.getText().toString());
        int duration = 1;
        //active = "true";
        int[] code = new int[repeatNo];

        //AddPill into Database
        for (int i = 0; i < repeatNo; i++) {
            PillData ds = new PillData(pillName, qty, unit, duration, days, date, time[i], repeatNo, active);
            Log.d("Data Check for pill", pillName + " " + qty + " " + unit + " " + duration + " " + days + " " + date + " " + repeatNo + " " + time[i]);
            code[i] = dbHelper.insertPillData(ds);
            Toast.makeText(getApplicationContext(), "Pill " + (i + 1) + " inserted for " + time[i], Toast.LENGTH_SHORT).show();
            Log.d("Code Check for pill", String.valueOf(code[i]));
        }
        Log.d("pill inserted", String.valueOf(code.length));
        //Trigger Alarm
        if (active.equals("true")) {
            for (int i = 0; i < code.length; i++) {
                getPresentTimeMillis();
                if (presentTimeMillis > timeInMilis[i]) {
                    timeInMilis[i] += 86400000;
                }
                AlarmHandler alarmHandler = new AlarmHandler();
                alarmHandler.startAlarm(AddPill.this, pillName, time[i], date, duration, timeInMilis[i], code[i], qty, unit);
                Log.d("Code Check for alarm", String.valueOf(code[i]));
                Log.d("Time for alarm", "Pill:" + code[i] + " " + String.valueOf(timeInMilis[i]));

                //Intent intent=new Intent(AddPill.this,ShowPill.class);
                //startActivity(intent);
            }
        }
    }

    //get timeMillis Now
    public void getPresentTimeMillis() {
        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the present year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        c.set(year, month, day, hour, minute, 0);
        presentTimeMillis = c.getTimeInMillis();
    }

    ////set spinner values, Date, and times
    public void getDateTime() {

        // Get the calander
        calendar = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        date = mDay + "-" + (mMonth + 1) + "-" + mYear;
        btnDate.setText(formatDate());

        switch (repeatNo) {
            case 1:
                calendar.set(mYear, mMonth, mDay, 10, 0, 0);
                timeInMilis[0] = calendar.getTimeInMillis();
                time[0] = "10:00";
                time1.setText("10:00 AM");
                break;
            case 2:
                calendar.set(mYear, mMonth, mDay, 10, 0, 0);
                timeInMilis[0] = calendar.getTimeInMillis();
                time[0] = "10:00";
                time1.setText("10:00 AM");

                calendar.set(mYear, mMonth, mDay, 13, 30, 0);
                timeInMilis[1] = calendar.getTimeInMillis();
                time[1] = "13:00";
                time2.setText("1:30 PM");
                break;
            case 3:
                calendar.set(mYear, mMonth, mDay, 10, 0, 0);
                timeInMilis[0] = calendar.getTimeInMillis();
                time[0] = "10:00";
                time1.setText("10:00 AM");

                calendar.set(mYear, mMonth, mDay, 13, 30, 0);
                timeInMilis[1] = calendar.getTimeInMillis();
                time[1] = "13:00";
                time2.setText("1:30 PM");

                calendar.set(mYear, mMonth, mDay, 17, 0, 0);
                timeInMilis[2] = calendar.getTimeInMillis();
                time[2] = "17:00";
                time3.setText("5:00 PM");
                break;
            case 4:
                calendar.set(mYear, mMonth, mDay, 10, 0, 0);
                timeInMilis[0] = calendar.getTimeInMillis();
                time[0] = "10:00";
                time1.setText("10:00 AM");

                calendar.set(mYear, mMonth, mDay, 13, 30, 0);
                timeInMilis[1] = calendar.getTimeInMillis();
                time[1] = "13:00";
                time2.setText("1:30 PM");

                calendar.set(mYear, mMonth, mDay, 17, 0, 0);
                timeInMilis[2] = calendar.getTimeInMillis();
                time[2] = "17:00";
                time3.setText("5:00 PM");

                calendar.set(mYear, mMonth, mDay, 21, 0, 0);
                timeInMilis[3] = calendar.getTimeInMillis();
                time[3] = "21:00";
                time4.setText("9:00 PM");
                break;
        }
    }

    //formate time with AM,PM for button
    public String formatTime(int hourOfDay, int minute) {
        String format, formattedTime, minutes;
        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        if (minute < 10)
            minutes = "0" + minute;
        else
            minutes = String.valueOf(minute);
        formattedTime = hourOfDay + ":" + minutes + " " + format;

        return formattedTime;
    }

    //formate date for button
    public String formatDate() {
        String formattedDate;
        SimpleDateFormat sdtf = new SimpleDateFormat("EEE, dd MMM yyyy");

        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay);
        Date now = c.getTime();
        formattedDate = sdtf.format(now);
        return formattedDate;
    }

}
