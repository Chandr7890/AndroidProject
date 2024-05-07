package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dprofileE extends AppCompatActivity {
    private Button button;
    private EditText etxt1;
    private EditText etxt2;
    private EditText etxt3;
    private EditText etxt4;
    private TextView pname;
    url ob = new url();
    private String URL = "http://"+ob.url+"/PHP/dprofileU.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dprofile_e);

        button = findViewById(R.id.btn1);
        etxt1 = findViewById(R.id.dtexE1);
        etxt2 = findViewById(R.id.dtexE2);
        etxt3 = findViewById(R.id.dtexE3);
        etxt4 = findViewById(R.id.dtexE4);
        pname = findViewById(R.id.prof);
        fetchStringFromPHP();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonData();



            }
        });
    }

    private void sendJsonData() {
        // Get the data from EditText fields
        String tx1 = etxt1.getText().toString();
        String tx2 = etxt2.getText().toString();
        String tx3 = etxt3.getText().toString();
        String tx4 = etxt4.getText().toString();

        // Create a JSON object with the data
        JSONObject jsonData = new JSONObject();
        try {
            Log.d("tag1",tx1);
            Log.d("tag1",tx2);
            Log.d("tag1",tx3);
            Log.d("tag1",tx4);
            jsonData.put("field1", tx1);
            jsonData.put("field2", tx2);
            jsonData.put("field3", tx3);
            jsonData.put("field4", tx4);
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
                                    Toast.makeText(dprofileE.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(dprofileE.this, dprofile.class);
                                    intent.putExtra("id",value);
                                    startActivity(intent);
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



    private EditText textView1 ;
    private EditText textView2 ;
    private EditText textView3 ;
    private EditText textView4;
    private void fetchStringFromPHP() {
        String url = "http://"+ob.url+"/PHP/dprofile.php";
        // Replace with your PHP script's URL
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                textView1= findViewById(R.id.dtexE1);
                textView2=findViewById(R.id.dtexE2);
                textView3=findViewById(R.id.dtexE3);
                textView4=findViewById(R.id.dtexE4);
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    Log.d("tag", String.valueOf(jsonObject));

                    // Check if the "D_name" field exists in the JSON response
                    if (jsonObject.has("D_id")) {
                        String status = jsonObject.get("D_id").getAsString();
                        textView1.setText(status);
                        status = jsonObject.get("D_name").getAsString();
                        textView2.setText(status);
                        pname.setText((status));
                        status = jsonObject.get("D_dep").getAsString();
                        textView3.setText(status);
                        status = jsonObject.get("D_phno").getAsString();
                        textView4.setText(status);
                    } else {
                        textView1.setText("D_name not found in JSON response");
                    }
                } catch (Exception e) {
                    textView1.setText("Error: " + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle any errors here
                textView1.setText("Error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Send the username and password as POST parameters
                Map<String, String> data = new HashMap<>();
                data.put("P_id",value);

                return data;
            }
        };

        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Initialize the Volley request queue and add the request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        ;


    }

}
