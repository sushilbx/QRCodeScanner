package com.sushil.qr;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.sushil.qr.APIs.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends AndroidViewModel {
    AppDatabase appDatabase ;
    public ProductViewModel(@NonNull Application application) {
        super(application);
       appDatabase= AppDatabase.getDatabase(application);
    }

    MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    LiveData<List<Product>> products = _products;

    public void getProductList() {

        Call<ProductData> call = RetrofitClient.getInstance().getApi().productList(new ProductBody("0","test4@test.com","123456444"
                ,"iZXFJIAFaLVWDx3qmpXuXRnCEsjAU6DZNwt6bEzONAHk4RoRWkyjutho7PQgZlFSYFqEZuspMzubzz0Lg8iXIIQ/t+oyheL4HxGouRJlz8o=","1","en",
                "4","ios","IOS_DUMMY_DEVICE_TOKEN","1","1","F2555F59-42E0-4289-B9A4-8F9D2C9F0A81",2013));
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                Log.e("response", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    for (Product product:response.body().data.product_details){
                        appDatabase.productDao().insert(product);
                    }
                   _products.postValue(appDatabase.productDao().getAll());
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
