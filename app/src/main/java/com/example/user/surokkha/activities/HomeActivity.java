package com.example.user.surokkha.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.classes.SharedPrefManager;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void pill(View view){
        Intent intent=new Intent(this,ShowPill.class);
        startActivity(intent);
        finish();

    }
    public void appointment(View view){
        Intent intent=new Intent(this,ShowAppointment.class);
        startActivity(intent);
        finish();

    }

    public void showNote(View view){
        Intent intent=new Intent(this,ShowNote.class);
        startActivity(intent);
        finish();
    }
    public void findDoctor(View view){
        Intent intent=new Intent(this,FindDoctor.class);
        startActivity(intent);
        finish();
    }
    public void findHospital(View view){
        Intent intent=new Intent(this,FindHospital.class);
        startActivity(intent);
        finish();
    }

    public void aboutUs(View view){
        Intent intent=new Intent(this,AboutUs.class);
        startActivity(intent);
        finish();

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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
