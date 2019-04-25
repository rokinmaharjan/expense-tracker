package com.example.expensetracker.items.views;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensetracker.R;
import com.example.expensetracker.items.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<Item> items = new ArrayList<>();
    private OnItemClickListener listener;

    public ItemsAdapter() {
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_recycler_view, viewGroup, false);
        return new ItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final Item item = items.get(i);

        itemViewHolder.name.setText(item.getName());
        itemViewHolder.price.setText(String.valueOf(item.getPrice()));

        if ("EXPENSE".equals(item.getItemType())) {
            itemViewHolder.itemTypeIndicator.setBackgroundColor(Color.parseColor("#B22222"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        private ImageView editButton;
        private ImageView deleteButton;
        private View itemTypeIndicator;

        public ItemViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            name = view.findViewById(R.id.item_name);
            price = view.findViewById(R.id.item_price);
            editButton = view.findViewById(R.id.image_edit);
            deleteButton = view.findViewById(R.id.image_delete);
            itemTypeIndicator = view.findViewById(R.id.item_type_indicator);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemEdit(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDelete(position);
                        }
                    }
                }
            });
        }
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public interface OnItemClickListener {
        void onItemEdit(int position);

        void onItemDelete(int positon);
    }
}
