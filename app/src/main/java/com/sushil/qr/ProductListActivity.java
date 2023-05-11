package com.sushil.qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sushil.qr.APIs.RetrofitClient;
import com.sushil.qr.databinding.ActivityProductListBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {
    ActivityProductListBinding b;
    private ProductViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityProductListBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productList();
    }

    private void productList() {
        viewModel.getProductList();
        viewModel.products.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                ProductListAdapter productListAdapter = new ProductListAdapter(products, ProductListActivity.this);
                b.rvProductList.setAdapter(productListAdapter);
            }
        });
    }

}