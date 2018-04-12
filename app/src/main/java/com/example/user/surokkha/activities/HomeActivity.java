package com.example.user.surokkha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.user.surokkha.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void pill(View view){
        Intent intent=new Intent(this,ShowPill.class);
        startActivity(intent);

    }
    public void appointment(View view){
        Intent intent=new Intent(this,ShowAppointment.class);
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
}
