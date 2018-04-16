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
import com.example.user.surokkha.activities.DoctorDetails;
import com.example.user.surokkha.activities.EditAppointment;
import com.example.user.surokkha.model.AppointmentData;
import com.example.user.surokkha.model.DoctorData;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 3/21/2018.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    Context contex;
    ArrayList<DoctorData> data;

    public DoctorAdapter(Context contex, ArrayList<DoctorData> data) {
        this.contex = contex;
        this.data = data;
    }


    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.doctor_card_layout, parent, false);
        return new DoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DoctorData obj = data.get(position);

        holder.doctorName.setText(obj.getDoctorName());
        holder.degree.setText(obj.getDegree());
        holder.speciality.setText(obj.getSpeciality());
        String chamber=obj.getChamber()+", "+obj.getDistrict();
        holder.chamber.setText(chamber);
        //holder.iv.setImageResource(R.drawable.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Checking: "+obj.getCode()+" "+obj.getDoctorName());

                Intent intent=new Intent(contex, DoctorDetails.class);
                intent.putExtra("doctorName",obj.getDoctorName());
                intent.putExtra("degree",obj.getDegree());
                intent.putExtra("speciality",obj.getSpeciality());
                intent.putExtra("chamber",obj.getChamber());
                intent.putExtra("location",obj.getLocation());
                intent.putExtra("visitingTime",obj.getVisitingTime());
                intent.putExtra("phone",obj.getPhone1());
                contex.startActivity(intent);
                Log.d(TAG, obj.getCode()+" "+obj.getDoctorName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView doctorName;
        TextView chamber;
        TextView degree,speciality;
        //ImageView iv;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.tvDoctorNAme);
            chamber = itemView.findViewById(R.id.tvDoctorChamber);
            degree=itemView.findViewById(R.id.tvDoctorDegree);
            speciality=itemView.findViewById(R.id.tvDoctorSpeciality);
            //iv = itemView.findViewById(R.id.ivT1);
            card=itemView.findViewById(R.id.card_doctor);
        }
    }
}
