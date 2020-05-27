package com.example.lavka.service;

import com.example.lavka.model.Product;
import com.example.lavka.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class Singleton {

    private User user;

    private Singleton() {
    }

    private static class SingletonHolder {
        private static final Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getUserProductsByCategoryId(Long categoryId) {
        return user.getProducts().stream()
                .filter(p -> p.getCategoryId().equals(categoryId))
                .collect(Collectors.toList());

    }

    public List<Product> getUserProducts() {
        return user.getProducts();
    }

    public User getUser() {
        return user;
    }
}