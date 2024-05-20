package com.example.travelapp.API;

import com.example.travelapp.Domain.UserDomain;
import com.example.travelapp.UserRequest;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/api/users/all")
    Call<List<UserDomain>> getUserData();
    @POST("/api/users/login")
    Call<String> loginUser(@Body RequestBody requestBody);

    @GET("/api/users/user")
    Call<UserDomain> getUserByEmail(@Query("email") String email);
    @POST("/api/users/add")
    Call<UserDomain> addUser(@Body UserRequest userRequest);


}
