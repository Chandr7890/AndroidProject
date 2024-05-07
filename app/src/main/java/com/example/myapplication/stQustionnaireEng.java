package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class stQustionnaireEng extends AppCompatActivity {

    private int score = 0; url ob = new url();

    private Button btn;
    private RequestQueue requestQueue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_qustionnaire_eng);
        requestQueue = Volley.newRequestQueue(this);
        RadioGroup radioGroup1 = findViewById(R.id.radioGroup1);
        RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);
        RadioGroup radioGroup3 = findViewById(R.id.radioGroup3);
        RadioGroup radioGroup4 = findViewById(R.id.radioGroup4);
        RadioGroup radioGroup5 = findViewById(R.id.radioGroup5);
        RadioGroup radioGroup6 = findViewById(R.id.radioGroup6);
        RadioGroup radioGroup7 = findViewById(R.id.radioGroup7);
        RadioGroup radioGroup8 = findViewById(R.id.radioGroup8);
        btn = findViewById(R.id.button);
        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 1));
        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 2));
        radioGroup3.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 3));
        radioGroup4.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 4));
        radioGroup5.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 5));
        radioGroup6.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 6));
        radioGroup7.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 7));
        radioGroup8.setOnCheckedChangeListener((group, checkedId) -> calculateScore(checkedId, 8));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayScore();
                sendScoreToServer(score);
            }
        });

    }

    private void calculateScore(int checkedId, int groupNumber) {
        int selectedScore = 0;

        switch (groupNumber) {
            case 1:

                if (checkedId == R.id.r1o1) {
                    selectedScore = 4;
                } else if (checkedId == R.id.r1o2) {
                    selectedScore = 3;
                } else if (checkedId == R.id.r1o3) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r1o4) {
                    selectedScore = 1;
                }
                break;
            case 2:
                if (checkedId == R.id.r2o1) {
                    selectedScore = 4;
                } else if (checkedId == R.id.r2o2) {
                    selectedScore = 3;
                } else if (checkedId == R.id.r2o3) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r2o4) {
                    selectedScore = 1;
                }
                break;
            case 3:
                if (checkedId == R.id.r3o1) {
                    selectedScore = 3;
                } else if (checkedId == R.id.r3o2) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r3o3) {
                    selectedScore = 1;
                }
                break;
            case 4:
                if (checkedId == R.id.r4o1) {
                    selectedScore = 3;
                }
                else if (checkedId == R.id.r4o3) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r4o4) {
                    selectedScore = 1;
                }
                break;
            case 5:
                if (checkedId == R.id.r5o1) {
                    selectedScore = 3;
                } else if (checkedId == R.id.r5o2) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r5o3) {
                    selectedScore = 1;
                }
                break;
            case 6:
                if (checkedId == R.id.r6o1) {
                    selectedScore = 1;
                } else if (checkedId == R.id.r6o2) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r6o3) {
                    selectedScore = 3;
                } else if (checkedId == R.id.r6o4) {
                    selectedScore = 4;
                }
                break;
            case 7:
                if (checkedId == R.id.r7o1) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r7o2) {
                    selectedScore = 1;
                }
                break;
            case 8:
                if (checkedId == R.id.r8o1) {
                    selectedScore = 3;
                } else if (checkedId == R.id.r8o2) {
                    selectedScore = 2;
                } else if (checkedId == R.id.r8o3) {
                    selectedScore = 1;
                }
                break;
            default:
                break;
        }

        score += selectedScore;

    }

    private void displayScore() {
        Toast.makeText(this, "Score: " + score, Toast.LENGTH_SHORT).show();
        // You can perform other actions based on the score here
    }

    private void sendScoreToServer(final int score) {
        Intent intent1 = getIntent();
       String value = intent1.getStringExtra("id");

        // URL of your PHP server
        String url = "http://"+ob.url+"/PHP/st.php";

        // Create a StringRequest to send the score as a parameter
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from server

                        Toast.makeText(getApplicationContext(), "Score sent successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(stQustionnaireEng.this,dashboard.class);
                        intent.putExtra("id",value);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(getApplicationContext(), "Error sending score: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Create a HashMap to hold the score parameter
                Map<String, String> params = new HashMap<>();
                params.put("score", String.valueOf(score));
                params.put("pid", value);
                return params;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }
}
