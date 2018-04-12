package com.example.user.surokkha.activities;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.example.user.surokkha.R;

public class AlarmActivity extends AppCompatActivity {
    AppCompatTextView tvTime, tvName, tvExtra;
    Button dismiss;
    String time,name,extra,unit;
    int qty;
    Boolean isDismissed=false;
    Bundle extras;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
        tvTime = findViewById(R.id.time_textView);
        tvName = findViewById(R.id.name_textView);
        tvExtra= findViewById(R.id.extra_textView);
        dismiss = findViewById(R.id.dismiss_button);

        extras = getIntent().getExtras();

        if(extras!=null)
        {
            //if check=0 it is appointment
            if(extras.getInt("check")==0) {
                time=extras.getString("time");
                tvTime.setText(formatTime(time));
                name = extras.getString("doctorName");
                tvName.setText(name);
                extra = extras.getString("location");
                tvExtra.setText(extra);
            }
            //if check=0 it is pill
            if(extras.getInt("check")==1) {
                time=extras.getString("time");
                tvTime.setText(formatTime(time));
                name = extras.getString("pillName");
                tvName.setText(name);
                qty = extras.getInt("qty");
                unit = extras.getString("unit");
                extra=qty+"  "+unit;
                tvExtra.setText(extra);

            }
        }

        //start media player toplay alarm ringtone
        mediaPlayer = MediaPlayer.create(AlarmActivity.this, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();

        //start a countdown timer for 30 sec stop media player after 30 sec and finish activity
        CountDownTimer timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Nothing to do
            }

            @Override
            public void onFinish() {
                if (!isDismissed) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    finish();
                }
            }
        };
        timer.start();

        //dismiss alarm button
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                finish();

                isDismissed=true;
            }
        });
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
}
