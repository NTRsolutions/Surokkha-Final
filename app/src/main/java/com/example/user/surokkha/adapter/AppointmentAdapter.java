package com.example.user.surokkha.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.surokkha.R;
import com.example.user.surokkha.activities.EditAppointment;
import com.example.user.surokkha.model.AppointmentData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 3/21/2018.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    Context contex;
    ArrayList<AppointmentData> data;

    public AppointmentAdapter(Context contex, ArrayList<AppointmentData> data) {
        this.contex = contex;
        this.data = data;
    }


    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.appointment_card_layout, parent, false);
        return new AppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentAdapter.ViewHolder holder, int position) {
        final AppointmentData obj = data.get(position);

        holder.doctorName.setText(obj.getDoctorName());
        holder.location.setText(obj.getLocation());
        //holder.date.setText( formatDate(obj.getDate()));
        String time = formatDate(obj.getDate()) + " | " + formatTime(obj.getTime());
        holder.time.setText(time);
        //holder.iv.setImageResource(R.drawable.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Checking: " + obj.getCode() + " " + obj.getDoctorName() + " " + obj.getDate() + " " + obj.getTime());

                Intent intent = new Intent(contex, EditAppointment.class);
                intent.putExtra("code", obj.getCode());
                intent.putExtra("doctorName", obj.getDoctorName());
                intent.putExtra("location", obj.getLocation());
                intent.putExtra("date", obj.getDate());
                intent.putExtra("time", obj.getTime());
                intent.putExtra("note", obj.getNote());
                intent.putExtra("active", obj.getActive());
                contex.startActivity(intent);
                Log.d(TAG, obj.getCode() + " " + obj.getDoctorName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView doctorName;
        TextView location;
        //TextView date;
        TextView time;
        //ImageView iv;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.tvDoctorNAme_appointment);
            location = itemView.findViewById(R.id.tvLocation_appointment);
            //date = itemView.findViewById(R.id.tvDate_appointment);
            time = itemView.findViewById(R.id.tvTime_appointment);
            //iv = itemView.findViewById(R.id.ivT1);
            card = itemView.findViewById(R.id.card_appointment);
        }
    }

    //formate time with AM,PM
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

    //formate date
    public String formatDate(String date) {
        String[] dateParts = date.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = (Integer.parseInt(dateParts[1]) - 1);
        int year = Integer.parseInt(dateParts[2]);

        String formattedDate;
        SimpleDateFormat sdtf = new SimpleDateFormat("EEE, dd MMM");

        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date now = c.getTime();
        formattedDate = sdtf.format(now);
        return formattedDate;
    }
}
