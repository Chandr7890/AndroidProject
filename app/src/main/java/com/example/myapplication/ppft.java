package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.github.mikephil.charting.charts.BarChart;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ppft extends AppCompatActivity {
    Button btn ;
    TextView textView1;

    private BarChart barChart;
    private RequestQueue requestQueue;
    TextView editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8;
    url ob = new url();
    String URL = "http://"+ob.url+"/PHP/pprepft.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppft);
        btn = findViewById(R.id.btn);
        editText1 = findViewById(R.id.id1);
        editText2 = findViewById(R.id.id2);
        editText3 = findViewById(R.id.id3);
        editText4 = findViewById(R.id.id4);
        editText5 = findViewById(R.id.idt1);
        editText6 = findViewById(R.id.idt2);
        editText7 = findViewById(R.id.idt3);
        editText8 = findViewById(R.id.idt4);

        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");

        Log.d("renarainterqnr1",value);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ppft.this,Qnr1.class);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });


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

    }
    private void handleResponse(String response) {
        Gson gson = new Gson();
        Log.d("JSON Response", response);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        String status = jsonObject.get("status").getAsString();
        String q1 = jsonObject.get("s1").getAsString();
        String q2= jsonObject.get("s2").getAsString();
        String q3 = jsonObject.get("s3").getAsString();
        String q4= jsonObject.get("s4").getAsString();
        String q5 = jsonObject.get("s5").getAsString();
        String q6= jsonObject.get("s6").getAsString();
        String q7 = jsonObject.get("s7").getAsString();
        String q8= jsonObject.get("s8").getAsString();
        Log.d("JSON Response", status);

        if ("success".equals(status)) {
//            Intent intent = new Intent(Qnr1.this, interqnr1.class);
//
//            //intent.putExtra("id",username);
//
//            startActivity(intent);
//            if(!TextUtils.isEmpty(q1)){
//                int n =Integer.parseInt(q1);
//                if(n>=0&&n<=2){
//                    t1.setText("Low Risk pf OSA");
//                    t1.setTextColor(Color.parseColor("#FF0000"));
//                }else if(n>=3&&n<=4){
//                    t1.setText("Intermediate risk of OSA");
//                    t1.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else{
//                    t1.setText("High risk of OSA");
//                    t1.setTextColor(Color.parseColor("#FF0000"));
//                }
//
//            }
//            if(!TextUtils.isEmpty(q2)){
//                int n1 =Integer.parseInt(q2);
//                if(n1>=0&&n1<=2){
//                    t2.setText("Low Risk pf OSA");
//                    t2.setTextColor(Color.parseColor("#FF0000"));
//                }else if(n1>=3&&n1<=4){
//                    t2.setText("Intermediate risk of OSA");
//                    t2.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else{
//                    t2.setText("High risk of OSA");
//                    t2.setTextColor(Color.parseColor("#FF0000"));
//                }
//
//            }



editText1.setText(q1);
            editText2.setText(q2);
            editText3.setText(q3);
            editText4.setText(q4);
            editText5.setText(q5);
            editText6.setText(q6);
            editText7.setText(q7);
            editText8.setText(q8);




        } else if ("failure".equals(status)) {
            Toast.makeText(ppft.this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle network request errors
    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }

}