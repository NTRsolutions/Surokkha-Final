package com.example.user.surokkha.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.SharedPrefManager;
import com.example.user.surokkha.db.DBExternal;

import java.util.ArrayList;

public class FindDoctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    Spinner spSpeciality, spDistrict, spArea;
    Button btnFind;
    ArrayList<String> speciality, district, area;
    String selectedDistrict,selectedSpeciality,selectedArea;
    DBExternal dbExternal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        spArea = findViewById(R.id.spArea);
        spDistrict = findViewById(R.id.spDistrict);
        spSpeciality = findViewById(R.id.spSpeciality);
        btnFind = findViewById(R.id.btnSearchDoctor);

        dbExternal = new DBExternal(getApplicationContext());
       //speciality adapter
        speciality = dbExternal.getSpeciality();
        Log.d("Speciality", "Check speciality: "+speciality.get(0));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spText, speciality);
        spSpeciality.setAdapter(adapter);
        //district adapter
        district = dbExternal.getDistrict();
        Log.d("District", "Check district length: "+district.get(0));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spText, district);
        spDistrict.setAdapter(adapter2);

        selectedDistrict=district.get(0);

        spSpeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpeciality=spSpeciality.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = spDistrict.getSelectedItem().toString();

                //area adapter
                area = dbExternal.getArea(selectedDistrict);
                //Log.d("Area", "Check area length: "+area.get(0));
                if (!area.isEmpty()) {
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(FindDoctor.this, R.layout.spinner_layout, R.id.spText, area);
                    spArea.setAdapter(adapter3);
                }
                else {
                    String[] aValue={"Not Found"};
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(FindDoctor.this, R.layout.spinner_layout, R.id.spText, aValue);
                    spArea.setAdapter(adapter3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea=spArea.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void searchDoctor(View view){
        Intent intent=new Intent(this,ShowDoctor.class);
        intent.putExtra("speciality",selectedSpeciality);
        intent.putExtra("district",selectedDistrict);
        intent.putExtra("location",selectedArea);
        startActivity(intent);
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
