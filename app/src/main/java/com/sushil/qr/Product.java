package com.sushil.qr;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Product {
    @PrimaryKey
    public int mst_product_id;
    public int mst_cat_id;
    public int mst_cat_for_member;
    public String mst_product_code;
    public int fix_ptype_id;
    public String fix_ptype_name;
    public String mst_product_type;
    public String mst_product_type_image;
    public String mst_product_name;
    public String mst_product_abb;
    public double mst_product_order;
    public String mst_product_image;
    public String mst_product_image_web;
    public int mst_product_limit_pax;
    public int mst_product_is_favourite;
    public int price_type;
    public int product_qty;
    public ProductData.ItemPriceDetails item_price_details;
//    public ArrayList<Object> dish_price_details;
//    public ArrayList<ProductData.ModifierDetail> modifier_details;
//    public ArrayList<Object> set_item_details;
//    public ProductData.SuspendDetails suspend_details;
//    public ArrayList<ProductData.SpecialIconDetail> special_icon_details;


}