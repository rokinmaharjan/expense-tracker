package com.example.expensetracker;

import android.app.Application;

import com.example.expensetracker.items.repository.ItemRepository;

public class AppCommons extends Application {
    private static ItemRepository itemRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        itemRepository = new ItemRepository(this);
    }

    public static ItemRepository getItemRepository() {
        return itemRepository;
    }
}
