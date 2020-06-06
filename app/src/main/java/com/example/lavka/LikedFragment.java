package com.example.lavka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.lavka.model.Product;
import com.example.lavka.service.RestService;
import com.example.lavka.service.Singleton;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedFragment extends Fragment {

    GridView gridView;

    public LikedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_liked, container, false);
        gridView = v.findViewById(R.id.gridView);

        List<Product> likedProducts =
                Singleton.getInstance().getUser().getLikedProducts();

        if (likedProducts.isEmpty()) {
            TextView textView = v.findViewById(R.id.noLikedProductsText);
            textView.setVisibility(View.VISIBLE);
        } else {
            setupProductsAdapter(likedProducts);
        }
        return v;
    }

    private void setupProductsAdapter(List<Product> products) {
        gridView.setAdapter(new ProductAdapter(getActivity(), products));
    }
}
