package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

  private RecyclerView recyclerView;
  private CustomAdapter adapter;
  private List<String> dataList;
  private List<String> filteredList;
  url ob = new url();
  String url = "http://"+ob.url+"/PHP/searchl.php";

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    dataList = new ArrayList<>();
    fetchfromPHP();


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
  }

  private void filter(String text) {
    filteredList.clear();
    if (text.isEmpty()) {
      filteredList.addAll(dataList);
    } else {
      text = text.toLowerCase().trim();
      for (String item : dataList) {
        if (item.toLowerCase().contains(text)) {
          filteredList.add(item);
        }
      }
    }
    adapter.notifyDataSetChanged();
  }

  class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<String> dataList;

    public CustomAdapter(List<String> dataList) {
      this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
      return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String item = dataList.get(position);

      // Set text and apply decoration
      holder.textView.setText(item);
      holder.textView.setTextColor(Color.BLACK); // Change text color if needed
      holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Change text size if needed

      // Apply background colors based on position or condition
      if (position % 2 == 0) { // Example: Set alternating background colors
        holder.itemView.setBackgroundColor(Color.LTGRAY);

      } else {
        holder.itemView.setBackgroundColor(Color.WHITE);
      }

      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // Get the clicked item
          String selectedItem = dataList.get(holder.getAdapterPosition());

          // Pass the selected item to the new activity or page
          Intent intent = new Intent(search.this, patientinfoPrint.class);
          intent.putExtra("item", selectedItem);
          startActivity(intent);
        }
      });






      // You can add more conditions and apply different background colors as needed
    }


    @Override
    public int getItemCount() {
      return dataList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
      TextView textView;

      public ViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(android.R.id.text1);
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
// Clear the dataList before adding new values
                  for (int i = 0; i < jsonArray.length(); i++) {
                    String patientId = jsonArray.getString(i);
                    dataList.add(patientId);
                  }

                  // Update the adapter with the new dataList
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

    // Add the request to the RequestQueue
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
