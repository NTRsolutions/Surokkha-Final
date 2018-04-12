package com.example.user.surokkha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.user.surokkha.R;

public class DoctorDetails extends AppCompatActivity {
    Bundle extras;
    String doctorName,degree,speciality,chamber,location,visitingTime,phone;
    TextView tvDoctorName,tvDegree,tvSpeciality,tvChamber,tvLocation,tvVisitingTime,tvPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tvDoctorName=findViewById(R.id.tvDDName);
        tvDegree=findViewById(R.id.tvDDDegree);
        tvSpeciality=findViewById(R.id.tvDDSpeciality);
        tvChamber=findViewById(R.id.tvDDChamber);
        tvLocation=findViewById(R.id.tvDDLocation);
        tvVisitingTime=findViewById(R.id.tvDDVT);

        //get Intent Data
        extras = getIntent().getExtras();
        if (extras != null) {
            doctorName = extras.getString("doctorName");
            degree = extras.getString("degree");
          speciality= extras.getString("speciality");
          chamber= extras.getString("chamber");
          location= extras.getString("location");
          visitingTime= extras.getString("visitingTime");
          phone= extras.getString("phone");
            // btnDelete.setVisibility(View.VISIBLE);


            tvDoctorName.setText(doctorName);
            tvDegree.setText(degree);
            tvSpeciality.setText(speciality);
            tvChamber.setText(chamber);
            tvLocation.setText(location);
            tvVisitingTime.setText(visitingTime);
        }


    }
}
