package com.downdemo.abhishekchandale.memorygamedemo.api;

import com.downdemo.abhishekchandale.memorygamedemo.model.ImageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abhishek on 6/17/2016.
 */
public interface RetrofitAPIService {

    @GET("services/feeds/photos_public.gne")
    public Call<ImageModel> getImageFromFlicker(@Query("id") String id, @Query("lang") String lang, @Query("format") String format, @Query("nojsoncallback") int nojsoncallback);
}
