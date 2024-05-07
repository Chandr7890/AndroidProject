package com.example.myapplication;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("upload_video.php") // Replace with your server endpoint for uploading
    Call<ResponseBody> uploadVideo(
            @Part MultipartBody.Part videoFile,
            @Part("title") RequestBody title // Additional parameters if needed
    );
}
