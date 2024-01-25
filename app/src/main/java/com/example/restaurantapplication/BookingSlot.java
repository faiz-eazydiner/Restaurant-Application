package com.example.restaurantapplication;

public class BookingSlot {
    private String time;
    public BookingSlot(String time, boolean isReserved) {
        this.time = time;
        this.isReserved = isReserved;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    private boolean isReserved;

    @Override
    public String toString() {
        return time;
    }

}
