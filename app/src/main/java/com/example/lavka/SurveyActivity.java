package com.example.lavka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.example.lavka.model.SurveyResponse;
import com.example.lavka.model.User;
import com.example.lavka.service.RestService;
import com.example.lavka.service.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        User user = Singleton.getInstance().getUser();
        if (user != null) {
            List<String> diets = user.getDiets();
            List<String> restrictedItems = user.getRestrictedItems();

            List<CheckBox> dietCheckboxes = this.getDietCheckboxes();
            for (CheckBox checkBox : dietCheckboxes) {
                if (diets.contains(checkBox.getText().toString())) {
                    checkBox.setChecked(true);
                }
            }

            List<CheckBox> productCheckboxes = this.getProductCheckboxes();
            for (CheckBox checkBox : productCheckboxes) {
                if (restrictedItems.contains(checkBox.getText().toString())) {
                    checkBox.setChecked(true);
                }
            }
        }
    }

    public void onSurveySubmit(View view) {
        Map<String, List<String>> surveyAnswers = fillAnswers();

        User user = Singleton.getInstance().getUser();

        sendSurveyAnswers(user, surveyAnswers);
    }

    private void sendSurveyAnswers(User user, Map<String, List<String>> surveyAnswers) {
        SurveyResponse surveyResponse = new SurveyResponse(user.getId(), surveyAnswers);

        Call<User> call = RestService.client.submitSurvey(surveyResponse);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                user.setRestrictionsOn(true);
                Singleton.getInstance().setUser(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Error");
            }
        });

        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    private Map<String, List<String>> fillAnswers() {
        Map<String, List<String>> surveyAnswers = new HashMap<>();

        List<String> dietsAnswers = new ArrayList<>();

        List<CheckBox> dietsCheckboxes = this.getDietCheckboxes();

        for (CheckBox checkBox : dietsCheckboxes) {
            if (checkBox.isChecked()) {
                dietsAnswers.add(checkBox.getText().toString());
            }
        }

        surveyAnswers.put("Оберіть пункти, які найкраще вас описують:", dietsAnswers);

        List<String> productsAnswers = new ArrayList<>();

        List<CheckBox> productsCheckboxes = this.getProductCheckboxes();

        for (CheckBox checkBox : productsCheckboxes) {
            if (checkBox.isChecked()) {
                productsAnswers.add(checkBox.getText().toString());
            }
        }

        surveyAnswers.put("Оберіть продукти, яких ви намагаєтесь уникати:", productsAnswers);

        return surveyAnswers;
    }

    private List<CheckBox> getDietCheckboxes() {
        List<CheckBox> dietsCheckboxes = new ArrayList<>();

        dietsCheckboxes.add(findViewById(R.id.weightloss));
        dietsCheckboxes.add(findViewById(R.id.paleo));
        dietsCheckboxes.add(findViewById(R.id.mediterrian));
        dietsCheckboxes.add(findViewById(R.id.keto));
        dietsCheckboxes.add(findViewById(R.id.diabet));
        dietsCheckboxes.add(findViewById(R.id.vegan));
        dietsCheckboxes.add(findViewById(R.id.vegetatian));
        dietsCheckboxes.add(findViewById(R.id.pesk));
        dietsCheckboxes.add(findViewById(R.id.glutenfree));
        dietsCheckboxes.add(findViewById(R.id.milkfree));
        dietsCheckboxes.add(findViewById(R.id.saltfree));

        return dietsCheckboxes;
    }

    private List<CheckBox> getProductCheckboxes() {
        List<CheckBox> productsCheckboxes = new ArrayList<>();

        productsCheckboxes.add(findViewById(R.id.eggplant));
        productsCheckboxes.add(findViewById(R.id.mayonaise));
        productsCheckboxes.add(findViewById(R.id.mushrooms));
        productsCheckboxes.add(findViewById(R.id.nuts));
        productsCheckboxes.add(findViewById(R.id.onion));
        productsCheckboxes.add(findViewById(R.id.seafood));
        productsCheckboxes.add(findViewById(R.id.soy));
        productsCheckboxes.add(findViewById(R.id.olive));
        productsCheckboxes.add(findViewById(R.id.eggs));
        productsCheckboxes.add(findViewById(R.id.subproducts));
        productsCheckboxes.add(findViewById(R.id.citrus));

        return productsCheckboxes;
    }
}
