package com.sushil.qr.APIs;

import com.sushil.qr.ProductBody;
import com.sushil.qr.ProductData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("product_details")
    Call<ProductData> productList(@Body ProductBody productBody);


}
