package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class language extends AppCompatActivity {
   Button btn1 ;
   Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Intent intent1= getIntent();
        String value = intent1.getStringExtra("id");
        btn1 = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(language.this,stQustionnaire.class);
                intent.putExtra("id",value);
                startActivity(intent);

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(language.this,stQustionnaireEng.class);
                intent.putExtra("id",value);
                startActivity(intent);

            }
        });

    }
}