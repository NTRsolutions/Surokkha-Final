package com.example.user.surokkha.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.SharedPrefManager;

public class HospitalDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;

    Bundle extras;
    String hospitalName, location, phone;
    TextView tvHospitalName, tvLocation, tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        tvHospitalName = findViewById(R.id.tvHDHospitalName);
        tvLocation = findViewById(R.id.tvHDLocation);

        //get Intent Data
        extras = getIntent().getExtras();
        if (extras != null) {
            hospitalName = extras.getString("hospitalName");
            location = extras.getString("location");
            phone = extras.getString("phone");
            // btnDelete.setVisibility(View.VISIBLE);

            tvHospitalName.setText(hospitalName);
            tvLocation.setText(location);


        }
    }

    public void callHospital(View view){
        Intent intent=new Intent(Intent.ACTION_CALL);
        if(phone!=null){
            intent.setData(Uri.parse("tel:"+phone));

            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Please grant the permission to call", Toast.LENGTH_SHORT).show();
                requestPermission();
            }
            else {
                startActivity(intent);
            }
        }
    }
    private  void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE},1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_update) {
            Toast.makeText(this, "Update Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_rate) {
            Toast.makeText(this, "Rate Selected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_logout) {
            Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
            SharedPrefManager.getmInstance(this).logout();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        return false;
    }
}
