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
import com.example.user.surokkha.activities.HospitalDetails;
import com.example.user.surokkha.model.DoctorData;
import com.example.user.surokkha.model.HospitalData;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {
    Context contex;
    ArrayList<HospitalData> data;

    public HospitalAdapter(Context contex, ArrayList<HospitalData> data) {
        this.contex = contex;
        this.data = data;
    }


    @Override
    public HospitalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.hospital_card_layout, parent, false);
        return new HospitalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HospitalAdapter.ViewHolder holder, int position) {
        final HospitalData obj = data.get(position);

        holder.hospitalName.setText(obj.getHospitalName());
        //String location=obj.getLocation()+", "+obj.getDistrict();
        holder.location.setText(obj.getLocation());
        //holder.iv.setImageResource(R.drawable.image);


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Checking: "+obj.getCode());

                Intent intent=new Intent(contex, HospitalDetails.class);
                intent.putExtra("hospitalName",obj.getHospitalName());
                intent.putExtra("location",obj.getLocation());
                intent.putExtra("phone",obj.getPhone1());
                contex.startActivity(intent);
                Log.d(TAG, obj.getCode()+" "+obj.getHospitalName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hospitalName;
        TextView location;
        //ImageView iv;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            hospitalName = itemView.findViewById(R.id.tvHospitalName);
            location = itemView.findViewById(R.id.tvHospitalLocation);
            //iv = itemView.findViewById(R.id.ivT1);
            card=itemView.findViewById(R.id.card_hospital);
        }
    }
}

