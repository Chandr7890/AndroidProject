package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView recyclerView;
    private videoDisplay1.VideoAdapter videoAdapter;
    private List<String> videoUrls = new ArrayList<>();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button b;
    ActionBarDrawerToggle toggle;
    static url ob = new url();
    private static final String PHP_ENDPOINT_URL = "http://"+ob.url+"/PHP/displayVideo.php";
    // @SuppressLint("MissingInflatedId")
    private static final String Channel_id = "channel";
    private static final int Notify_id = 100;
    private static final int INTERVAL_MINUTES = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);


        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.img,null);
        BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        //scheduleNotification();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon).setSmallIcon(R.drawable.img)
                    .setContentText("new message")
                    .setSubText("Hello Its time to do Exercises")
                    .setChannelId(Channel_id)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(Channel_id,"new channel",NotificationManager.IMPORTANCE_HIGH  ));
        }else{
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon).setSmallIcon(R.drawable.img)
                    .setContentText("Do the exercise regularly")
                    .setSubText("new message from app")
                    .build();
        }
        nm.notify(Notify_id,notification);




        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        videoAdapter = new videoDisplay1.VideoAdapter(videoUrls, this);
        recyclerView.setAdapter(videoAdapter);
        int spaceWidth = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        HorizontalSpaceItemDecoration itemDecoration = new HorizontalSpaceItemDecoration(spaceWidth);
        recyclerView.addItemDecoration(itemDecoration);


        fetchVideosFromServer();
        b=findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this,videoDisplay1.class);
                startActivity(intent);
            }
        });




        String videoPath = "android.resource://"+getPackageName()+"/"+R.raw.video;
        Uri uri = Uri.parse(videoPath);

        ;
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        Intent intent1= getIntent();
        String value = intent1.getStringExtra("id");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(value);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        navigationView = (NavigationView) findViewById(R.id.Navigationview);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent1= getIntent();
                String value = intent1.getStringExtra("id");
                int id = item.getItemId();
                if(id == R.id.P){
//                    Toast.makeText(dashboard.this,"Profile page",Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(dashboard.this, profile.class);
                    intent.putExtra("id",value);
                    startActivity(intent);
                }
                else if(id == R.id.AB){
//                    Toast.makeText(dashboard.this,"calender open",Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(dashboard.this, Calender.class);
                    intent.putExtra("id",value);
                    startActivity(intent);
                }
                else if(id == R.id.QF){
//                    Toast.makeText(dashboard.this,"query",Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(dashboard.this, interst.class);
                    intent.putExtra("id",value);
                    startActivity(intent);
                }
                else if(id == R.id.BS){
//                    Toast.makeText(dashboard.this,"booked slots",Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(dashboard.this, bokkedslots.class);
                    intent.putExtra("id",value);
                    startActivity(intent);
                }
                else if(id == R.id.mes){
//                    Toast.makeText(dashboard.this,"booked slots",Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
                    String url = "https://api.whatsapp.com/send?phone=" + "917337207416" + "&text=" + Uri.encode("HI");

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            }
        });


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
                        Toast.makeText(dashboard.this, "Error fetching videos" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Initialize the Volley request queue and add the request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }


    public static class VideoAdapter extends RecyclerView.Adapter<videoDisplay1.VideoAdapter.VideoViewHolder> {

        private List<String> videoUrls;
        private AppCompatActivity activity;

        public VideoAdapter(List<String> videoUrls, AppCompatActivity activity) {
            this.videoUrls = videoUrls;
            this.activity = activity;
        }

        @NonNull
        @Override
        public videoDisplay1.VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item1, parent, false);
            return new videoDisplay1.VideoAdapter.VideoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull videoDisplay1.VideoAdapter.VideoViewHolder holder, int position) {
            String videoUrl = videoUrls.get(position);
            generateVideoThumbnail(videoUrl, holder.videoThumbnail);
            holder.videoThumbnail.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 150));
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

        public class VideoViewHolder extends RecyclerView.ViewHolder {
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

    class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int horizontalSpaceWidth;

        public HorizontalSpaceItemDecoration(int horizontalSpaceWidth) {
            this.horizontalSpaceWidth = horizontalSpaceWidth;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int position = parent.getChildAdapterPosition(view);
            if (position != RecyclerView.NO_POSITION && position != 0) {
                outRect.left = horizontalSpaceWidth;
            } else {
                outRect.left = 0;
            }
        }
    }
}

