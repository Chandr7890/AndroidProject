package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class form extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6,btn7;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        btn1 = findViewById(R.id.btnEss1) ;
        btn2 = findViewById(R.id.btnEss2) ;
        btn3 = findViewById(R.id.btnsmwt) ;

        btn5 = findViewById(R.id.btnmmr);
        btn6 = findViewById(R.id.btnmbds);
        btn7 =findViewById(R.id.btnpft);
        Intent intent1 = getIntent();
       String value = intent1.getStringExtra("id");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form.this, interqnr1.class);
                Log.d("renaraform",value);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form.this, interqnr2.class);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form.this, interqnr6.class);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });


        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form.this, interqnr3.class);
                Log.d("renaraform",value);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form.this, interqnr4.class);
                Log.d("renaraform",value);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form.this, interqnr5.class);
                Log.d("renaraform",value);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });

    }
}