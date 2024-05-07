package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class videoDisplay1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<String> videoUrls = new ArrayList<>();
    static url ob = new url();
    private static final String PHP_ENDPOINT_URL = "http://"+ob.url+"/PHP/displayVideo.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(videoUrls, this);
        recyclerView.setAdapter(videoAdapter);




        fetchVideosFromServer();
    }

    private void fetchVideosFromServer() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, PHP_ENDPOINT_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("urlsuuu", String.valueOf(response));
                        try {
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject video = response.getJSONObject(i);
                                    String videoUrl = video.getString("url");
                                    videoUrls.add(videoUrl);
                                }
                                videoAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(videoDisplay1.this, "Error fetching videos" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Initialize the Volley request queue and add the request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    public static class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

        private List<String> videoUrls;
        private AppCompatActivity activity;

        public VideoAdapter(List<String> videoUrls, AppCompatActivity activity) {
            this.videoUrls = videoUrls;
            this.activity = activity;
        }

        @NonNull
        @Override
        public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
            return new VideoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
            String videoUrl = videoUrls.get(position);
            generateVideoThumbnail(videoUrl, holder.videoThumbnail);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, VideoPlayer.class);
                    intent.putExtra("VIDEO_URL", "http://"+ob.url+"/PHP/" + videoUrl);
                    activity.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return videoUrls.size();
        }

        public static class VideoViewHolder extends RecyclerView.ViewHolder {
            ImageView videoThumbnail;

            public VideoViewHolder(View itemView) {
                super(itemView);
                videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            }
        }

        private void generateVideoThumbnail(String videoUrl, ImageView thumbnailImageView) {
            Glide.with(activity)
                    .load("http://"+ob.url+"/PHP/" + videoUrl)
                    .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                    .frame(1000000) // Specify the time position in microseconds (1 sec in this case)
                    .into(thumbnailImageView);
        }
    }
}
