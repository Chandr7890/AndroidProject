package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calender extends AppCompatActivity {
 CalendarView calendarView;

 TextView data;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendarView = findViewById(R.id.calender);
        data = findViewById(R.id.dis);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                month += 1;

                // Formatting the month and day strings to have leading zeros if needed
                String formattedMonth = String.format("%02d", month);
                String formattedDayOfMonth = String.format("%02d", day);


                Intent intent1 = getIntent();
                String value = intent1.getStringExtra("id");
                String date = year+"/"+formattedMonth+"/"+formattedDayOfMonth;
                Intent intent = new Intent(Calender.this,Availableslots.class);
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();

                intent.putExtra("key", date);
                intent.putExtra("id",value);
                startActivity(intent);

            }
        });





    }
}