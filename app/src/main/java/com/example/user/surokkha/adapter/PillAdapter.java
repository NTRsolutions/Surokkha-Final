package com.example.user.surokkha.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.user.surokkha.R;
import com.example.user.surokkha.activities.EditPill;
import com.example.user.surokkha.model.PillData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 3/18/2018.
 */

public class PillAdapter extends RecyclerView.Adapter<PillAdapter.ViewHolder> {
    Context contex;
    ArrayList<PillData> data;
    String time;

    public PillAdapter(Context contex, ArrayList<PillData> data) {
        this.contex = contex;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //contex=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.pill_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PillData obj = data.get(position);

        holder.setPillTypeColor(obj.getUnit());
        holder.pill.setText(obj.getPillName());
        String qtyUnit = obj.getQty() + " " + obj.getUnit();
        holder.qtyUnit.setText(qtyUnit);
        //holder.unit.setText(obj.getUnit());
        //holder.day.setText(obj.getDay());
        //String time = formatDate(obj.getDate()) + " | " +obj.getDuration()+" Times";
        if(obj.getRepeatNo()==1){
            time = "Everyday" + " | " +obj.getRepeatNo()+" Time";
        }
        else {
            time = "Everyday" + " | " + obj.getRepeatNo() + " Times";
        }

        holder.time.setText(time);
        //holder.iv.setImageResource(R.drawable.image);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Checking: " + obj.getCode() + " " + obj.getPillName());
                Log.d(TAG, "Checking: " + contex);
                Intent intent = new Intent(contex, EditPill.class);
                intent.putExtra("pillName", obj.getPillName());
                intent.putExtra("qty", obj.getQty());
                intent.putExtra("unit", obj.getUnit());
                intent.putExtra("day", obj.getDay());
                intent.putExtra("time", obj.getTime());
                intent.putExtra("date", obj.getDate());
                intent.putExtra("duration", obj.getDuration());
                intent.putExtra("active", obj.getActive());
                intent.putExtra("repeatNo", obj.getRepeatNo());
                contex.startActivity(intent);
                Log.d(TAG, obj.getCode() + " " + obj.getPillName());
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pill;
        TextView qtyUnit;
        //TextView day;
        TextView time;
        ImageView ivPill;
        LinearLayout linearLayout;
        CardView card;

        private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;

        public ViewHolder(View itemView) {
            super(itemView);
            pill = itemView.findViewById(R.id.tvPillName);
            qtyUnit = itemView.findViewById(R.id.tvQtyUnit);
            //unit = itemView.findViewById(R.id.tvUnit);
            //day = itemView.findViewById(R.id.tvDay);
            time = itemView.findViewById(R.id.tvTime);
            ivPill = itemView.findViewById(R.id.ivPill);
            linearLayout = itemView.findViewById(R.id.ll1);
            card = itemView.findViewById(R.id.card);
        }

        // Set reminder title view
        public void setPillTypeColor(String unit) {
            if (unit != null && !unit.isEmpty()) {
                unit = unit.toLowerCase();
                if (unit.equals("pill")) {
                    ivPill.setImageResource(R.drawable.tablet_48);
                }
                if (unit.equals("ml")) {
                    ivPill.setImageResource(R.drawable.syrup_48);
                }
                if (unit.equals("drop")) {
                    ivPill.setImageResource(R.drawable.drop_48);
                }
                if (unit.equals("spoon")) {
                    ivPill.setImageResource(R.drawable.syrup_48);
                }
            }

            int color = mColorGenerator.getRandomColor();
            linearLayout.setBackgroundColor(color);
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
