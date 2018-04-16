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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class EditPill extends AppCompatActivity {
    EditText etPill, etQty, etUnit, etDuration;
    Button insert, btnDate, btnTime1, btnTime2, btnTime3, btnTime4, btnDelete;
    String time1, time2, time3, time4;
    Spinner spNo,spUnit, spFrequency;
    Switch swActive;
    private final int Date_id = 0;
    private final int Time_id = 1;
    Bundle extras;
    String[] reminderNo,unitArray,frequency;
    String pillName,unit, date, newDate, days="Everyday", active, notification = "false";
    public String[] time = new String[4];
    int repeatNo, newRepeatNo;
    int timeBtnId;
    Calendar calendar;
    int mYear, mMonth, mDay, mHour, mMinute, year, month, day, hour, minute;
    ArrayList<PillData> pillData = new ArrayList<>();
    ArrayAdapter<String> unitAdapter;
    public static long dateInMilis, presentTimeMillis;
    public long[] timeInMilis = new long[4];
    DBHelper dbHelper;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pill);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(getApplicationContext());

        etPill = (EditText) findViewById(R.id.etPN);
        etQty = (EditText) findViewById(R.id.etQty);
        //etUnit = (EditText) findViewById(R.id.etUnit);
        //etDuration = (EditText) findViewById(R.id.etDuration);
        swActive = findViewById(R.id.swActive);
        //insert = (Button) findViewById(R.id.Insert);
        //btnDelete = findViewById(R.id.btnDeleteAlarm);
        btnDate = (Button) findViewById(R.id.btnStartDate);
        btnTime1 = findViewById(R.id.time1);
        btnTime2 = findViewById(R.id.time2);
        btnTime3 = findViewById(R.id.time3);
        btnTime4 = findViewById(R.id.time4);
        spNo = findViewById(R.id.spNo);
        spUnit=findViewById(R.id.spUnit);
        //spFrequency = findViewById(R.id.spFrequency);

        //spinner adapter

        reminderNo = getResources().getStringArray(R.array.reminderNo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spText, reminderNo);
        spNo.setAdapter(adapter);
        //frequency = getResources().getStringArray(R.array.frequency);
        // ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spText, frequency);
        // spFrequency.setAdapter(adapter2);
        unitArray = getResources().getStringArray(R.array.unit);
        unitAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spText, unitArray);
        spUnit.setAdapter(unitAdapter);


        //getting extras
        extras = getIntent().getExtras();
        // if (extras != null) {
        pillName = extras.getString("pillName");
        repeatNo = extras.getInt("repeatNo");
        date = extras.getString("date");
        active = extras.getString("active");

        //getting pill Info by Name
        pillData = dbHelper.getPillInfo(pillName);

        //getting timepreset
        getValues(repeatNo);

        //set switch value
        swActive.setChecked(Boolean.parseBoolean(pillData.get(0).getActive()));
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
                //repeatNo = Integer.parseInt(spNo.getSelectedItem().toString());
                newRepeatNo = Integer.parseInt(spNo.getSelectedItem().toString());
                switch (newRepeatNo) {
                    case 1:
                        btnTime1.setVisibility(View.VISIBLE);
                        btnTime2.setVisibility(View.GONE);
                        btnTime3.setVisibility(View.GONE);
                        btnTime4.setVisibility(View.GONE);
                        break;
                    case 2:
                        btnTime1.setVisibility(View.VISIBLE);
                        btnTime2.setVisibility(View.VISIBLE);
                        btnTime3.setVisibility(View.GONE);
                        btnTime4.setVisibility(View.GONE);
                        break;
                    case 3:
                        btnTime1.setVisibility(View.VISIBLE);
                        btnTime2.setVisibility(View.VISIBLE);
                        btnTime3.setVisibility(View.VISIBLE);
                        btnTime4.setVisibility(View.GONE);
                        break;
                    case 4:
                        btnTime1.setVisibility(View.VISIBLE);
                        btnTime2.setVisibility(View.VISIBLE);
                        btnTime3.setVisibility(View.VISIBLE);
                        btnTime4.setVisibility(View.VISIBLE);
                        break;
                }
                Log.d("Count Check", String.valueOf(repeatNo));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit =spUnit.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        int count = extras.getInt("repeatNo");
        //calling dialogue for date and time
        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(EditPill.this, date_listener, mYear,
                        mMonth, mDay);

            case Time_id:

                if (timeBtnId == R.id.time1) {
                    //splitting time into parts
                    String[] timeParts = pillData.get(0).getTime().split(":");
                    hour = Integer.parseInt(timeParts[0]);
                    minute = Integer.parseInt(timeParts[1]);
                } else if (timeBtnId == R.id.time2) {
                    //splitting time into parts

                    if (count >= 2) {
                        String[] timeParts = pillData.get(1).getTime().split(":");
                        hour = Integer.parseInt(timeParts[0]);
                        minute = Integer.parseInt(timeParts[1]);
                    }
                } else if (timeBtnId == R.id.time3) {

                    if (count >= 3) {
                        //splitting time into parts
                        String[] timeParts = pillData.get(2).getTime().split(":");
                        hour = Integer.parseInt(timeParts[0]);
                        minute = Integer.parseInt(timeParts[1]);
                    }

                } else if (timeBtnId == R.id.time4) {
                    if (count == 4) {
                        //splitting time into parts
                        String[] timeParts = pillData.get(3).getTime().split(":");
                        hour = Integer.parseInt(timeParts[0]);
                        minute = Integer.parseInt(timeParts[1]);
                    }
                }
                Log.d("Time Button ID", String.valueOf(timeBtnId) + " " + R.id.time1);
                // Open the timepicker dialog
                return new TimePickerDialog(EditPill.this, time_listener, hour,
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
            date = String.valueOf(mDay) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mYear);
            newDate = String.valueOf(mDay) + "-" + String.valueOf(mMonth + 1) + "-" + String.valueOf(mYear);
            btnDate.setText(formatDate());
            Log.d("Date Check", newDate);
            Log.d("Date milis Check", String.valueOf(dateInMilis));
        }
    };
    // Time picker dialog
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            int count = extras.getInt("repeatNo");
            String[] timeParts;
            calendar = Calendar.getInstance();
            switch (timeBtnId) {
                case R.id.time1:
                    //timeParts = pillData.get(0).getTime().split(":");
                    //mHour = Integer.parseInt(timeParts[0]);
                    //mMinute = Integer.parseInt(timeParts[1]);
                    mHour = hour;
                    mMinute = minute;
                    Log.d("timeIn", hour + ":" + minute);

                    calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
                    Log.d("timeInMilis length", String.valueOf(timeInMilis.length));

                    timeInMilis[0] = calendar.getTimeInMillis();
                    Log.d("timeInMilis 0", String.valueOf(timeInMilis[0]));
                    if (minute < 10) {
                        time[0] = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                    } else {
                        time[0] = String.valueOf(hour) + ":" + String.valueOf(minute);
                    }

                    btnTime1.setText(formatTime(time[0]));
                    break;

                case R.id.time2:
                    if (count >= 2) {
                        //timeParts = pillData.get(1).getTime().split(":");
                        //mHour = Integer.parseInt(timeParts[0]);
                        //mMinute = Integer.parseInt(timeParts[1]);
                        mHour = hour;
                        mMinute = minute;
                    } else {
                        mHour = hour;
                        mMinute = minute;
                    }
                    calendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
                    timeInMilis[1] = calendar.getTimeInMillis();
                    Log.d("timeInMilis length", String.valueOf(timeInMilis.length));
                    Log.d("timeInMilis 1", String.valueOf(timeInMilis[1]));
                    if (minute < 10) {
                        time[1] = String.valueOf(hour) + ":" + "0" + String.valueOf(minute);
                    } else {
                        time[1] = String.valueOf(hour) + ":" + String.valueOf(minute);
                    }

                    btnTime2.setText(formatTime(time[1]));
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
                    btnTime3.setText(formatTime(time[2]));
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
                    btnTime4.setText(formatTime(time[3]));
                    break;
            }
            //String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            //set_time.setText(time1);
            //Log.d("Time milis Check", String.valueOf(timeInMilis[0]));
            Log.d("Date Check", time[0] + " " + time[1] + " " + time[2] + " " + time[3]);
        }
    };

    ////set spinner values Date, and times
    public void getValues(int repeatNo) {
        Log.d("repeat Check", String.valueOf(repeatNo));
        String[] dateParts = pillData.get(0).getDate().split("-");
        mDay = Integer.parseInt(dateParts[0]);
        mMonth = (Integer.parseInt(dateParts[1])) - 1;
        mYear = Integer.parseInt(dateParts[2]);
        //date for alarm
        date = mDay + "-" + mMonth + "-" + mYear;
        //date for db
        newDate = mDay + "-" + (mMonth + 1) + "-" + mYear;
        newRepeatNo = repeatNo;
        btnDate.setText(formatDate());

        //set edit text value
        etPill.setText(pillData.get(0).getPillName());
        etQty.setText(String.valueOf(pillData.get(0).getQty()));

        //set unit spinner value
        int selectionPosition= unitAdapter.getPosition(pillData.get(0).getUnit());
        spUnit.setSelection(selectionPosition);

        //etUnit.setText(pillData.get(0).getUnit());
        //etDuration.setText(String.valueOf(pillData.get(0).getDuration()));
        spNo.setSelection(repeatNo - 1);


        if (repeatNo == 1) {
            btnTime1.setVisibility(View.VISIBLE);
            btnTime1.setText(formatTime(pillData.get(0).getTime()));
            getTimeMilis(0);

        }
        if (repeatNo == 2) {
            btnTime1.setVisibility(View.VISIBLE);
            btnTime2.setVisibility(View.VISIBLE);
            btnTime1.setText(formatTime(pillData.get(0).getTime()));
            btnTime2.setText(formatTime(pillData.get(1).getTime()));
            getTimeMilis(0);
            getTimeMilis(1);

        }
        if (repeatNo == 3) {
            btnTime1.setVisibility(View.VISIBLE);
            btnTime2.setVisibility(View.VISIBLE);
            btnTime3.setVisibility(View.VISIBLE);
            btnTime1.setText(formatTime(pillData.get(0).getTime()));
            btnTime2.setText(formatTime(pillData.get(1).getTime()));
            btnTime3.setText(formatTime(pillData.get(2).getTime()));
            getTimeMilis(0);
            getTimeMilis(1);
            getTimeMilis(2);

        }
        if (repeatNo == 4) {
            btnTime1.setVisibility(View.VISIBLE);
            btnTime2.setVisibility(View.VISIBLE);
            btnTime3.setVisibility(View.VISIBLE);
            btnTime4.setVisibility(View.VISIBLE);
            btnTime1.setText(formatTime(pillData.get(0).getTime()));
            btnTime2.setText(formatTime(pillData.get(1).getTime()));
            btnTime3.setText(formatTime(pillData.get(2).getTime()));
            btnTime4.setText(formatTime(pillData.get(3).getTime()));
            getTimeMilis(0);
            getTimeMilis(1);
            getTimeMilis(2);
            getTimeMilis(3);
        }
        Log.d(TAG, "NextAlarm Time: " + time[0] + " " + timeInMilis[0]);
    }

    public void getTimeMilis(int code) {

        //get alarm time
        time[code] = pillData.get(code).getTime();

        //get time in milis
        String datetime = newDate + " " + pillData.get(code).getTime();
        SimpleDateFormat sdtf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            java.util.Date mDate = sdtf.parse(datetime);
            timeInMilis[code] = mDate.getTime();
            Log.d(TAG, "Date format: " + datetime);
            Log.d(TAG, "NextAlarm Milis: " + code + " " + timeInMilis[code]);
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
        switch (item.getItemId()) {
            //for toolbar arrow
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "back Button Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuSave:
                updatePill();
                //Toast.makeText(getApplicationContext(), "Save Button Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDelete:
                deletePill();
                //Toast.makeText(getApplicationContext(), "Delete Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void deletePill() {
        String pillName = etPill.getText().toString();
        int id;
        //AddPill into Database
        for (int i = 0; i < repeatNo; i++) {
            id = pillData.get(i).getCode();
            //pillName=pillData.get(0).getPillName();
            dbHelper.deletePill(id);
            Toast.makeText(getApplicationContext(), "Data " + (i + 1) + " succesfully deleted for " + pillName, Toast.LENGTH_SHORT).show();
        }
        //Cancel Alarm
        for (int i = 0; i < repeatNo; i++) {
            id = pillData.get(i).getCode();
            AlarmHandler alarmHandler = new AlarmHandler();
            alarmHandler.cancelAlarm(EditPill.this, id);
            Log.d("Code for delete alarm", String.valueOf(id));
            Log.d("Date for delete alarm", String.valueOf(date));
            Log.d("Time for delete alarm", String.valueOf(time[i]));
            Log.d("Milis for delete alarm", String.valueOf(timeInMilis[i]));
        }

        finish();
        Intent intent = new Intent(EditPill.this, ShowPill.class);
        startActivity(intent);
        finish();
    }

    public void updatePill() {
        String pillName = etPill.getText().toString();
        int qty = Integer.parseInt(etQty.getText().toString());
        //String unit = this.unit;
        //int duration = Integer.parseInt(etDuration.getText().toString());
        int duration=1;
        int id;
        Log.d("Data length:", String.valueOf(pillData.size()));
        Log.d("Data Check for update", pillName + " " + newDate + " " + repeatNo + " " + newRepeatNo);



        //AddPill into Database
        //if new repeat is equal to previous repeat,just update the times
        if (repeatNo == newRepeatNo) {
            Log.d("id-update", "repeatNo==newRepeatNo");
            for (int i = 0; i < repeatNo; i++) {
                id = pillData.get(i).getCode();
                PillData ds = new PillData(id, pillName, qty, unit, duration, days, newDate, time[i], newRepeatNo, active);
                Log.d("Data Check for update", id + " " + pillName + " " + qty + " " + unit + " " + duration + " " + days + " " + newDate + " " + repeatNo + " " + time[i]);
                dbHelper.updatePillData(ds);
                Toast.makeText(getApplicationContext(), "Pill " + (i + 1) + " Updated for " + time[i], Toast.LENGTH_SHORT).show();
            }
        }
        //if new repeat is more than previous repeat,update previous time and add new time
        if (repeatNo < newRepeatNo) {

            //check time for new repeat
            for (int i = repeatNo; i < newRepeatNo; i++) {
               if(time[i]==null){
                   Toast.makeText(this, "Please select Time", Toast.LENGTH_SHORT).show();
                   return;
               }
            }

            Log.d("id-update", "repeatNo<newRepeatNo");
            //update time for previous repeat
            for (int i = 0; i < repeatNo; i++) {
                id = pillData.get(i).getCode();
                PillData ds = new PillData(id, pillName, qty, unit, duration, days, newDate, time[i], newRepeatNo, active);
                Log.d("Data Check for update", id + " " + pillName + " " + qty + " " + unit + " " + duration + " " + days + " " + newDate + " " + repeatNo + " " + time[i]);
                dbHelper.updatePillData(ds);
                Toast.makeText(getApplicationContext(), "Pill " + (i + 1) + " Updated for " + time[i], Toast.LENGTH_SHORT).show();
            }
            //add new time in db and add the returned object in current pillData object
            for (int i = repeatNo; i < newRepeatNo; i++) {
                PillData ds = new PillData(pillName, qty, unit, duration, days, newDate, time[i], newRepeatNo, active);
                int code = dbHelper.insertPillData(ds);
                pillData.addAll(dbHelper.getPillInfoById(code));
                Log.d("Data length:", String.valueOf(pillData.size()));
                Log.d("Data-update-add", code + " " + pillName + " " + qty + " " + unit + " " + duration + " " + days + " " + newDate + " " + repeatNo + " " + time[i]);
            }
        }
//if new repeat is less than previous repeat,update new time and delete extra previous times
        if (repeatNo > newRepeatNo) {
            Log.d("id-update", "repeatNo>newRepeatNo");
            for (int i = 0; i < newRepeatNo; i++) {
                id = pillData.get(i).getCode();
                PillData ds = new PillData(id, pillName, qty, unit, duration, days, newDate, time[i], newRepeatNo, active);
                Log.d("Data Check for update", id + " " + pillName + " " + qty + " " + unit + " " + duration + " " + days + " " + newDate + " " + repeatNo + " " + time[i]);
                dbHelper.updatePillData(ds);
                Toast.makeText(getApplicationContext(), "Pill " + (i + 1) + " Updated for " + time[i], Toast.LENGTH_SHORT).show();
            }
            //get id of extra times in previous repeat,delete from db and cancel alarm
            for (int i = (repeatNo - 1); i >= newRepeatNo; i--) {
                id = pillData.get(i).getCode();
                dbHelper.deletePill(id);
                AlarmHandler alarmHandler = new AlarmHandler();
                alarmHandler.cancelAlarm(EditPill.this, id);
                Log.d("id-update-delete", String.valueOf(id));
            }
        }
        Log.d("Code length", String.valueOf(repeatNo));
        //Trigger Alarm
        if (active.equals("true")) {
            for (int i = 0; i < newRepeatNo; i++) {
                id = pillData.get(i).getCode();

                getPresentTimeMillis();
                if (presentTimeMillis > timeInMilis[i]) {
                    timeInMilis[i] += 86400000;
                }
                AlarmHandler alarmHandler = new AlarmHandler();
                //alarmHandler.cancelAlarm(EditPill.this, id);
                alarmHandler.startAlarm(EditPill.this, pillName,time[i],date,duration, timeInMilis[i], id,qty,unit);
                Log.d("Code Check for alarm", String.valueOf(id));
                Log.d("Date for alarm", String.valueOf(date));
                Log.d("Time for alarm", String.valueOf(time[i]));
                Log.d("TimeMilis for alarm", String.valueOf(timeInMilis[i]));
            }
        }
        if (active.equals("false")) {
            for (int i = 0; i < newRepeatNo; i++) {
                id = pillData.get(i).getCode();
                AlarmHandler alarmHandler = new AlarmHandler();
                alarmHandler.cancelAlarm(EditPill.this, id);
                Log.d("Code for CancelAlarm", String.valueOf(id));
                Log.d("Date for CancelAlarm", String.valueOf(date));
                Log.d("Time for CancelAlarm", String.valueOf(time[i]));
                Log.d("TimeMilis for Cancel", String.valueOf(timeInMilis[i]));
            }
        }
        finish();
        Intent intent = new Intent(EditPill.this, ShowPill.class);
        startActivity(intent);
        finish();
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
        Log.d("Present TimeMilis", String.valueOf(presentTimeMillis));
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
        c.set(mYear, mMonth, mDay);
        Date now = c.getTime();
        formattedDate = sdtf.format(now);
        return formattedDate;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ShowPill.class));
        finish();
    }

}
