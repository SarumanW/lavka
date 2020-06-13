package com.example.lavka;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.lavka.model.SurveyResponse;
import com.example.lavka.model.User;
import com.example.lavka.service.RestService;
import com.example.lavka.service.Singleton;
import com.tooltip.Tooltip;

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

        this.setupHelperButtons();
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

    private void setupHelperButtons() {
        findViewById(R.id.weightlossB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта, розрахована на тих, хто має проблеми із зайвою вагою. " +
                                    "Переважно уникає випічки, десертів, солодких фруктів, мінімізує вуглеводи.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.paleoB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Назва походить від слова палеоліт, тобто дієта печерних людей, які займалися масливством та збиральництвом. " +
                                    "Основою є тваринний білок (без напівфабрикатів, бекону та сала). Виключаються злаки, молочні продукти, бобові, цукор та сіль.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.mediterrianB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта заснована на наукових даних про кращі показники здоров'я у представників середземноморського регіону. " +
                                    "Основою їх раціону є рослинна їжа: фрукти, овочі, горіхи, бобові, злаки. Виключається червоне м'ясо та солодощі.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.ketoB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Низьковуглеводна дієта, що передбачає 80% жирів у раціоні. Зобороняються борошняні продукти, рис, коренеплоди, цукор, кукурудза, " +
                                    "боби, фрукти (окрім ягід) та молоко.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.diabetB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта для людей, що хворіють діабетом та мають підвищений рівень глюкози у крові. У такому випадку усі продукти, " +
                                    "що містять цукор, виключаються із щоденного раціону. " +
                                    "Також варто уникати продуктів, що можуть викликати підвищення рівня глюкози: картопля, рис, баранина, білий хліб, алкоголь.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.veganB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта для людей, що повністю відмовились від усіх продуктів тваринного походження: м'яса, риби, яєць, молочних продуктів, меду, желатину.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.vegetatianB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта для людей, що частково відмовились від продуктів тваринного походження, не вживають " +
                                    "тільки м'ясо і рибу, але дозволяють вживання молочних продуктів та яєць.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.peskB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Окрема форма вегетаріанства, що дуже нагадує середземноморську дієту, оскільки існує повна відмова " +
                                    "від будь-якого м'яса, натомість перевага надається морепродуктам.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.glutenfreeB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта, що виключає із раціону продукти, що містять глютен: усі популярні крупи, квас, оцет, " +
                                    "ароматизатори, борошняні вироби та солодощі, макарони, усі готові соуси, консерви та напівфабрикати.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.milkfreeB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта, що виключає із раціону молочні продукти.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });

        findViewById(R.id.saltfreeB)
                .setOnClickListener(v -> {
                    Tooltip tooltip = new Tooltip.Builder(v)
                            .setText("Дієта, що виключає із раціону продукти із високим вмістом солі.")
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.TOP)
                            .setCornerRadius(8f)
                            .setDismissOnClick(true)
                            .show();
                });
    }

}
