package com.example.lavka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.lavka.model.Category;
import com.example.lavka.service.RestService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    GridView gridView;
    private static final String[] mContacts = {"Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Полосатик", "Матроскин", "Лизка", "Томосина",
            "Бегемот", "Чеширский", "Дивуар", "Тигра", "Лаура"};

    public ProductFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        gridView = v.findViewById(R.id.gridView);
        //setupAdapter();

        getCategoriesList();

        return v;
    }

    private void getCategoriesList() {
        Call<List<Category>> call = RestService.client.categories();

        call.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> body = response.body();

                loopCategories(body, null);

                List<Category> categories = body.stream()
                        .filter(c -> c.getSubCategories().size() > 0)
                        .collect(Collectors.toList());

                setupCategoriesAdapter(categories);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private static void loopCategories(List<Category> categories, Category parentCategory) {
        for (Category category : categories) {
            category.setParentCategory(parentCategory);

            if (category.getSubCategories() != null) {
                loopCategories(category.getSubCategories(), category);
            }
        }
    }

    private void setupCategoriesAdapter(List<Category> categories) {
        gridView.setAdapter(new CategoryAdapter(getActivity(), categories));
    }

    private void setupAdapter() {
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
