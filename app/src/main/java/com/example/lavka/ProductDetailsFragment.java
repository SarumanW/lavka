package com.example.lavka;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lavka.model.Product;
import com.example.lavka.model.User;
import com.example.lavka.service.RestService;
import com.example.lavka.service.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment {

    Product product;

    public ProductDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        product = (Product) getArguments().getSerializable("product");

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

        Button likeButton = v.findViewById(R.id.addToLiked);

        if (Singleton.getInstance().getUser().getLikedProducts().contains(product)) {
            setUnlikeButton(likeButton);
        } else {
            setLikeButton(likeButton);
        }

        return v;
    }

    private void setUnlikeButton(Button button) {
        button.setText(R.string.unlike_product);

        button.setOnClickListener((buttonView) -> {
            unlikeProductAndShowAlert(Singleton.getInstance().getUser(), product.getId());

            setLikeButton(button);
        });
    }

    private void setLikeButton(Button button) {
        button.setText(R.string.like_product);

        button.setOnClickListener((buttonView) -> {
            likeProductAndShowAlert(Singleton.getInstance().getUser(), product.getId());

            setUnlikeButton(button);
        });
    }

    private void likeProductAndShowAlert(User user, Long productId) {
        Call<User> call = RestService.client.likeProduct(user, productId);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    Singleton.getInstance().setUser(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();

        alertDialog.setTitle("Успіх!");
        alertDialog.setMessage("Продукт було додано до списку улюблених");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ОК", (dialog, which) -> {

        });

        alertDialog.show();
    }

    private void unlikeProductAndShowAlert(User user, Long productId) {
        Call<User> call = RestService.client.unlikeProduct(user, productId);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    Singleton.getInstance().setUser(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();

        alertDialog.setTitle("Успіх!");
        alertDialog.setMessage("Продукт було видалено із списку улюблених");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ОК", (dialog, which) -> {

        });

        alertDialog.show();
    }

}
