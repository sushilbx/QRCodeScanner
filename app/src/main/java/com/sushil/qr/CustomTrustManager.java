package com.sushil.qr;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class CustomTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // Implement your client certificate validation logic here, if needed
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // Implement your server certificate validation logic here

        // Example: Trust certificates signed by a specific trusted CA
        boolean trusted = false;
        for (X509Certificate cert : chain) {
            // Check if the certificate is signed by a trusted CA
            if (isCertificateSignedByTrustedCA(cert)) {
                trusted = true;
                break;
            }
        }

        if (!trusted) {
            throw new CertificateException("Server certificate is not trusted.");
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    private boolean isCertificateSignedByTrustedCA(X509Certificate certificate) {
        // Implement your own logic to check if the certificate is signed by a trusted CA
        // You can compare the certificate's issuer with the trusted CA's certificate or check against a list of trusted CA certificates
        // Return true if the certificate is signed by a trusted CA, false otherwise
        return false;
    }
}

