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

public class interqnr5 extends AppCompatActivity {
    Button btn,btn1,btn2 ;
    TextView textView1;
    TextView t1;
    TextView t2;
    TextView textView2;
    private BarChart barChart;

    private RequestQueue requestQueue;
    url ob = new url();
    String URL = "http://"+ob.url+"/PHP/inter5.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interqnr5);
        btn = findViewById(R.id.btn);
        btn1 =findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
//        textView1 = findViewById(R.id.pre);
//        textView2 = findViewById(R.id.post);
//        barChart = findViewById(R.id.barChart);
//        t1 = findViewById(R.id.i1);
//        t2= findViewById(R.id.i2);
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");
        Log.d("renarainterqnr1",value);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(interqnr5.this,pft.class);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(interqnr5.this,ppft.class);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(interqnr5.this,papft.class);
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
//
//
//
//
//            textView1.setText(q1);
//            textView2.setText(q2);
            processJSONData(q1,q2,q3,q4,q5,q6,q7,q8);



        } else if ("failure".equals(status)) {
            Toast.makeText(interqnr5.this, "Invalid login", Toast.LENGTH_SHORT).show();
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
    private void processJSONData(String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8) {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();

        // Comparing q1 with q5, q2 with q6, q3 with q7, and q4 with q8
        compareAndAddEntry(q1, q5, "pre vs q5", labels, entries);
        compareAndAddEntry(q2, q6, "q2 vs q6", labels, entries);
        compareAndAddEntry(q3, q7, "q3 vs q7", labels, entries);
        compareAndAddEntry(q4, q8, "q4 vs q8", labels, entries);

        // Setting up BarChart with data
        setupBarChart(labels, entries);
    }

    private void compareAndAddEntry(String value1, String value2, String label, ArrayList<String> labels, ArrayList<BarEntry> entries) {
        if (!TextUtils.isEmpty(value1) && !TextUtils.isEmpty(value2)) {
            entries.add(new BarEntry(entries.size(), Float.parseFloat(value1)));
            entries.add(new BarEntry(entries.size(), Float.parseFloat(value2)));
            labels.add(label);
        }
    }

    private void setupBarChart(ArrayList<String> labels, ArrayList<BarEntry> entries) {
        if (!entries.isEmpty()) {
            barChart = findViewById(R.id.barChart);

            BarDataSet dataSet = new BarDataSet(entries, "Comparison");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            BarData barData = new BarData(dataSet);
            dataSet.setColors(new int[] {Color.parseColor("#FFCCCB"), Color.parseColor("#90EE90")});

            barChart.setData(barData);

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
        } else {
            Toast.makeText(this, "No data available for comparison", Toast.LENGTH_SHORT).show();
        }
    }

}