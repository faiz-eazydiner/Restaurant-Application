package com.example.restaurantapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<ListData> dataArrayList;
    private ArrayList<ListData> originalData;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ListAdapter(ArrayList<ListData> dataArrayList) {
        this.dataArrayList = dataArrayList;
        this.originalData = new ArrayList<>(dataArrayList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Create ViewHolder class
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListData listData = dataArrayList.get(position);
        // Set data to views
        holder.listImage.setImageResource(listData.image);
        holder.listName.setText(listData.name);
        holder.listTime.setText(listData.location);

        // Set the click listener for the item
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView listImage;
        public TextView listName;
        public TextView listTime;

        public ViewHolder(View itemView) {
            super(itemView);
            listImage = itemView.findViewById(R.id.listImage);
            listName = itemView.findViewById(R.id.listName);
            listTime = itemView.findViewById(R.id.listTime);
        }
    }

    // Search Filter
    public void filter(String query) {
        if (query.isEmpty()) {
            // If the query is empty, restore the original data
            dataArrayList.clear();
            dataArrayList.addAll(originalData);

        } else {
            // If there is a query, filter the data
            ArrayList<ListData> filteredList = new ArrayList<>();
            for (ListData data : originalData) {
                if (data.location.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(data);
                }
            }
            dataArrayList.clear();
            dataArrayList.addAll(filteredList);
        }
        notifyDataSetChanged();
    }
}