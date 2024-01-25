package com.example.restaurantapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingSlotAdapter extends RecyclerView.Adapter<BookingSlotAdapter.ViewHolder> {
    private List<BookingSlot> slots;

    private OnSlotClickListener onSlotClickListener;


    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnSlotClickListener {
        void onSlotClick(int position);
    }
    public void setSlots(List<BookingSlot> slots) {
        this.slots = slots;
    }
    public List<BookingSlot> getSlots() {
        return slots;
    }


    public OnSlotClickListener getOnSlotClickListener() {
        return onSlotClickListener;
    }

    public void setOnSlotClickListener(OnSlotClickListener onSlotClickListener) {
        this.onSlotClickListener = onSlotClickListener;
    }

    public BookingSlotAdapter(List<BookingSlot> slots, OnSlotClickListener onSlotClickListener) {
        this.slots = slots;
        this.onSlotClickListener = onSlotClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking_slot, parent, false);
        return new ViewHolder(view, onSlotClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingSlot slot = slots.get(position);
        holder.textViewSlotTime.setText(slot.getTime());

        // Set background color based on availability

        int backgroundColor = ContextCompat.getColor(holder.itemView.getContext(), slot.isReserved() ? R.color.colorReserved : R.color.colorAvailable);
        holder.textViewSlotTime.setBackgroundColor(backgroundColor);

//        holder.textViewSlotTime.setBackgroundResource(R.drawable.slot_item_background);

        if (position == selectedPosition) {
            // Set a different background color or drawable for the selected slot
            holder.itemView.setBackgroundResource(R.drawable.slot_item_background);
        } else {
            // Set the default background for other slots
            holder.itemView.setBackgroundResource(R.drawable.selected_slot_bg); // or R.drawable.default_background
        }

        holder.itemView.setOnClickListener(v -> {
            if (onSlotClickListener != null) {
                onSlotClickListener.onSlotClick(position);
                setSelectedPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSlotTime;
        BookingSlotAdapter.OnSlotClickListener onSlotClickListener;

        ViewHolder(View itemView, BookingSlotAdapter.OnSlotClickListener onSlotClickListener) {
            super(itemView);
            this.onSlotClickListener = onSlotClickListener;
            textViewSlotTime = itemView.findViewById(R.id.textViewSlotTime);

            // Set click listener to handle slot selection
            itemView.setOnClickListener(v -> {
                if (this.onSlotClickListener != null) {
                    this.onSlotClickListener.onSlotClick(getAdapterPosition());
                }
            });
        }
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged(); // Notify the adapter to rebind all items
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

}
