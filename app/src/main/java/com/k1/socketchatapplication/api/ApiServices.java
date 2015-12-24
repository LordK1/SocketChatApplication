package com.k1.socketchatapplication.api;

import com.k1.socketchatapplication.model.User;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by k1 on 12/13/15.
 */
public interface ApiServices {
    @POST("/api/login")
    Call<User> loginUser(@Query("format") String format);

    @POST("/api/register")
    Call<User> registerUser(@Query("format") String format);
}
