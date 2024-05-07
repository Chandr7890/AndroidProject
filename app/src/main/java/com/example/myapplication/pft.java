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

public class pft extends AppCompatActivity {

    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8;
    Button saveButton;
    url ob = new url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pft);

        editText1 = findViewById(R.id.id1);
        editText2 = findViewById(R.id.id2);
        editText3 = findViewById(R.id.id3);
        editText4 = findViewById(R.id.id4);
        editText5 = findViewById(R.id.idt1);
        editText6 = findViewById(R.id.idt2);
        editText7 = findViewById(R.id.idt3);
        editText8 = findViewById(R.id.idt4);
        saveButton = findViewById(R.id.btn);

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

                sendRequestToServer(text1, text2, text3, text4, text5, text6, text7, text8);
            }
        });
    }

    private void sendRequestToServer(String text1, String text2, String text3, String text4,
                                     String text5, String text6, String text7, String text8) {

        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");
        String url = "http://"+ob.url+"/PHP/pft.php"; // Replace with your PHP script URL

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response
                        Log.d("Response", response);
                        Intent intent = new Intent(pft.this,interqnr5.class);
                        intent.putExtra("id",value);
                        startActivity(intent);
                        Toast.makeText(pft.this, "Data sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("Error", "Error occurred", error);
                        Toast.makeText(pft.this, "Error occurred while sending data", Toast.LENGTH_SHORT).show();
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
                params.put("id",value);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
