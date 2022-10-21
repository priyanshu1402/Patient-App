package com.example.pateintapp;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/login")
    Call<loginr> executeLogin(@Body HashMap<String , String> map);

}
