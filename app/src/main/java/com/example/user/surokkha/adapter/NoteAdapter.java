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

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.user.surokkha.R;
import com.example.user.surokkha.activities.AddNote;
import com.example.user.surokkha.model.NoteData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 3/23/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context contex;
    ArrayList<NoteData> data;

    public NoteAdapter(Context contex, ArrayList<NoteData> data) {
        this.contex = contex;
        this.data = data;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.note_card_layout, parent, false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        final NoteData obj = data.get(position);

        holder.title.setText(obj.getTitle());
        holder.setReminderTitle(obj.getTitle());
        holder.note.setText(obj.getNote());
        //holder.date.setText( formatDate(obj.getDate()));
        String time=formatDate(obj.getDate())+" | "+formatTime(obj.getTime());
        holder.time.setText(time);
        //holder.iv.setImageResource(R.drawable.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contex, AddNote.class);
                intent.putExtra("id", obj.getId());
                intent.putExtra("title", obj.getTitle());
                intent.putExtra("note", obj.getNote());
                intent.putExtra("date", obj.getDate());
                intent.putExtra("time", obj.getTime());
                contex.startActivity(intent);
                Log.d(TAG, "Checking: " + obj.getId() + " " + obj.getTitle() + " " + obj.getDate() + " " + obj.getTime());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView note;
        //TextView date;
        TextView time;
        private ImageView iv , mThumbnailImage;
        CardView card;
        private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
        private TextDrawable mDrawableBuilder;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNoteTitle);
            note = itemView.findViewById(R.id.tvNoteBody);
            //date = itemView.findViewById(R.id.tvDate_Note);
            time = itemView.findViewById(R.id.tvTime_Note);
            // iv = itemView.findViewById(R.id.ivT1);
            mThumbnailImage = (ImageView) itemView.findViewById(R.id.thumbnail_image);
            card = itemView.findViewById(R.id.card_note);
        }


        // Set reminder title view
        public void setReminderTitle(String title) {
            String letter = "A";

            if(title != null && !title.isEmpty()) {
                letter = title.substring(0, 1);
            }

            int color = mColorGenerator.getRandomColor();

            // Create a circular icon consisting of  a random background colour and first letter of title
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color);
            mThumbnailImage.setImageDrawable(mDrawableBuilder);
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
        int month = (Integer.parseInt(dateParts[1])-1);
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
