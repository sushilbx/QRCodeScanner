package com.sushil.qr.APIs;

import com.sushil.qr.ProductBody;
import com.sushil.qr.ProductData;
import com.sushil.qr.models.LoginBody;
import com.sushil.qr.models.SessionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @POST("product_details")
    Call<ProductData> productList(@Body ProductBody productBody);

    /*    @Headers("Content-Type:application/x-www-form-urlencoded")
        @FormUrlEncoded*/
    @Headers("Content-Type: application/x-www-form-urlencoded")
 /*   @FormUrlEncoded*/
    @POST("token")
    Call<SessionModel> login(
           /* @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grant_type*/
            @Body LoginBody loginBody);


   /* @GET("professional/city-list/{state_id}")
    Call<CityModel> city(
            @Path("state_id") String state_id
    );*/

}
