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
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sushil.qr.adapters.SecondaryAdapter;
import com.sushil.qr.databinding.ActivityFindQrBinding;
import com.sushil.qr.databinding.ActivityLogininBinding;
import com.sushil.qr.models.QrModel;
import com.sushil.qr.models.SecondaryModel;
import com.sushil.qr.models.SessionModel;

import java.util.HashMap;
import java.util.Map;

public class FindQrActivity extends AppCompatActivity {
    ActivityFindQrBinding b;
    Context context;
    SessionManager sessionManager;
    SessionModel sessionModel;
    String auth;
    String url = "https://kkisan.karnataka.gov.in/KKISANQRAPI/api/GetPrimaryDetail?QRCode=";
    String urlList = "https://kkisan.karnataka.gov.in/KKISANQRAPI/api/GetSecondaryDetail?QRCode=";
    String qrCode = "";
    String mainQr = "";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FindQrActivity.this, QrActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityFindQrBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        context = FindQrActivity.this;
        sessionManager = new SessionManager(FindQrActivity.this);
        sessionModel = sessionManager.getLoginSession();
        auth = "Bearer " + sessionModel.getAccess_token();


        qrCode = getIntent().getStringExtra("pass");
        b.back.setTitle(qrCode.substring(qrCode.length() - 10));
        mainQr = qrCode.substring(qrCode.length() - 10);
        Log.e("Auth Find", auth);
        Log.e("QRCODE", qrCode);


        if (qrCode.substring(0, 2).equalsIgnoreCase("00")) {
            b.llQr.setVisibility(View.GONE);
            b.rvQr.setVisibility(View.VISIBLE);
            getList();
        } else if (qrCode.substring(0, 2).equalsIgnoreCase("01")) {
            b.llQr.setVisibility(View.VISIBLE);
            b.rvQr.setVisibility(View.GONE);
            getData();
        }
        b.back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindQrActivity.this, QrActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void getList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("QRRESPONSE", response);
                Gson gson = new Gson();
                SecondaryModel model = gson.fromJson(response, SecondaryModel.class);
                SecondaryAdapter secondaryAdapter = new SecondaryAdapter(model.SecondaryLabelDetail, context);
                b.rvQr.setAdapter(secondaryAdapter);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlList + mainQr, responseListener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("QRCode", mainQr);

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
        MySingleton.myGetMySingleton(FindQrActivity.this).myAddToRequest(stringRequest);
    }


    private void getData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("QRRESPONSE", response);
                Gson gson = new Gson();
                QrModel model = gson.fromJson(response, QrModel.class);

               /* SecondaryModel model = gson.fromJson(response, SecondaryModel.class);
                SecondaryAdapter secondaryAdapter = new SecondaryAdapter(model.SecondaryLabelDetail, context);
                b.rvQr.setAdapter(secondaryAdapter);*/
                Log.e("QRCode", model.QRCode);
                Log.e("ProductCode", model.ProductCode);
                Log.e("BatchNumber", model.BatchNumber);

                b.tvQr.setText(model.QRCode);
                b.tvProductCode.setText(model.ProductCode);
                b.tvBatchNo.setText(model.BatchNumber);
                b.tvSerialNo.setText(model.SerialNumber);
                b.tvMfg.setText(model.ManufactureDate);
                b.tvExp.setText(model.ExpiryDate);

              /*  b.mbNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FindQrActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("productCode", model.ProductCode);
                        startActivity(intent);
                    }
                });*/

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + mainQr, responseListener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("QRCode", mainQr);

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
        MySingleton.myGetMySingleton(FindQrActivity.this).myAddToRequest(stringRequest);
    }
}