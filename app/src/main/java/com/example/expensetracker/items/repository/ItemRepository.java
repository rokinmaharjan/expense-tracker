package com.example.expensetracker.items.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.expensetracker.database.ExpenseDatabase;
import com.example.expensetracker.items.model.Item;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ItemRepository {
    ItemDao itemDao;

    public ItemRepository(Application application) {
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        this.itemDao = database.itemDao();
    }

    public void add(Item item) {
        new AddItemAsyncTask(itemDao).execute(item);
    }

    public void update(Item item) {
        new UpdateItemAsyncTask(itemDao).execute(item);
    }

    public void delete(Item item) {
        new DeleteItemAsyncTask(itemDao).execute(item);
    }

    public LiveData<List<Item>> getAllLive() {
        return itemDao.getAllLive();
    }

    public List<Item> getAll() throws ExecutionException, InterruptedException {
        return new GetAllItemsAsyncTask(itemDao).execute().get();
    }

    public Item findById(int id) throws ExecutionException, InterruptedException {
        return new FindItemById(itemDao).execute(id).get();
    }

    public List<Item> findByMonthAndYear(int month, int year) throws ExecutionException, InterruptedException {
        Integer[] params = new Integer[]{month, year};
        return new GetAllItemsByMonthAndYearAsyncTask(itemDao).execute(params).get();
    }

    public LiveData<List<Item>> findByMonthAndYearLive(int month, int year) {
        return itemDao.findByMonthAndYearLive(month, year);
    }


    private static class GetAllItemsByMonthAndYearAsyncTask extends AsyncTask<Integer, Void, List<Item>> {
        private ItemDao itemDao;

        public GetAllItemsByMonthAndYearAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected List<Item> doInBackground(Integer... integers) {
            return itemDao.findByMonthAndYear(integers[0], integers[1]);
        }
    }


    private static class AddItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        public AddItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Item... items) {
            itemDao.add(items[0]);
            return null;
        }
    }

    private static class GetAllItemsAsyncTask extends AsyncTask<Void, Void, List<Item>> {
        private ItemDao itemDao;

        public GetAllItemsAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected List<Item> doInBackground(Void... voids) {
            return itemDao.getAll();
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        public UpdateItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        public DeleteItemAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class FindItemById extends AsyncTask<Integer, Void, Item> {
        private ItemDao itemDao;

        public FindItemById(ItemDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Item doInBackground(Integer... integers) {
            return itemDao.findById(integers[0]);
        }
    }

}
