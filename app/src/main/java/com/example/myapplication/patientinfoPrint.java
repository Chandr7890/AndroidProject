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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class patientinfoPrint extends AppCompatActivity {

    EditText pidEditText, nameEditText, ageEditText, sexEditText, causeEditText;
    Button btn ;
    Button button1;
    url ob = new url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientinfo_print);

        // Initialize EditText fields
        pidEditText = findViewById(R.id.pid1);
        nameEditText = findViewById(R.id.name);
        ageEditText = findViewById(R.id.age);
        sexEditText = findViewById(R.id.sex);
        causeEditText = findViewById(R.id.cause);
        button1 = findViewById(R.id.form);
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("item");


        // Make an HTTP request to your PHP script
        fetchStringFromPHP();
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonData();

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patientinfoPrint.this, form.class);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });
    }

    private void fetchStringFromPHP() {
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("item");
        String url = "http://"+ob.url+"/PHP/printPatientInfo.php"; // Replace with your PHP script's URL

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RSPONSE",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        // Set values to EditText fields
                        pidEditText.setText(jsonObject.getString("pid"));
                        nameEditText.setText(jsonObject.getString("name"));
                        ageEditText.setText(jsonObject.getString("age"));
                        sexEditText.setText(jsonObject.getString("sex"));
                        causeEditText.setText(jsonObject.getString("cause"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("pid", value);
                return data;
            }
        };

        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest);
    }
    private void sendJsonData() {
        String URL = "http://"+ob.url+"/PHP/patientInfoU.php";
        // Get the data from EditText fields
        String pid = pidEditText.getText().toString();
        String name= nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String sex = sexEditText.getText().toString();
        String cause = causeEditText.getText().toString();
        // Create a JSON object with the data
        JSONObject jsonData = new JSONObject();
        try {

            jsonData.put("pid", pid);
            jsonData.put("name", name);
            jsonData.put("age", age);
            jsonData.put("sex", sex);
            jsonData.put("cause",cause);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");

        // Send the JSON data to the PHP script using Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Check if the JSON response contains a "status" key
                        if (response.has("status")) {
                            String status = null;
                            try {
                                status = response.getString("status");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            if (status.equals("success")) {
                                // Data was updated successfully
                                try {
                                    String message = response.getString("message");
                                    Toast.makeText(patientinfoPrint.this, message, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(dprofileE.this, dprofile.class);
//                                    intent.putExtra("id",value);
//                                    startActivity(intent);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {
                                // Data update was not successful
                                try {
                                    String message = response.getString("message");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        } else {

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors (if needed)
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }





}
