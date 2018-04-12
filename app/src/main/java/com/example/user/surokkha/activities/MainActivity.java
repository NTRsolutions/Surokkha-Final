package com.example.user.surokkha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.AlarmHandler;

public class MainActivity extends AppCompatActivity {
Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1= (Button) findViewById(R.id.bvRecord);
        b2= (Button) findViewById(R.id.bvLeague);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddPill.class));
                AlarmHandler alarmHandler = new AlarmHandler();
                //alarmHandler.startNextAlarm("napa");

                //ArrayList<PillData> data=new ArrayList<>();
                //DBHelper db=new DBHelper(getApplicationContext());
                //data=db.getNextPill("Napa",0);
                //db.getNextPill("Napa",0);
                //String date=data.get(0).getDate()+" "+data.get(0).getTime();
                //Log.d(TAG, "startNextAlarm: "+date);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowPill.class));
            }
        });

        /*DBHelper db=new DBHelper(getApplicationContext());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        //calendar.add(Calendar.DATE, -1);
        String Date =mdformat.format(calendar.getTime());
        //Log.d("-----Date-----",Date);
        db.deleteData(Date);*/

    }
    public void appointment(View view){
        Intent intent=new Intent(this,ShowAppointment.class);
        startActivity(intent);

    }
    public void addAppointment(View view){
        Intent intent=new Intent(this,AddAppointment.class);
        startActivity(intent);

    }
    public void addNote(View view){
        Intent intent=new Intent(this,AddNote.class);
        startActivity(intent);

    }
    public void showNote(View view){
        Intent intent=new Intent(this,ShowNote.class);
        startActivity(intent);

    }
    public void findDoctor(View view){
        Intent intent=new Intent(this,FindDoctor.class);
        startActivity(intent);
    }
    public void findHospital(View view){
        Intent intent=new Intent(this,FindHospital.class);
        startActivity(intent);
    }
    public void verify(View view){
        Intent intent=new Intent(this,PhoneAuthActivity.class);
        startActivity(intent);
    }
    public void phoneAuth(View view){
        Intent intent=new Intent(this,AuthOld.class);
        startActivity(intent);
    }
    public void home(View view){
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}

