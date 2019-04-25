package com.example.expensetracker.items.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.expensetracker.AppCommons;
import com.example.expensetracker.R;
import com.example.expensetracker.items.model.Item;
import com.example.expensetracker.items.model.ItemType;
import com.example.expensetracker.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class AddItemDialog extends AppCompatDialogFragment {
    private EditText itemName;
    private EditText itemPrice;

    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<String> monthAdapter;

    private Spinner itemTypeSpinner;
    private ArrayAdapter<ItemType> itemTypeAdapter;

    private ItemsAdapter itemsAdapter;
    private List<Item> items;

    public AddItemDialog() {
    }

    public void setItemsAdapter(ItemsAdapter itemsAdapter) {
        this.itemsAdapter = itemsAdapter;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_item, null);

        itemName = view.findViewById(R.id.item_name_add);
        itemPrice = view.findViewById(R.id.item_price_add);
        yearSpinner = view.findViewById(R.id.year_spinner_add);
        monthSpinner = view.findViewById(R.id.month_spinner_add);
        itemTypeSpinner = view.findViewById(R.id.item_type_spinner_add);

        initializeYearsDropdown();
        initializeMonthsDropdown();
        initializeItemTypeDropdown();

        builder.setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = itemName.getText().toString();
                        float price = Float.valueOf(itemPrice.getText().toString());
                        int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                        int month = DateUtils.getMonthNumberFromMonthName(monthSpinner.getSelectedItem().toString());
                        String itemType = itemTypeSpinner.getSelectedItem().toString();

                        Item item = new Item(name, price, year, month, itemType);

                        AppCommons.getItemRepository().add(item);

                        items.add(item);
                        itemsAdapter.notifyItemInserted(items.size());
                    }
                });


        return builder.create();
    }


    private void initializeYearsDropdown() {
        List<Integer> years = Arrays.asList(2018, 2019, 2020, 2021, 2022);
        yearAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, years);
        yearSpinner.setAdapter(yearAdapter);

        int defaultYear = years.indexOf(DateUtils.getYearFromDate(new Date()));
        yearSpinner.setSelection(defaultYear);
    }

    private void initializeMonthsDropdown() {
        List<String> months = Arrays.asList("January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, months);
        monthSpinner.setAdapter(monthAdapter);

        int defaultMonth = months.indexOf(DateUtils.getMonthNameFromDate(new Date()));
        monthSpinner.setSelection(defaultMonth);
    }

    private void initializeItemTypeDropdown() {
        List<ItemType> itemTypes = Arrays.asList(ItemType.EXPENSE, ItemType.INCOME);
        itemTypeAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, itemTypes);

        itemTypeSpinner.setAdapter(itemTypeAdapter);
    }

}
