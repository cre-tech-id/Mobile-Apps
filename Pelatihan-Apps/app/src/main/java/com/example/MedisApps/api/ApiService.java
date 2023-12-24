package com.medis.MedisApps.api;
import com.medis.MedisApps.DataModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiService {
    @Headers("Cache-Control: max-age=640000")
    @POST("login")
    Call<LoginResponse> postLogin(@Body BodyLogin bodyLogin);

    @GET("hasil")
    Call<DataModel> getResult();

}

