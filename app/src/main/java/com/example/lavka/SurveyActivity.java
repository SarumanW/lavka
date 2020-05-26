package com.example.lavka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.example.lavka.model.SurveyResponse;
import com.example.lavka.model.User;
import com.example.lavka.service.RestService;

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
    }

    public void onSurveySubmit(View view) {
        Map<String, List<String>> surveyAnswers = fillAnswers();

        User user = (User) getIntent().getSerializableExtra("user");

        sendSurveyAnswers(user, surveyAnswers);
    }

    private void sendSurveyAnswers(User user, Map<String, List<String>> surveyAnswers) {
        SurveyResponse surveyResponse = new SurveyResponse(user.getId(), surveyAnswers);

        Call<User> call = RestService.client.submitSurvey(surveyResponse);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                startNavigationActivity(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void startNavigationActivity(User user) {
        Intent intent = new Intent(this, NavigationActivity.class);

        intent.putExtra("id", user.getId());

        startActivity(intent);
    }

    private Map<String, List<String>> fillAnswers() {
        Map<String, List<String>> surveyAnswers = new HashMap<>();

        List<String> dietsAnswers = new ArrayList<>();

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

        for (CheckBox checkBox : dietsCheckboxes) {
            if (checkBox.isChecked()) {
                dietsAnswers.add(checkBox.getText().toString());
            }
        }

        surveyAnswers.put("Оберіть пункти, які найкраще вас описують:", dietsAnswers);

        List<String> productsAnswers = new ArrayList<>();

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

        for (CheckBox checkBox : productsCheckboxes) {
            if (checkBox.isChecked()) {
                productsAnswers.add(checkBox.getText().toString());
            }
        }

        surveyAnswers.put("Оберіть продукти, яких ви намагаєтесь уникати:", productsAnswers);

        return surveyAnswers;
    }
}
