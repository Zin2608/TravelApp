package com.example.travelapp.API;

import com.example.travelapp.Domain.PropertyDomain;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PropertyApi {
    @GET("/api/allPro")
    Call<List<PropertyDomain>> getProperties();

    @GET("/api/favoritePro")
    Call<List<PropertyDomain>> getFavoriteProperties();

    @POST("/api/updateFav")
    Call<Void> updateFavoriteStatus(@Query("title") String title, @Query("isFavorite") boolean isFavorite);

    @GET("/api/bookedProperties")
    Call<List<PropertyDomain>> getBookedProperties();

}
