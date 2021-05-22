package com.example.youtubedownloader.api;



import com.example.youtubedownloader.VideoRequest;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiClient {

    @GET("/downloader")
    Call<ResponseBody> getVideo();
    @POST("/downloader")
    Call<ResponseBody> getVideoFromUrl(@Body VideoRequest videoRequest);


}
