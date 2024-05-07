package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class dprofile extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private  TextView pname ;
    private Button btn;
    url ob = new url();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dprofile);
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");

        textView1= findViewById(R.id.tex1);
        textView2=findViewById(R.id.tex2);
        textView3=findViewById(R.id.tex3);
        textView4=findViewById(R.id.tex4);
        pname =findViewById(R.id.pName);

        // Make an HTTP request to your PHP script
        fetchStringFromPHP();
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dprofile.this,dprofileE.class);
                intent.putExtra("id",value);
                startActivity(intent);
            }
        });

    }

    private void fetchStringFromPHP() {
        Intent intent1 = getIntent();
        String value = intent1.getStringExtra("id");
        String url = "http://"+ob.url+"/PHP/dprofile.php"; // Replace with your PHP script's URL

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.d("JSON Response", response);
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                    // Check if the "D_name" field exists in the JSON response
                    if (jsonObject.has("D_id")) {
                        String status = jsonObject.get("D_id").getAsString();
                        textView1.setText(status);
                        status = jsonObject.get("D_name").getAsString();
                        textView2.setText(status);
                        pname.setText(status);
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
