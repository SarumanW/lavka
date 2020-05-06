package com.example.lavka.service;

import com.example.lavka.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface LavkaClient {

    //@Headers("Accept: application/json")
    @GET("api/usda/categories")
    Call<List<Category>> categories();

//    @Headers("Accept: application/json")
//    @GET("api/products/categories")
//    Call<List<Category>> categories();
}
