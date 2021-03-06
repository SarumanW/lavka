package com.example.lavka.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("products")
    @Expose
    private List<Product> products;

    @SerializedName("likedProducts")
    @Expose
    private List<Product> likedProducts;

    @SerializedName("diets")
    @Expose
    private List<String> diets;

    @SerializedName("restrictedItems")
    @Expose
    private List<String> restrictedItems;

    private boolean isRestrictionsOn = true;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean isRestrictionsOn() {
        return isRestrictionsOn;
    }

    public void setRestrictionsOn(boolean restrictionsOn) {
        isRestrictionsOn = restrictionsOn;
    }

    public List<String> getRestrictedItems() {
        return restrictedItems;
    }

    public void setRestrictedItems(List<String> restrictedItems) {
        this.restrictedItems = restrictedItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public List<Product> getLikedProducts() {
        return likedProducts;
    }

    public void setLikedProducts(List<Product> likedProducts) {
        this.likedProducts = likedProducts;
    }
}
