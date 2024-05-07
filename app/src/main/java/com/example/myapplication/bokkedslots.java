package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class bokkedslots extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    url ob = new url();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bokkedslots);

        textView1 = findViewById(R.id.pidTextView);
        textView2 = findViewById(R.id.nameTextView);
        textView3 = findViewById(R.id.dateTextView);
        textView4 = findViewById(R.id.statusTextView);
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");

        // Make an HTTP request to your PHP script
        String pid = value; // Replace with the actual PID you want to send
        fetchStringFromPHP(pid);
    }

    private void fetchStringFromPHP(String pid) {
        String url = "http://"+ob.url+"/PHP/bokkedslots.php"; // Replace with your PHP script's URL

        // Create a JSON object to send data to the PHP script
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("pid", pid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);


                    // Check if the "pid" field exists in the JSON response
                    if (jsonObject.has("pid")) {
                        String status = jsonObject.get("pid").getAsString();
                        textView1.setText(status);
                        status = jsonObject.get("name").getAsString();
                        textView2.setText(status);
                        status = jsonObject.get("date").getAsString();
                        textView3.setText(status);
                        status = jsonObject.get("status").getAsString();
                        textView4.setText(status);
                    } else {
                        textView1.setText("pid not found in JSON response");
                    }
                } catch (Exception e) {
                    textView1.setText("Error: " + e.getMessage());
                }

                String tx = textView4.getText().toString();
                if(tx.equals("Approved")){
                    textView4.setTextColor(R.color.green);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                textView1.setText("Error: " + error.getMessage());
            }
        }) {
            @Override
            public byte[] getBody() {
                // Convert the JSON object to a byte array
                return jsonData.toString().getBytes();
            }
        };

        requestQueue.add(stringRequest);
    }
}
