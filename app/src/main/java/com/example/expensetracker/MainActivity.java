package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.expensetracker.items.model.Item;
import com.example.expensetracker.items.views.AddItemDialog;
import com.example.expensetracker.items.views.EditItemDialog;
import com.example.expensetracker.items.views.ItemsAdapter;
import com.example.expensetracker.items.views.MonthYearPickerDialog;
import com.example.expensetracker.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button dateButton;

    private RecyclerView recyclerView;
    private List<Item> items;
    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    private TextView netTextView;

    private FloatingActionButton fab;

    private static final Logger LOGGER = Logger.getLogger("mainActivityLogger");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Expense Tracker");

        try {
            items = AppCommons.getItemRepository()
                    .findByMonthAndYear(DateUtils.getMonthFromDate(new Date()), DateUtils.getYearFromDate(new Date()));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        createDateTextView();

        createNetTextView();

        createItemsRecyclerView();

        createFab();
    }

    private void createNetTextView() {
        netTextView = findViewById(R.id.net);
        float net = calculateNet(items);

        if (net < 0) {
            netTextView.setTextColor(Color.parseColor("#B22222"));
        } else {
            netTextView.setTextColor(Color.parseColor("#008000"));
        }
        netTextView.setText(String.valueOf(net));
    }

    private void createDateTextView() {
        dateButton = findViewById(R.id.date_button);

        String readableDate = DateUtils.getMonthNameFromDate(new Date()) + " " + DateUtils.getYearFromDate(new Date());
        dateButton.setText(readableDate);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void createItemsRecyclerView() {
        itemsAdapter.setItems(items);
        itemsAdapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.recycleListView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemsAdapter);

        itemsAdapter.setListener(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemEdit(int position) {
                openEditItemDialog(position);
            }

            @Override
            public void onItemDelete(int positon) {
                AppCommons.getItemRepository().delete(items.remove(positon));
                itemsAdapter.notifyItemRemoved(positon);
            }
        });
    }

    private void createFab() {
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddItemDialog();
            }
        });
    }

    private void openAddItemDialog() {
        AddItemDialog dialog = new AddItemDialog();
        dialog.setItemsAdapter(itemsAdapter);
        dialog.setItems(items);

        dialog.show(getSupportFragmentManager(), "add item dialog");
    }

    private void openEditItemDialog(int itemPosition) {
        EditItemDialog dialog = new EditItemDialog();
        dialog.setItem(items.get(itemPosition));
        dialog.setItemsAdapter(itemsAdapter);
        dialog.setItemPosition(itemPosition);

        dialog.show(getSupportFragmentManager(), "edit item dialog");
    }

    private void openDatePicker() {
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateButton.setText(DateUtils.getMonthNameFromMonthInteger(month - 1) + " " + year);

                try {
                    items = AppCommons.getItemRepository().findByMonthAndYear(month - 1, year);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                createNetTextView();

                itemsAdapter.setItems(items);
                itemsAdapter.notifyDataSetChanged();
            }
        });
        pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }

    private float calculateNet(List<Item> items) {
        float net = 0;

        for (Item item : items) {
            if ("EXPENSE".equals(item.getItemType())) {
                net -= item.getPrice();
            } else {
                net += item.getPrice();
            }
        }

        return net;
    }
}