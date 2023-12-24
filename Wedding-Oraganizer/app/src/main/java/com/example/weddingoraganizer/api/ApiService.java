package com.example.weddingoraganizer.api;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import okhttp3.ResponseBody;


public interface ApiService {
    @POST("login")
    Call<LoginResponse> postLogin(@Body BodyLogin bodyLogin);

    @GET("penyedia")
    Call<StoreItem> getStore();
}

