package com.example.lavka.service;

import com.example.lavka.model.Category;
import com.example.lavka.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LavkaClient {

    @GET("api/food/categories")
    Call<List<Category>> categories();

    @GET("api/food/{categoryId}/products")
    Call<List<Product>> products(@Path("categoryId") Long categoryId);

}
