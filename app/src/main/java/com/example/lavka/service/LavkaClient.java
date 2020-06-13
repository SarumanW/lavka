package com.example.lavka.service;

import com.example.lavka.model.Category;
import com.example.lavka.model.Product;
import com.example.lavka.model.SurveyResponse;
import com.example.lavka.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LavkaClient {

    @GET("api/food/categories")
    Call<List<Category>> categories();

    @GET("api/food/products")
    Call<List<Product>> getProducts();

    @POST("api/user/changePassword")
    Call<User> changePassword(@Body User user);

    @POST("api/user/changeLogin")
    Call<User> changeLogin(@Body User user);

    @POST("api/auth/sign-up")
    Call<User> signUp(@Body User user);

    @POST("api/survey/sendAnswers")
    Call<User> submitSurvey(@Body SurveyResponse surveyResponse);

    @GET("api/user/{login}")
    Call<User> login(@Path("login") String login);

    @POST("api/food/like/{productId}")
    Call<User> likeProduct(@Body User user, @Path("productId") Long productId);

    @POST("api/food/unlike/{productId}")
    Call<User> unlikeProduct(@Body User user, @Path("productId") Long productId);
}
