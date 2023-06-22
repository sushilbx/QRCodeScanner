package com.sushil.qr.models;

public class LoginBody {
    public String username;
    public String password;
    public String grant_type;

    public LoginBody(String username, String password, String grant_type) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }
}
