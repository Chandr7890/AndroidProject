package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class interqnr4 extends AppCompatActivity {
    Button btn ;
    TextView textView1;
    TextView t1;
    TextView t2;
    TextView textView2;
    private BarChart barChart;
    private RequestQueue requestQueue;
    url ob = new url();
    String URL = "http://"+ob.url+"/PHP/pmbds.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interqnr4);
        btn = findViewById(R.id.btn);
        textView1 = findViewById(R.id.pre);
        textView2 = findViewById(R.id.post);
        barChart = findViewById(R.id.barChart);
        t1 = findViewById(R.id.i1);
        t2= findViewById(R.id.i2);
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(interqnr4.this,mbds.class);
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
        Log.d("JSON Response", status);

        if ("success".equals(status)) {
//            Intent intent = new Intent(Qnr1.this, interqnr1.class);
//
//            //intent.putExtra("id",username);
//
//            startActivity(intent);
            if(!TextUtils.isEmpty(q1)){
                int n =Integer.parseInt(q1);
//                if(n>=1&&n<=6){
//                    t1.setText("Normal sleep");
//                    t1.setTextColor(Color.parseColor("#FF0000"));
//                }else if(n>=7&&n<=8){
//                    t1.setText("Average sleepiness");
//                    t1.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else{
//                    t1.setText("Abnormal Sleepiness(possibly pathalogic)");
//                    t1.setTextColor(Color.parseColor("#FF0000"));
//                }
                if(n == 0){
                    t1.setText("Nothing at all");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n == 1){
                    t1.setText("Very , Very Slight");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }
                else  if(n == 2){
                    t1.setText("Very Slight");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n == 3){
                    t1.setText("Slight");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n == 4){
                    t1.setText("Moderate");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n == 5){
                    t1.setText("Somewhat severe");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n == 6){
                    t1.setText("Severe");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n == 7){
                    t1.setText("Very Severe");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }
                else  if(n == 8){
                    t1.setText("Very , Very Severe(almost maximal)");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }
                else  if(n == 9){
                    t1.setText("Maximal");
                    t1.setTextColor(Color.parseColor("#FF0000"));
                }

            }
            if(!TextUtils.isEmpty(q2)){
                int n1 =Integer.parseInt(q2);
//                if(n1>=1&&n1<=6){
//                    t2.setText("Normal sleep");
//                    t2.setTextColor(Color.parseColor("#FF0000"));
//                }else if(n1>=7&&n1<=8){
//                    t2.setText("Average sleepiness");
//                    t2.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else{
//                    t2.setText("Abnormal Sleepiness(possibly pathalogic)");
//                    t2.setTextColor(Color.parseColor("#FF0000"));
//                }

                if(n1 == 0){
                    t2.setText("Nothing at all");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n1 == 1){
                    t2.setText("Very , Very Slight");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }
                else  if(n1 == 2){
                    t2.setText("Very Slight");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n1 == 3){
                    t2.setText("Slight");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n1 == 4){
                    t2.setText("Moderate");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n1 == 5){
                    t2.setText("Somewhat severe");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n1 == 6){
                    t2.setText("Severe");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }else  if(n1 == 7){
                    t2.setText("Very Severe");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }
                else  if(n1 == 8){
                    t2.setText("Very , Very Severe(almost maximal)");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }
                else  if(n1 == 9){
                    t2.setText("Maximal");
                    t2.setTextColor(Color.parseColor("#FF0000"));
                }

            }




            textView1.setText(q1);
            textView2.setText(q2);
            processJSONData();



        } else if ("failure".equals(status)) {
            Toast.makeText(interqnr4.this, "Invalid login", Toast.LENGTH_SHORT).show();
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
    private void processJSONData() {
        String text1 = textView1.getText().toString();
        String text2 = textView2.getText().toString();

        // Check for empty or null strings before parsing
        if (!TextUtils.isEmpty(text1) && !TextUtils.isEmpty(text2)) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            barChart = findViewById(R.id.barChart);
            // Add the entries and labels
            entries.add(new BarEntry(0, Float.parseFloat(textView1.getText().toString()))); // 's1' value for 'pre'
            entries.add(new BarEntry(1, Float.parseFloat(textView2.getText().toString()))); // 's2' value for 'post'
            labels.add("pre");
            labels.add("post");

            BarDataSet dataSet = new BarDataSet(entries, "Scores");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            BarData barData = new BarData(dataSet);

            barChart.setData(barData);
            dataSet.setColors(new int[] {Color.parseColor("#FFCCCB"), Color.parseColor("#90EE90")});

            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis yAxisLeft = barChart.getAxisLeft();
            YAxis yAxisRight = barChart.getAxisRight();
            yAxisLeft.setAxisMinimum(0);
            yAxisRight.setAxisMinimum(0);

            barChart.setFitBars(true);
            barChart.getDescription().setEnabled(false);
            barChart.animateY(1000);
            barChart.invalidate();
        }
    }
}