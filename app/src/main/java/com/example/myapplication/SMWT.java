package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SMWT extends AppCompatActivity {
    url ob = new url();

    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8,editText9, editText10, editText11, editText12;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smwt);

        editText1 = findViewById(R.id.bp);
        editText2 = findViewById(R.id.hr);
        editText3 = findViewById(R.id.spo2);
        editText4 = findViewById(R.id.dys);
        editText5 = findViewById(R.id.fat);
        editText6 = findViewById(R.id.bp1);
        editText7 = findViewById(R.id.hr1);
        editText8 = findViewById(R.id.spo21);
        editText9 = findViewById(R.id.dys1);
        editText10 = findViewById(R.id.fat1);
        editText11= findViewById(R.id.t1);
        editText12 = findViewById(R.id.t2);

        saveButton = findViewById(R.id.button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                String text3 = editText3.getText().toString();
                String text4 = editText4.getText().toString();
                String text5 = editText5.getText().toString();
                String text6 = editText6.getText().toString();
                String text7 = editText7.getText().toString();
                String text8 = editText8.getText().toString();
                String text9 = editText9.getText().toString();
                String text10 = editText10.getText().toString();
                String text11= editText11.getText().toString();
                String text12 = editText12.getText().toString();

                sendRequestToServer(text1, text2, text3, text4, text5, text6, text7, text8,text9,text10,text11,text12);
            }
        });
    }

    private void sendRequestToServer(String text1, String text2, String text3, String text4,
                                     String text5, String text6, String text7, String text8,String text9,String text10,String text11,String text12) {
//
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");

        String url = "http://"+ob.url+"/PHP/smwt.php"; // Replace with your PHP script URL

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response
                       Log.d("Response", response);
                        Intent intent = new Intent(SMWT.this,interqnr5.class);
                        intent.putExtra("id",value);
//                        startActivity(intent);
                        Toast.makeText(SMWT.this, "Data sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("Error", "Error occurred", error);
                        Toast.makeText(SMWT.this, "Error occurred while sending data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Create a HashMap to hold the parameters to be sent to the server
                Map<String, String> params = new HashMap<>();
                params.put("param1", text1);
                params.put("param2", text2);
                params.put("param3", text3);
                params.put("param4", text4);
                params.put("param5", text5);
                params.put("param6", text6);
                params.put("param7", text7);
                params.put("param8", text8);
                params.put("param9", text9);
                params.put("param10", text10);
                params.put("param11", text11);
                params.put("param12", text12);
                params.put("id",value);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
