package com.example.lavka;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lavka.model.User;
import com.example.lavka.service.RestService;
import com.example.lavka.service.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    AlertDialog alertDialog;
    EditText editText;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = Singleton.getInstance().getUser();

        CheckBox restrictionsCheckBox = v.findViewById(R.id.restrictions);
        restrictionsCheckBox.setChecked(user.isRestrictionsOn());

        restrictionsCheckBox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    user.setRestrictionsOn(isChecked);

                    Singleton.getInstance().setUser(user);
                }
        );

        TextView loginView = v.findViewById(R.id.login);
        loginView.setText(user.getLogin());

        loginView.setOnClickListener(v1 -> {
            alertDialog = new AlertDialog.Builder(this.getContext()).create();
            editText = new EditText(this.getContext());

            editText.setText(loginView.getText());

            alertDialog.setTitle("Редагувати логін");
            alertDialog.setView(editText);

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти", (dialog, which) -> {
                loginView.setText(editText.getText());

                user.setLogin(editText.getText().toString());

                updateUser(user);
            });

            alertDialog.show();
        });

        return v;
    }

    private void updateUser(User user) {
        Call<User> call = RestService.client.updateUser(user);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Singleton.getInstance().setUser(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
