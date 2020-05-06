package com.example.lavka;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.lavka.model.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private FragmentActivity context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> products) {
        this.context = (FragmentActivity) context;
        this.categoryList = products;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = new Button(context);
            button.setText(categoryList.get(position).getCategoryName());
        } else {
            button = (Button) convertView;
        }
        button.setId(position);

        button.setOnClickListener(v -> {
            ProductListFragment productListFragment = new ProductListFragment();

            Bundle args = new Bundle();
            args.putLong("categoryNumber", categoryList.get(position).getCategoryNumber());
            productListFragment.setArguments(args);

            context.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    productListFragment).commit();
        });

        return button;
    }
}
