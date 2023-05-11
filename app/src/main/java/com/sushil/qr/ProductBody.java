package com.sushil.qr;

public class ProductBody {
    public String ending_index;
    public String customer_emailid;
    public String customer_contact;
    public String api_key;
    public String category_id;
    public String language_code;

    public ProductBody(String ending_index, String customer_emailid, String customer_contact, String api_key, String category_id, String language_code, String branch_id, String device_type, String device_token, String price_type, String start_index, String device_id, int customer_id) {
        this.ending_index = ending_index;
        this.customer_emailid = customer_emailid;
        this.customer_contact = customer_contact;
        this.api_key = api_key;
        this.category_id = category_id;
        this.language_code = language_code;
        this.branch_id = branch_id;
        this.device_type = device_type;
        this.device_token = device_token;
        this.price_type = price_type;
        this.start_index = start_index;
        this.device_id = device_id;
        this.customer_id = customer_id;
    }

    public String branch_id;
    public String device_type;
    public String device_token;
    public String price_type;
    public String start_index;
    public String device_id;
    public int customer_id;
}
