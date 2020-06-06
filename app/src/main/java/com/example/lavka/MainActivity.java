package com.example.lavka;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.lavka.model.Product;
import com.example.lavka.model.User;
import com.example.lavka.service.RestService;
import com.example.lavka.service.Singleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragmet(new LoginFragment());
        pagerAdapter.addFragmet(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
        
        getAllProductsList();
    }

    private void getAllProductsList() {
        if (Singleton.getInstance().getProducts() == null) {
            Call<List<Product>> call = RestService.client.getProducts();

            call.enqueue(new Callback<List<Product>>() {

                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    List<Product> products = response.body();

                    Singleton.getInstance().setProducts(products);
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });

        }
    }

    public void login(View view) {
        EditText login = findViewById(R.id.login_login);

        loginUserAndChangeActivity(login.getText().toString());
    }

    public void goToSurvey(View view) {
        EditText login = findViewById(R.id.register_login);
        EditText password = findViewById(R.id.register_password);

        User user = new User(login.getText().toString(), password.getText().toString());

        signUpUserAndChangeActivity(user);
    }

    private void signUpUserAndChangeActivity(User user) {
        Call<User> call = RestService.client.signUp(user);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                user.setRestrictionsOn(true);
                Singleton.getInstance().setUser(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
    }

    private void onLoginFail() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("Помилка");
        alertDialog.setMessage("Логін чи пароль введені неправильно!");


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ОК", (dialog, which) -> {

        });

        alertDialog.show();
    }

    private void loginUserAndChangeActivity(String login) {
        Call<User> call = RestService.client.login(login);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    user.setRestrictionsOn(true);
                    Singleton.getInstance().setUser(user);

                    //openNavigation();
                } else {
                    onLoginFail();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onLoginFail();
            }
        });

        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragmet(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}
