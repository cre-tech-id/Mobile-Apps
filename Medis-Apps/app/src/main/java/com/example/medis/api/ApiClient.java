package com.example.medis.api;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiService service;
    public static ApiService getService() {
        if (service == null) {
            String BASE_URL = "https://rsudbangil.biz.id/api/";
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.client(httpClient.build()).build();
            service = retrofit.create(ApiService.class);
        }
        return service;
    }
}