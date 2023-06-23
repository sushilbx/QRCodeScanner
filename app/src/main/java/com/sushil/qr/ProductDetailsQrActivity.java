package com.sushil.qr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sushil.qr.databinding.ActivityProductDetailsQrBinding;
import com.sushil.qr.models.ProductDetailsModel;
import com.sushil.qr.models.QrModel;
import com.sushil.qr.models.SessionModel;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsQrActivity extends AppCompatActivity {
    ActivityProductDetailsQrBinding b;
    Context context;
    SessionManager sessionManager;
    SessionModel sessionModel;
    String auth;
    String url = "https://kkisan.karnataka.gov.in/KKISANQRAPI/api/GetProductDetail?ProductCode=";
    String productCode = "";
    String qrCode = "";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductDetailsQrActivity.this, FindQrActivity.class);
        intent.putExtra("pass", qrCode);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityProductDetailsQrBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        context = ProductDetailsQrActivity.this;
        sessionManager = new SessionManager(context);
        sessionModel = sessionManager.getLoginSession();
        auth = "Bearer " + sessionModel.getAccess_token();
        productCode = getIntent().getStringExtra("productCode");
        qrCode = getIntent().getStringExtra("pass");
        getData();
        b.back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void getData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("QRRESPONSE", response);
                Gson gson = new Gson();
                ProductDetailsModel model = gson.fromJson(response, ProductDetailsModel.class);

                Log.e("QRCode", model.ProductName);
                Log.e("ProductCode", model.ProductCode);
                Log.e("BatchNumber", model.CategoryName);

                b.tvProductName.setText(model.ProductName);
                b.tvProductCode.setText(model.ProductCode);
                b.tvProCode.setText(model.ProductCode);
                b.tvMfg.setText(model.ManufacturerName);
                b.tvSuplier.setText(model.SupplierName);
                b.tvCat.setText(model.CategoryName);
                b.tvSub.setText(model.SubCategoryName);
                b.tvProName.setText(model.ProductName);
                b.tvBrand.setText(model.BrandName);
                b.tvUmo.setText(model.UomID);
                b.tvWeight.setText(model.Weight);



            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + productCode, responseListener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("QRCode", productCode);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", auth);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
}