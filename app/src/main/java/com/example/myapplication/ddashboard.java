package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ddashboard extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView img;
    ActionBarDrawerToggle toggle;
    String value;
    Button viewall;

    // Declare the BarChart and RequestQueue variables
    private BarChart barChart;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private List<PatientInfo> dataList;
    private CustomAdapter adapter;
    url ob = new url();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddashboard);

        // Extract the "id" value from the intent
        Intent intent1 = getIntent();
        value = intent1.getStringExtra("id");

        // Initialize the BarChart and RequestQueue
        barChart = findViewById(R.id.barChart);
        requestQueue = Volley.newRequestQueue(this);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new CustomAdapter(dataList);
        recyclerView.setAdapter(adapter);

        // Make an HTTP request to your PHP script
        String url = "http://"+ob.url+"/PHP/graph.php"; // Replace with the correct URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        processJSONData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonArrayRequest);

        // Rest of your code for the navigation drawer, toolbar, and ImageView
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(value);

        drawerLayout = findViewById(R.id.dl);
        navigationView = findViewById(R.id.Navigationview);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        viewall = findViewById(R.id.viewall);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ddashboard.this,tdayAppo.class);
                startActivity(intent);
            }
        });




     fetchfromPHP();
        img = findViewById(R.id.im1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ddashboard.this, dprofile.class);
                in.putExtra("id", value);
                startActivity(in);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.P) {
                    Intent intent = new Intent(ddashboard.this, addpatient.class);
                    intent.putExtra("id", value);
                    startActivity(intent);
                } else if (id == R.id.AB) {
                    Intent intent = new Intent(ddashboard.this, videos.class);
                    intent.putExtra("id", value);
                    startActivity(intent);
                } else if (id == R.id.QF) {
                    Intent intent = new Intent(ddashboard.this, status.class);
                    startActivity(intent);
                } else if (id == R.id.BS) {
                    Intent intent = new Intent(ddashboard.this, pList.class);
                    intent.putExtra("id", value);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
    protected void onResume() {
        super.onResume();
        fetchfromPHP(); // Reload data from PHP script
    }

    private void processJSONData(JSONArray jsonArray) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList();

        try {
            int length = jsonArray.length();

            for (int i = 0; i < length; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String month = object.getString("month");
                int patients = object.getInt("patients");
                entries.add(new BarEntry(i, patients));
                labels.add(month);
            }

            BarDataSet dataSet = new BarDataSet(entries, "Patients");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            BarData barData = new BarData(dataSet);
            barData.setBarWidth(0.5f);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private List<PatientInfo> dataList;

        public CustomAdapter(List<PatientInfo> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout1, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PatientInfo patient = dataList.get(position);
            Log.d("lastTag", patient.getId());
            Log.d("lastTag", patient.getGender());

            if (holder.idTextView != null) {
                holder.idTextView.setText("ID: " + (patient.getId() != null ? patient.getId() : ""));
            }
            if (holder.nameTextView != null) {
                holder.nameTextView.setText("Name: " + (patient.getName() != null ? patient.getName() : ""));
            }
            if (holder.genderTextView != null) {
                holder.genderTextView.setText("Date: " + (patient.getGender() != null ? patient.getGender() : ""));
            }
            if (holder.phnoTextView != null) {
                holder.phnoTextView.setText("Phno: " + (patient.getPhno() != null ? patient.getPhno() : ""));
            }

            if (holder.profileImageView != null && patient.getProfilePhoto() != null
                    && !patient.getProfilePhoto().isEmpty()) {
                String completeImageUrl = "http://"+ob.url+"/php/" + patient.getProfilePhoto();
                Picasso.get().load(completeImageUrl).into(holder.profileImageView);
            } else {
                // Set a placeholder image or handle empty profilePhoto
                // For example, you can set a default placeholder image like this:
                holder.profileImageView.setImageResource(R.drawable.person);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedItem = patient.getId() != null ? patient.getId() : "";

                    Intent intent = new Intent(ddashboard.this, patientinfoPrint.class);
                    intent.putExtra("item", selectedItem);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView idTextView, nameTextView, genderTextView, phnoTextView;
            ImageView profileImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                idTextView = itemView.findViewById(R.id.id);
                nameTextView = itemView.findViewById(R.id.name);
                genderTextView = itemView.findViewById(R.id.date);
                phnoTextView = itemView.findViewById(R.id.phno);
                profileImageView = itemView.findViewById(R.id.profile);
            }
        }
    }

    public void fetchfromPHP() {
        String url = "http://"+ob.url+"/PHP/todayappo.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            dataList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.optString("pid");
                                String name = jsonObject.optString("name");
                                String gender = jsonObject.optString("date");
                                String phno = jsonObject.optString("P_phno");
                                String profilePhoto = jsonObject.optString("img");
                                Log.d("tag1", id);
                                Log.d("tag1", name);
                                Log.d("tag1", gender);
                                Log.d("tag1", phno);

                                dataList.add(new PatientInfo(id, name, gender, phno, profilePhoto));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error);
            }
        });

        queue.add(stringRequest);
    }

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }




}
