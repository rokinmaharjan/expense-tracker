package com.example.expensetracker.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.expensetracker.items.model.Item;
import com.example.expensetracker.items.repository.ItemDao;

@Database(entities = {Item.class}, version = 3)
public abstract class ExpenseDatabase extends RoomDatabase {
    private static ExpenseDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized ExpenseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ExpenseDatabase.class, "expenses_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
