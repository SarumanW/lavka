package com.example.lavka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lavka.model.Product;

import java.io.Serializable;

public class ProductDetailsFragment extends Fragment {


    public ProductDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Product product = (Product) getArguments().getSerializable("product");

        View v = inflater.inflate(R.layout.fragment_product_details, container, false);

        ImageView productImageView = v.findViewById(R.id.product_img);
        new ProductAdapter.DownloadImageTask(productImageView).execute(product.getImagePath());

        TextView productNameView = v.findViewById(R.id.product_name);
        productNameView.setText(product.getShortName());

        TextView fullProductNameView = v.findViewById(R.id.full_name);
        fullProductNameView.setText(product.getName());

        TextView expirationView = v.findViewById(R.id.expiration);
        expirationView.setText(product.getExpiration());

        TextView compositionView = v.findViewById(R.id.composition);
        compositionView.setText(product.getComposition());

        TextView proteinsView = v.findViewById(R.id.proteins);
        proteinsView.setText(product.getNutritionFacts().get("Protein"));

        TextView carbsView = v.findViewById(R.id.carbs);
        carbsView.setText(product.getNutritionFacts().get("Carbs"));

        TextView fatsView = v.findViewById(R.id.fats);
        fatsView.setText(product.getNutritionFacts().get("Fat"));

        TextView energyView = v.findViewById(R.id.energy);
        energyView.setText(product.getNutritionFacts().get("Energy"));

        return v;
    }

}
