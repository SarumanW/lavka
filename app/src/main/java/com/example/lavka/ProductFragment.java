package com.example.lavka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    GridView gridView;
    private static final String[] mContacts = {"Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Полосатик", "Матроскин", "Лизка", "Томосина",
            "Бегемот", "Чеширский", "Дивуар", "Тигра", "Лаура"};

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        gridView = v.findViewById(R.id.gridView);
        setupAdapter();
        return v;
    }

    void setupAdapter() {
        if (getActivity() == null || gridView == null) return;
        if (mContacts != null) {
            gridView.setAdapter(new ArrayAdapter<>(getActivity(),
                    R.layout.category_item, R.id.categoryText, mContacts));
        } else {
            gridView.setAdapter(null);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
