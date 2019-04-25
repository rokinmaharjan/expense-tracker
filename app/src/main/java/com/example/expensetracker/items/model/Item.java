package com.example.expensetracker.items.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "items_table")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float price;
    private int year;
    private int month;
    private String itemType;

    public Item() {
    }

    public Item(String name, float price, int year, int month, String itemType) {
        this.name = name;
        this.price = price;
        this.year = year;
        this.month = month;
        this.itemType = itemType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", month=" + month +
                ", itemType=" + itemType +
                '}';
    }
}
