package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class pList extends AppCompatActivity {
    url ob = new url();
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<PatientInfo> dataList;
    private List<PatientInfo> filteredList;
    String url = "http://"+ob.url+"/PHP/pList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plist);

        dataList = new ArrayList<>();
        filteredList = new ArrayList<>(dataList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        fetchfromPHP();
    }

    private void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(dataList);
        } else {
            text = text.toLowerCase().trim();
            for (PatientInfo item : dataList) {
                if (item.getId() != null && item.getName() != null && item.getGender() != null && item.getPhno() != null) {
                    if (item.getId().toLowerCase().contains(text)
                            || item.getName().toLowerCase().contains(text)
                            || item.getGender().toLowerCase().contains(text)
                            || item.getPhno().toLowerCase().contains(text)) {
                        filteredList.add(item);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private List<PatientInfo> dataList;

        public CustomAdapter(List<PatientInfo> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PatientInfo patient = dataList.get(position);
            Log.d("lastTag", patient.getId());

            if (holder.idTextView != null) {
                holder.idTextView.setText("ID: " + (patient.getId() != null ? patient.getId() : ""));
            }
            if (holder.nameTextView != null) {
                holder.nameTextView.setText("Name: " + (patient.getName() != null ? patient.getName() : ""));
            }
            if (holder.genderTextView != null) {
                holder.genderTextView.setText("Gender: " + (patient.getGender() != null ? patient.getGender() : ""));
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

                    Intent intent = new Intent(pList.this, patientinfoPrint.class);
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
                genderTextView = itemView.findViewById(R.id.gender);
                phnoTextView = itemView.findViewById(R.id.phno);
                profileImageView = itemView.findViewById(R.id.profile);
            }
        }
    }

    public void fetchfromPHP() {
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
                                String id = jsonObject.optString("P_id");
                                String name = jsonObject.optString("P_name");
                                String gender = jsonObject.optString("P_gender");
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

class PatientInfo {
    private String id;
    private String name;
    private String gender;
    private String phno;
    private String profilePhoto;

    public PatientInfo(String id, String name, String gender, String phno, String profilePhoto) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phno = phno;
        this.profilePhoto = profilePhoto;
    }

    // Add getters for each field
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhno() {
        return phno;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }
}
