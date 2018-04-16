package com.example.user.surokkha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.surokkha.R;
import com.example.user.surokkha.db.DBHelper;
import com.example.user.surokkha.model.NoteData;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    EditText etTitle, etNote;
    Button btnDelete;
    int id;
    String title, note, date, time;
    Boolean isUpdate = false;
    DBHelper dbHelper;
    Bundle extras;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new DBHelper(getApplicationContext());

        etNote = findViewById(R.id.etNoteBody);
        etTitle = findViewById(R.id.etNoteTitle);
        //btnDelete = findViewById(R.id.btnDeleteNote);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int pos = etNote.getText().length();
        etNote.setSelection(pos);
        //etNote.requestFocus(pos);

        //get Intent Data
        extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            title = extras.getString("title");
            note = extras.getString("note");
            isUpdate = true;
            etTitle.setText(title);
            etNote.setText(note);
           // btnDelete.setVisibility(View.VISIBLE);
        }
        Log.d("Extra Data Check", id + " " + title + " " + isUpdate);


    }

    //For Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isUpdate) {
            getMenuInflater().inflate(R.menu.menu_update, menu);
        }
        if (!isUpdate) {
            getMenuInflater().inflate(R.menu.menu_add, menu);
        }
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
                saveNote();
                //Toast.makeText(getApplicationContext(), "Save Button Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDelete:
                deleteNote();
                //Toast.makeText(getApplicationContext(), "Delete Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void saveNote() {
        title = etTitle.getText().toString();
        note = etNote.getText().toString();
        int code;

        // From calander get the year, month, day, hour, minute
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        date = day + "-" + (month + 1) + "-" + year;
        time = hour + ":" + minute;

        //Add Note into Database
        Log.d("Appoint Data Check", title + " " + note + " " + date + " " + time);
        if (!isUpdate) {
            NoteData dn = new NoteData(title, note, date, time);
            code = dbHelper.addNote(dn);
            Toast.makeText(getApplicationContext(), "Note " + code + " Added", Toast.LENGTH_SHORT).show();
        }
        if (isUpdate) {
            NoteData dn = new NoteData(id, title, note, date, time);
            dbHelper.updateNote(dn);
            Toast.makeText(getApplicationContext(), "Note " + id + " Updated", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, ShowNote.class);
        startActivity(intent);
        finish();
    }

    public void deleteNote() {
        dbHelper.deleteNote(id);
        Intent intent = new Intent(this, ShowNote.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ShowNote.class));
        finish();
    }
}
