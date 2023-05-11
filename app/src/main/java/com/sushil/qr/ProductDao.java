package com.sushil.qr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE mst_product_id IN (:userIds)")
    List<Product> loadAllByIds(int[] userIds);


    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product product);

    @Query("DELETE FROM product")
    void deleteAll();
}
