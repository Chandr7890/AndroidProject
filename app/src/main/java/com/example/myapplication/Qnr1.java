package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Qnr1 extends AppCompatActivity {

    private int totalMark = 0; // Initialize totalMark to keep track of the sum
    Button btn ;
    url ob = new url();
    private String URL = "http://"+ob.url+"/PHP/Qnr1.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnr1);
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");

        // Find all the CheckBoxes by their IDs
        CheckBox yesCheckBox1 = findViewById(R.id.yes);
        CheckBox yesCheckBox2 = findViewById(R.id.yes1);
        CheckBox yesCheckBox3 = findViewById(R.id.yes2);
        CheckBox yesCheckBox4 = findViewById(R.id.yes3);
        CheckBox yesCheckBox5 = findViewById(R.id.yes4);
        CheckBox yesCheckBox6 = findViewById(R.id.yes5);
        CheckBox yesCheckBox7 = findViewById(R.id.yes6);
        CheckBox yesCheckBox8 = findViewById(R.id.yes7);

        // Set click listeners for all the CheckBoxes
        yesCheckBox1.setOnClickListener(onClickListener);
        yesCheckBox2.setOnClickListener(onClickListener);
        yesCheckBox3.setOnClickListener(onClickListener);
        yesCheckBox4.setOnClickListener(onClickListener);
        yesCheckBox5.setOnClickListener(onClickListener);
        yesCheckBox6.setOnClickListener(onClickListener);
        yesCheckBox7.setOnClickListener(onClickListener);
        yesCheckBox8.setOnClickListener(onClickListener);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Handle the response

                                handleResponse(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleError(error);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // Send the username and password as POST parameters
                        Map<String, String> data = new HashMap<>();
                        data.put("score", Integer.toString(totalMark));
                        data.put("pid",value);
                        return data;
                    }
                };

                // Customize the retry policy
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                // Initialize the Volley request queue and add the request
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
                Toast.makeText(Qnr1.this,  "Total Marks: " + totalMark, Toast.LENGTH_SHORT).show();
            }




        });



    }

    private void handleResponse(String response) {
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");
        Gson gson = new Gson();
        Log.d("JSON Response", response);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String status = jsonObject.get("status").getAsString();
        Log.d("JSON Response", status);

        if ("success".equals(status)) {
            Intent intent = new Intent(Qnr1.this, interqnr1.class);

            intent.putExtra("id",value);

            startActivity(intent);

        } else if ("failure".equals(status)) {
            Toast.makeText(Qnr1.this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle network request errors
    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(Qnr1.this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Qnr1.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }

    // Click listener for all the CheckBoxes
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox checkBox = (CheckBox) v;

            // Check if the CheckBox is checked
            if (checkBox.isChecked()) {
                // If checked, increase the totalMark by 1
                totalMark += 1;
                Toast.makeText(Qnr1.this, "Marked as 1", Toast.LENGTH_SHORT).show();
            } else {
                // If unchecked, decrease the totalMark by 1

                Toast.makeText(Qnr1.this, "Unchecked", Toast.LENGTH_SHORT).show();
            }


        }


    };

}
