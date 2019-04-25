package com.example.expensetracker.items.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.expensetracker.items.model.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void add(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items_table")
    LiveData<List<Item>> getAllLive();

    @Query("SELECT * FROM items_table")
    List<Item> getAll();

    @Query("SELECT * FROM items_table WHERE id=:id LIMIT 1")
    Item findById(int id);

    @Query("SELECT * FROM items_table WHERE month=:month AND year=:year ")
    List<Item> findByMonthAndYear(int month, int year);

    @Query("SELECT * FROM items_table WHERE month=:month AND year=:year ")
    LiveData<List<Item>> findByMonthAndYearLive(int month, int year);
}
