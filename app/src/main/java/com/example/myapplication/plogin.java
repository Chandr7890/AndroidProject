package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class plogin extends AppCompatActivity {
    Button btn;
    private EditText eid, epassword;

    private String id, password;
    private RelativeLayout parentLayout;
    url ob = new url();
    private String URL = "http://"+ ob.url+"/php/login.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plogin);
        id = password = "";
        eid = findViewById(R.id.txt);
        epassword = findViewById(R.id.txt1);
        btn = findViewById(R.id.button);
        parentLayout = findViewById(R.id.parentLayout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = eid.getText().toString();
                password = epassword.getText().toString();
                if (!id.equals("") && !password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                        // Inside the onResponse method of your StringRequest
                        @Override
                        public void onResponse(String response) {
                            Log.d("server response ", response);
                            try {
                                // Check if the response is a valid JSON object
                                if (response.startsWith("{") && response.endsWith("}")) {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String status = jsonResponse.getString("status");

                                    if (status.equals("success")) {
                                        Intent intent = new Intent(plogin.this, dashboard.class);
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                        finish();
                                    } else if (status.equals("failure")) {
                                        Toast.makeText(plogin.this, "Invalid login", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If the response is not a valid JSON object
                                    Toast.makeText(plogin.this, "Invalid server response", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(plogin.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(plogin.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("username",id);
                            data.put("password", password);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(plogin.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        }); // Missing closing parenthesis here

        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(); // Hide keyboard when parent layout is touched
                return false;
            }
        });
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}