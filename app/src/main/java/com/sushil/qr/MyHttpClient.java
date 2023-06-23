package com.sushil.qr;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MyHttpClient {

    public static OkHttpClient createCustomOkHttpClient() {
        try {
            // Create a custom TrustManager that trusts all certificates
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};

            // Create a custom SSLContext and initialize it with the custom TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);

            // Create an OkHttpClient.Builder and configure it with the custom SSLContext
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);

            // Create and return the OkHttpClient instance
            return builder.build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void makeRequest() {
        OkHttpClient client = createCustomOkHttpClient();

        Request request = new Request.Builder()
                .url("https://example.com")  // Replace with your server URL
                .build();

        try {
            Response response = client.newCall(request).execute();
            // Handle the response as needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

