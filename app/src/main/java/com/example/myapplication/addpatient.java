package com.example.myapplication;

import android.annotation.SuppressLint;
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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class addpatient extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private EditText name, age, sex, cause;
    url ob = new url();
    String URL = "http://"+ob.url+"/PHP/addpatient.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);
        button = findViewById(R.id.btn);
        editText = findViewById(R.id.pid1);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        sex = findViewById(R.id.sex);
        cause = findViewById(R.id.cause);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = editText.getText().toString();
                String name1 = name.getText().toString();
                String age1 = age.getText().toString();
                String sex1 = sex.getText().toString();
                String cause1 = cause.getText().toString();

                if (!pid.isEmpty() && !name1.isEmpty() && !age1.isEmpty() && !sex1.isEmpty() && !cause1.isEmpty()) {
                    try {
                        int ageValue = Integer.parseInt(age1);
                        String password = pid.substring(Math.max(0, pid.length() - 4));

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
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
                                Map<String, String> data = new HashMap<>();
                                data.put("pid", pid);
                                data.put("password", password);
                                data.put("name", name1);
                                data.put("age", age1);
                                data.put("sex", sex1);
                                data.put("cause", cause1);
                                return data;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    } catch (NumberFormatException e) {
                        Toast.makeText(addpatient.this, "Invalid age input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(addpatient.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleResponse(String response) {
        Gson gson = new Gson();
        Log.d("JSON Response", response);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String status = jsonObject.get("status").getAsString();
        Log.d("JSON Response", status);

        if ("success".equals(status)) {
            Toast.makeText(this, "successfully saved the data", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(addpatient.this, ddashboard.class);
            startActivity(intent);
        } else if ("failure".equals(status)) {
            Toast.makeText(this, "not able to save", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }
}
