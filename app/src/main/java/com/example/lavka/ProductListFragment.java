package com.example.lavka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.lavka.model.Product;
import com.example.lavka.service.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {

    GridView gridView;

    public ProductListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_list, container, false);
        gridView = v.findViewById(R.id.gridView);

        getProductsList(getArguments().getLong("categoryNumber"));

        return v;
    }

    private void getProductsList(Long categoryId) {
        Call<List<Product>> call = RestService.client.products(categoryId);

        call.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> body = response.body();

                setupProductsAdapter(body);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private void setupProductsAdapter(List<Product> products) {
        gridView.setAdapter(new ProductAdapter(getActivity(), products));
    }

}
