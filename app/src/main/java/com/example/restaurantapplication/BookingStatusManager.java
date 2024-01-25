package com.example.restaurantapplication;

public class BookingStatusManager {
    private static BookingStatusManager instance;
    private boolean isBookingConfirmed = false;

    private BookingStatusManager() {
        // Private constructor to enforce singleton pattern
    }

    public static BookingStatusManager getInstance() {
        if (instance == null) {
            instance = new BookingStatusManager();
        }
        return instance;
    }

    public boolean isBookingConfirmed() {
        return isBookingConfirmed;
    }

    public void setBookingConfirmed(boolean bookingConfirmed) {
        isBookingConfirmed = bookingConfirmed;
    }
}
