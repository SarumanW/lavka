package com.example.lavka.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private Long id;

    private String login;

    private List<Product> products;

    private List<String> diets;
}
