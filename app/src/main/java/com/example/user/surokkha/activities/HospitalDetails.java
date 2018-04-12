package com.example.user.surokkha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.user.surokkha.R;

public class HospitalDetails extends AppCompatActivity {
    Bundle extras;
    String hospitalName,location,phone;
    TextView tvHospitalName,tvLocation,tvPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        tvHospitalName=findViewById(R.id.tvHDHospitalName);
        tvLocation=findViewById(R.id.tvHDLocation);

        //get Intent Data
        extras = getIntent().getExtras();
        if (extras != null) {
            hospitalName = extras.getString("hospitalName");
            location= extras.getString("location");
            phone= extras.getString("phone");
            // btnDelete.setVisibility(View.VISIBLE);

tvHospitalName.setText(hospitalName);
tvLocation.setText(location);


        }
    }
}
