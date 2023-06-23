package com.sushil.qr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sushil.qr.APIs.RetrofitClient;
import com.sushil.qr.databinding.ActivityLogininBinding;
import com.sushil.qr.models.LoginBody;
import com.sushil.qr.models.SessionModel;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class LogininActivity extends AppCompatActivity {
    ActivityLogininBinding b;
    Context context;
    SessionManager sessionManager;
    SessionModel sessionModel;
    String username = "";
    String password = "";
    String url = "https://kkisan.karnataka.gov.in/kkisanqrapi/api/token";
   // String auth = "L21bge7oevQHSoLlDEZ7hs40xkijA2igj3FULhSPSEDSMOvnHoalgX89ps_0tsrz2SUbFzRrLbg9atEhOsgaqIuYauIhiDpWzS_4AC1pPbQnP7dQfXBbjP3tPCjFsTL25pZj2BGm6DFdja8VDG8NyrlWObVxD6y7ob7cl5eZMfk6SwYvYXVjePrmFd5XmYXqEUXxiMZJtVv2tY9qxdPCYe1CUU-GvZWDIqDLPpMLyPi11d4eXH4c-4YrOKJ2BPKEDp-bq98myo3uXRfImXH3eBFdBUueqY-R_kXZjUudsFrWeFZUprX6vIGidYwdp9Osh0tJS-euVIGupLK-fC_nirHFsdb6ELS31z9H7R3jQRYsmDIMBB7wxL0N-7FZOUBK1j12L-Gv0Wl8ZL4_L3ATChMw82Wy3DFqz1pGhGwt3BYOTeFgsD3aiO48Lcp7DX9fporLIwrjH8QH4kGT_i0c0-kVPiwQeYbPN1AbPSc0Dp2ZC2CzglOLPA7MC5tMHriCQ8MT7pL7TN4LE1IVLT1w1Sid2uFnXxXlvyxwFa6lE8U";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLogininBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        context = LogininActivity.this;
        sessionManager = new SessionManager(LogininActivity.this);
        sessionModel = sessionManager.getLoginSession();
        b.tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ForgotPasswordActivity.class));
            }
        });
        disableSSLCertificateChecking();
        b.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    getData();
                }
            }
        });
      /*  b.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(LogininActivity.this, QrActivity.class);
               intent.putExtra("auth", ""+auth);
               startActivity(intent);
            }
        });*/

    }

    public static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    /*private void forgot() {
        Call<SessionModel> call = RetrofitClient.getInstance().getApi().login(new LoginBody(username, password, "password"));
        call.enqueue(new Callback<SessionModel>() {
            @Override
            public void onResponse(Call<SessionModel> call, Response<SessionModel> response) {
                Log.e("sushil", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    int statusCode = response.code();
                    if (statusCode == 200) {
                        //   Toast.makeText(LogininActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogininActivity.this, QrActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LogininActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(LogininActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SessionModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LogininActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    private void getData() {
        b.rlLoading.setVisibility(View.VISIBLE);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TokenResponse", response);
                b.rlLoading.setVisibility(View.GONE);
               /* Intent intent = new Intent(LogininActivity.this, QrActivity.class);
                intent.putExtra("auth", ""+response);
                startActivity(intent);*/
                Gson gson = new Gson();
                SessionModel model = gson.fromJson(response, SessionModel.class);
                sessionManager.createLoginSession(model);
                Log.e("access_token", model.getAccess_token());
                Log.e("userName", model.getUserName());
                Intent intent = new Intent(LogininActivity.this, QrActivity.class);
                intent.putExtra("auth", model.getAccess_token());
                startActivity(intent);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                b.rlLoading.setVisibility(View.GONE);
                error.printStackTrace();
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("grant_type", "password");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(LogininActivity.this).myAddToRequest(stringRequest);
    }


    private boolean checkForm() {
        username = b.etEmail.getText().toString().trim();
        password = b.etPassword.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            b.etEmail.setFocusableInTouchMode(true);
            b.etEmail.requestFocus();
            return false;
        } else if (!Utils.myEmailValid(username)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            b.etEmail.setFocusableInTouchMode(true);
            b.etEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            b.etPassword.setError("Password is empty");
            b.etPassword.requestFocus();
            return false;
        } else if (password.length() < 6) {
            b.etPassword.setError("Password should be minimum 6 characters");
            b.etPassword.requestFocus();
            return false;
        }

        return true;
    }

}
