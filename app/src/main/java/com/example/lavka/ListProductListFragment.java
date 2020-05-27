package com.example.lavka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lavka.model.Product;
import com.example.lavka.service.Singleton;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ListProductListFragment extends Fragment {

    public ListProductListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_product_list, container, false);

        List<String> userProducts = Singleton.getInstance().getUserProducts()
                .stream()
                .map(Product::getShortName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        ListView productsList = v.findViewById(R.id.productsList);

        ArrayAdapter<String> adapter = new ArrayAdapter(v.getContext(),
                android.R.layout.simple_list_item_1, userProducts);

        productsList.setAdapter(adapter);

        productsList.setOnItemClickListener((parent, v1, position, id) -> {

        });

        return v;
    }

}
