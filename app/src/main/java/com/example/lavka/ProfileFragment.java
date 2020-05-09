package com.example.lavka;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    AlertDialog alertDialog;
    EditText editText;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView loginView = v.findViewById(R.id.login);

        loginView.setOnClickListener(v1 -> {
            alertDialog = new AlertDialog.Builder(this.getContext()).create();
            editText = new EditText(this.getContext());

            editText.setText(loginView.getText());

            alertDialog.setTitle("Редагувати логін");
            alertDialog.setView(editText);

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Зберегти", (dialog, which) -> {
                loginView.setText(editText.getText());
            });

            alertDialog.show();
        });



        return v;
    }

}
