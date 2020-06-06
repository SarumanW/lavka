package com.example.lavka;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lavka.model.Product;

import java.io.InputStream;
import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private FragmentActivity context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = (FragmentActivity) context;
        this.productList = products;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = inflater.inflate(R.layout.product_grid_item, null);

            TextView textView = gridView
                    .findViewById(R.id.product_name);

            String name;
            if (productList.get(position).getShortName() != null) {
                name = productList.get(position).getShortName();
            } else {
                name = productList.get(position).getName();
            }

            textView.setText(name);

            ImageView imageView = gridView
                    .findViewById(R.id.product_img);

            imageView.setOnClickListener(v -> {
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();

                Bundle args = new Bundle();
                args.putSerializable("product", productList.get(position));
                productDetailsFragment.setArguments(args);

                context.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        productDetailsFragment).commit();
            });

            String imagePath = productList.get(position).getImagePath();
            new DownloadImageTask(imageView).execute(imagePath);
        } else {
            gridView = convertView;
        }

        return gridView;
    }

    @SuppressLint("StaticFieldLeak")
    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;

            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
