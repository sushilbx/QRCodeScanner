package com.sushil.qr;

import java.util.ArrayList;
import java.util.List;

public class ProductData {
    public String status;
    public String show_status;
    public String message;
    public String server_time;
    public Data data;

    public class Data {
        public int pagnation;
        public int current_start_index;
        public int current_end_index;
        public int current_total_record;
        public List<Product> product_details;
    }

    public class IsNormalSupend {
    }

    public class IsPromoSuspend {
        public String suspend_text;
        public String suspend_image;
        public int promo_product_id;
    }

    public class ItemPriceDetails {
        public double mst_cat_discount;
        public double sub_price;
        public double sub_promotional_price;
        public double sub_price_discount_per;
        public double sub_price_discount_fix;
        public String final_price;
        public String price_current_taken_type;
        public String price_id;
    }

    public class ModifierDetail {
        public String modifier_id;
        public String modifier_name;
        public String modifier_abb;
        public String modifier_need_printer;
        public String modifier_type;
        public String sub_product_modifers_id;
        public ArrayList<ModifierOption> modifier_options;
    }

    public class ModifierOption {
        public int sub_option_id;
        public int modifier_options_id;
        public String modifier_options_name;
        public String modifier_options_abb;
        public int modifier_options_default;
        public double modifier_options_price;
        public String modifier_options_printer;
        public String modifier_options_type;
    }




    public class SpecialIconDetail {
        public String speical_icon_id;
        public String speical_icon_name;
        public String speical_icon_image;
    }

    public class SuspendDetails {
        public String is_suspend_type;
        public IsNormalSupend is_normal_supend;
        public IsPromoSuspend is_promo_suspend;
    }


}
