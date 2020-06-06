package com.example.lavka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.lavka.model.Product;
import com.example.lavka.service.Singleton;

import java.util.List;
import java.util.stream.Collectors;

public class ProductListFragment extends Fragment {

    GridView gridView;

    public ProductListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_list, container, false);
        gridView = v.findViewById(R.id.gridView);

        long categoryId = getArguments().getLong("categoryId");

        List<Product> userProducts;

        if (Singleton.getInstance().isRestrictionsOn()) {
            userProducts = Singleton.getInstance().getUserProductsByCategoryId(categoryId);
        } else {
            userProducts = Singleton.getInstance().getProducts().stream()
                    .filter(p -> String.valueOf(p.getCategoryId()).startsWith(String.valueOf(categoryId)))
                    .collect(Collectors.toList());
        }

        if (userProducts.isEmpty()) {
            TextView textView = v.findViewById(R.id.noProductsText);
            textView.setVisibility(View.VISIBLE);
        } else {
            setupProductsAdapter(userProducts);
        }

        return v;
    }

    private void setupProductsAdapter(List<Product> products) {
        gridView.setAdapter(new ProductAdapter(getActivity(), products));
    }

}
