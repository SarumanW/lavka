package com.example.lavka.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("imagePath")
    @Expose
    @Nullable
    private String imagePath;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("shortName")
    @Expose
    @Nullable
    private String shortName;

    @SerializedName("expiration")
    @Expose
    @Nullable
    private String expiration;

    @SerializedName("composition")
    @Expose
    private String composition;

    @SerializedName("nutritionFacts")
    @Expose
    private Map<String, String> nutritionFacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@Nullable String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getShortName() {
        return shortName;
    }

    public void setShortName(@Nullable String shortName) {
        this.shortName = shortName;
    }

    @Nullable
    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(@Nullable String expiration) {
        this.expiration = expiration;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Map<String, String> getNutritionFacts() {
        return nutritionFacts;
    }

    public void setNutritionFacts(Map<String, String> nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }
}
