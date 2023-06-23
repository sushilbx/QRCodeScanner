package com.sushil.qr.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.sushil.qr.ProductDetailsQrActivity;
import com.sushil.qr.R;
import com.sushil.qr.databinding.SecondaryItemBinding;
import com.sushil.qr.models.SecondaryModel;

import java.util.List;

public class SecondaryAdapter extends RecyclerView.Adapter<SecondaryAdapter.MyViewHolder> {
    public List<SecondaryModel.SeecondaryLabelDetail> psy;
    public Context context;
    String qrCode;
    public SecondaryAdapter(List<SecondaryModel.SeecondaryLabelDetail> list, Context context, String qrCode) {
        this.psy = list;
        this.context = context;
        this.qrCode = qrCode;
    }

    @Override
    public SecondaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.secondary_item, parent, false);
        return new SecondaryAdapter.MyViewHolder(SecondaryItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final SecondaryAdapter.MyViewHolder holder, final int position) {
        SecondaryModel.SeecondaryLabelDetail model = psy.get(position);

        holder.b.tvQr.setText(model.QRCode);
        holder.b.tvProductCode.setText(model.ProductCode);
        holder.b.tvBatchNo.setText(model.BatchNumber);
        holder.b.tvSerialNo.setText(model.SerialNumber);
        holder.b.tvMfg.setText(model.ManufactureDate);
        holder.b.tvExp.setText(model.ExpiryDate);
        holder.b.mbNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsQrActivity.class);
                intent.putExtra("productCode", model.ProductCode);
                intent.putExtra("pass", qrCode);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.psy.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private SecondaryItemBinding b;

        public MyViewHolder(SecondaryItemBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }
}





