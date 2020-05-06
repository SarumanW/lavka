package com.example.lavka.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    @SerializedName("categoryNumber")
    @Expose
    private Long categoryNumber;

    @SerializedName("categoryId")
    @Expose
    private Long categoryId;

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    @SerializedName("parentCategory")
    @Expose
    private Category parentCategory;

    @SerializedName("subCategories")
    @Expose
    private List<Category> subCategories;

    public Long getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(Long categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
}
