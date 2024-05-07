package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ESS extends AppCompatActivity {
    url ob = new url();
    private String URL = "http://"+ob.url+"/PHP/ESS.php";
    private RadioGroup[] radioGroups;
    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ess); // Replace with your XML layout file
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");
        radioGroups = new RadioGroup[8]; // Adjust the size based on the number of RadioGroups in your layout
        radioGroups[0] = findViewById(R.id.radioGroup);
        radioGroups[1] = findViewById(R.id.radio1Group);
        // Add remaining RadioGroups...


        radioGroups[2] = findViewById(R.id.radio2Group);
        radioGroups[3] = findViewById(R.id.radio3Group);
        radioGroups[4] = findViewById(R.id.radio4Group);
        radioGroups[5] = findViewById(R.id.radio5Group);
        radioGroups[6] = findViewById(R.id.radio6Group);
        radioGroups[7] = findViewById(R.id.radio7Group);

        submitButton = findViewById(R.id.button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalScore = calculateTotalScore();
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
                        data.put("score", Integer.toString(totalScore));
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
                Toast.makeText(ESS.this, "Total Score: " + totalScore, Toast.LENGTH_SHORT).show();




            }
        });

        setRadioButtonClickListeners();
    }

    private void setRadioButtonClickListeners() {
        for (final RadioGroup radioGroup : radioGroups) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton != null) {
                        String scoreText = radioButton.getText().toString();
                        try {
                            int score = Integer.parseInt(scoreText);
                            // Do something with the checked score if needed
                            // For example: Toast.makeText(ESS.this, "Checked score: " + score, Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }


    private int calculateTotalScore() {
        int totalScore = 0;

        for (RadioGroup radioGroup : radioGroups) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(checkedRadioButtonId);

            if (radioButton != null) {
                String scoreText = radioButton.getText().toString();

                try {
                    int score = Integer.parseInt(scoreText);
                    totalScore += score;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        return totalScore;
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
            Intent intent = new Intent(ESS.this, interqnr2.class);

            intent.putExtra("id",value);

            startActivity(intent);

        } else if ("failure".equals(status)) {
            Toast.makeText(ESS.this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle network request errors
    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(ESS.this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ESS.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }
}
