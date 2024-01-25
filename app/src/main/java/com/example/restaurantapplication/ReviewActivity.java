package com.example.restaurantapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewActivity extends AppCompatActivity {

    private TextView restaurantNameTextView;
    private TextView dateTextView;
    private TextView timeSlotTextView;
    private Button editButton;
    private Button confirmBookingButton;
    private ImageView imageView;
    private TextView numberOfPerson;
    private TextView contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Initialize views
        restaurantNameTextView = findViewById(R.id.restaurantName);
        dateTextView = findViewById(R.id.date);
        timeSlotTextView = findViewById(R.id.timeSlot);
        editButton = findViewById(R.id.editButton);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);
        imageView = findViewById(R.id.restaurantImage);
        numberOfPerson = findViewById(R.id.numberOfPerson);
        contactNumber = findViewById(R.id.contactNumber);

        // Get data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String restaurantName = intent.getStringExtra("restaurantName");
            String date = intent.getStringExtra("date");
            String timeSlot = intent.getStringExtra("timeSlot");
            int image = intent.getIntExtra("image", R.drawable.maggi);
            String noOfPerson = intent.getStringExtra("numberOfPerson");
            String contactNo = intent.getStringExtra("contactNumber");

            // Display data in TextViews
            restaurantNameTextView.setText(restaurantName);
            dateTextView.setText(date);
            timeSlotTextView.setText(timeSlot);
            imageView.setImageResource(image);
            numberOfPerson.setText(noOfPerson);
            contactNumber.setText(contactNo);
        }

        // For notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id",
                    "Your Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        editButton.setOnClickListener(view -> finish());
        confirmBookingButton.setOnClickListener(v -> {

            Intent intent1 = new Intent(ReviewActivity.this,ConfirmationDetail.class);
            intent1.putExtra("restaurantName",restaurantNameTextView.getText().toString());
            intent1.putExtra("date",dateTextView.getText().toString());
            intent1.putExtra("timeSlot",timeSlotTextView.getText().toString());
            intent1.putExtra("numberOfPerson",numberOfPerson.getText().toString());
            intent1.putExtra("contactNumber",contactNumber.getText().toString());

            startActivity(intent1);
            showToast("Booking confirmed!");
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}