package com.sushil.qr;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class Converter {
    @TypeConverter
    public ProductData.ItemPriceDetails StringToPriceDetails(String string) {
        return new Gson().fromJson(string, ProductData.ItemPriceDetails.class);
    }

    @TypeConverter
    public String priceDetailsToString(ProductData.ItemPriceDetails priceDetails) {
        return new Gson().toJson(priceDetails);
    }
}
