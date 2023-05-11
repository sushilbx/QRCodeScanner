package com.sushil.qr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sushil.qr.databinding.ProductItemBinding;

import java.util.List;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    public List<Product>psy;
    public Context context;

    public ProductListAdapter(List<Product> list, Context context) {
        this.psy = list;
        this.context = context;
    }


    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductListAdapter.MyViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.MyViewHolder holder, final int position) {
        Product product =  psy.get(position);

        holder.b.tvName.setText(product.mst_product_name);
        holder.b.tvPrice.setText("Rs. "+product.item_price_details.final_price);
        Glide.with(context).load(product.mst_product_image).into(holder.b.ivImage);


    }

    @Override
    public int getItemCount() {
        return this.psy.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ProductItemBinding b;

        public MyViewHolder(ProductItemBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }
}





