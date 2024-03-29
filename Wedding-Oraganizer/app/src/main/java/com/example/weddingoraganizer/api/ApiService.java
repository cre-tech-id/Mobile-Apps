package com.example.weddingoraganizer.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiService {
    @POST("login")
    Call<LoginResponse> postLogin(@Body BodyLogin bodyLogin);

    @GET("paket")
    Call<StoreItem> getStore();

    @GET("event")
    Call<EventItem> getEvent();

    @POST("postpesanan")
    Call<PesananResponse> postPesanan(@Body BodyPesanan bodyPesanan);

    @GET("pesanan/{id}")
    Call<StatusPemesanan> getStatus(@Path("id") int postId);

    @GET("user/{id}")
    Call<GetUserId> getUserPerId(@Path("id") int postId);

    @Multipart
    @POST("updateprofile")
    Call<PostEditProfile> updatewgambar( @Part("data") RequestBody description,
                                        @Part MultipartBody.Part file);

    @Multipart
    @POST("updateprofile")
    Call<PostEditProfile> update(
            @Part("data") RequestBody description);

    @POST("rating")
    Call<RatingResponse> postRating(@Body BodyRating bodyRating);

    @GET("komentar/{id}")
    Call<GetKomentar> getKomentar(@Path("id") int postId);

    @POST("postkomentar")
    Call<PesananResponse> postKomentar(@Body BodyKomentar bodyKomentar);

    @GET("penyedia")
    Call<GetPenyedia> getPenyedia();

    @GET("listpembayaran/{id}")
    Call<GetListPembayaran> getListPembayaran(@Path("id") int postId);

    @Multipart
    @POST("uploadbukti")
    Call<PostEditProfile> uploadbukti( @Part("data") RequestBody description,
                                         @Part MultipartBody.Part file);
}

