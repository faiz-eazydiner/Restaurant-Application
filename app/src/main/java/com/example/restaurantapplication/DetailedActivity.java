package com.example.restaurantapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.restaurantapplication.databinding.ActivityDetailedBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailedActivity extends AppCompatActivity implements BookingSlotAdapter.OnSlotClickListener{
    ActivityDetailedBinding binding;
    private int selectedSlotPosition = -1;

    // Date Picker
    private DatePicker datePicker;
    private RecyclerView recyclerView;
    private Button reviewButton;
    private BookingSlotAdapter slotAdapter;
    private EditText numberOfPersons, editTextContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if (intent != null){
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
            int description = intent.getIntExtra("description", R.string.description);
            int image = intent.getIntExtra("image", R.drawable.maggi);
            binding.restaurantName.setText(name);
            binding.cityName.setText(time);
            binding.description.setText(description);
            binding.detailImage.setImageResource(image);
        }

        datePicker = findViewById(R.id.datePicker);
        recyclerView = findViewById(R.id.recyclerViewSlots);
        reviewButton = findViewById(R.id.reviewButton);
        numberOfPersons = findViewById(R.id.numberOfPerson);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);

        // Set Date Picker
        datePicker.setMinDate(System.currentTimeMillis()-1000);
        datePicker.init(
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                ((view, year, monthOfYear, dayOfMonth) -> loadTimeSlots(year,monthOfYear,dayOfMonth)));

        // Use GridLayoutManager with 3 columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        List<BookingSlot> slots = generateSampleSlots();    // Sample Slots
        slotAdapter = new BookingSlotAdapter(slots, this);
        recyclerView.setAdapter(slotAdapter);

        // Set a click listener for your review button
        reviewButton.setOnClickListener(v -> {

            String contactNumber = editTextContactNumber.getText().toString().trim();
            String numberOfPerson = numberOfPersons.getText().toString();

            if (selectedSlotPosition == RecyclerView.NO_POSITION) {
                Toast.makeText(DetailedActivity.this, "Please select a time slot!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (numberOfPerson.isEmpty()) {
                Toast.makeText(this, "Please enter number of persons!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contactNumber.isEmpty()) {
                Toast.makeText(DetailedActivity.this, "Please enter a contact number!", Toast.LENGTH_SHORT).show();
                return;
            }

            BookingSlot bookingSlot = slotAdapter.getSlots().get(selectedSlotPosition);

            if (intent != null) {
                Intent confirmationIntent = new Intent(DetailedActivity.this, ReviewActivity.class);
                confirmationIntent.putExtra("restaurantName", intent.getStringExtra("name"));
                confirmationIntent.putExtra("image", intent.getIntExtra("image", R.drawable.maggi));
                confirmationIntent.putExtra("date", getSelectedDate());
                confirmationIntent.putExtra("timeSlot", String.valueOf(bookingSlot));
                confirmationIntent.putExtra("numberOfPerson", numberOfPerson);
                confirmationIntent.putExtra("contactNumber", contactNumber);
                startActivity(confirmationIntent);
            }
        });

        // Initializing Time Slots
        loadTimeSlots(
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth());
    }

    private List<BookingSlot> generateSampleSlots() {
        List<BookingSlot> slots = new ArrayList<>();
        // Get the current hour in 24-hour format
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        // Generate sample slots for the next available hours
        if (currentHour < 17) {
            for (int i = currentHour; i < 17; i++) {
                addSlot(slots, i);
            }
        }

        // Generate sample slots for upcoming days (from 9 AM to 11 PM)
        for (int i = 9; i <= 23; i++) {
            addSlot(slots, i);
        }
        return slots;
    }
    private void addSlot(List<BookingSlot> slots, int hour) {
        // Convert 24-hour format to 12-hour format
        int startHour = hour % 12;
        int endHour = (hour + 1) % 12;

        // Adjust for 12-hour format
        if (startHour == 0) {
            startHour = 12;
        }
        if (endHour == 0) {
            endHour = 12;
        }

        // Determine AM or PM
        String amPmStart = (hour < 12) ? "AM" : "PM";
        String amPmEnd = ((hour + 1) < 12) ? "AM" : "PM";

        // Create time slot string
        String timeSlot = startHour + ":00 " + amPmStart + " - " + endHour + ":00 " + amPmEnd;

        // Add the time slot to the list
        slots.add(new BookingSlot(timeSlot, false));
    }
    @Override
    public void onSlotClick(int position) {
        // Deselect the previously selected slot if any
        if (selectedSlotPosition != -1) {
            BookingSlot previousSelectedSlot = slotAdapter.getSlots().get(selectedSlotPosition);
            previousSelectedSlot.setReserved(false);
            slotAdapter.notifyItemChanged(selectedSlotPosition);
        }

        // Handle slot click (reserve/unreserve)
        BookingSlot clickedSlot = slotAdapter.getSlots().get(position);
        clickedSlot.setReserved(true);
        // Notify the adapter that the data has changed
        slotAdapter.notifyItemChanged(position);

        // Update the selected slot position
        selectedSlotPosition = position;
    }
    private void loadTimeSlots(int year, int month, int day) {
        // Implement your logic to load available and unavailable time slots
        // For demo purposes, let's generate some sample time slots
        List<BookingSlot> timeSlots = generateSampleSlots();
        slotAdapter.setSlots(timeSlots);
        slotAdapter.setSelectedPosition(RecyclerView.NO_POSITION);
//        reviewButton.setEnabled(false);       // Write Logic
    }
    private String getSelectedDate() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1; // Adding 1 as months are zero-based
        int dayOfMonth = datePicker.getDayOfMonth();

        return year + "-" + month + "-" + dayOfMonth;
    }

    public void decrement(View view) {
        int currentValue = getValueFromEditText();
        // Ensure the new value is at least 1
        int newValue = Math.max(1, currentValue - 1);
        numberOfPersons.setText(String.valueOf(newValue));
    }
    public void increment(View view) {
        int currentValue = getValueFromEditText();
        // Ensure the new value is at most 10
        int newValue = Math.min(10, currentValue + 1);
        numberOfPersons.setText(String.valueOf(newValue));
    }
    private int getValueFromEditText() {
        String valueStr = numberOfPersons.getText().toString();
        if (valueStr.isEmpty()) {
            return 1; // Default value if the EditText is empty
        } else {
            int value = Integer.parseInt(valueStr);
            // Ensure the value is at least 1
            return Math.max(1, value);
        }
    }

}