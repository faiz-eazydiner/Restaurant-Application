package com.example.restaurantapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfirmationDetail extends AppCompatActivity {

    private TextView restaurantNameTextView;
    private TextView dateTextView;
    private TextView timeSlotTextView;
    private TextView numberOfPerson;
    private TextView contactNumber;
    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_detail);

        // Notification popup
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("notification_key")) {
            // Show your popup dialog or perform any action
            showPopupDialog();
        }
        showNotification("Booking Confirmed", "Your booking has been confirmed. Click to view details.");


        // Initialize views
        restaurantNameTextView = findViewById(R.id.restaurantName);
        dateTextView = findViewById(R.id.date);
        timeSlotTextView = findViewById(R.id.timeSlot);
        numberOfPerson = findViewById(R.id.numberOfPerson);
        contactNumber = findViewById(R.id.contactNumber);
        goBackButton = findViewById(R.id.goBack);

        Intent intent = getIntent();
        if (intent != null) {
            String restaurantName = intent.getStringExtra("restaurantName");
            String date = intent.getStringExtra("date");
            String timeSlot = intent.getStringExtra("timeSlot");
            String noOfPerson = intent.getStringExtra("numberOfPerson");
            String contactNo = intent.getStringExtra("contactNumber");

//             Display data in TextViews
            restaurantNameTextView.setText(restaurantName);
            dateTextView.setText(date);
            timeSlotTextView.setText(timeSlot);
            numberOfPerson.setText(noOfPerson);
            contactNumber.setText(contactNo);
        }

        goBackButton.setOnClickListener(v -> {
            BookingStatusManager.getInstance().setBookingConfirmed(true);
            Intent intent1 = new Intent(ConfirmationDetail.this,RestaurantList.class);
            startActivity(intent1);
            finish();
        });

    }

    // Method for Notification popup
    private void showPopupDialog() {
        // Create and show your popup dialog here
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification Tapped");
        builder.setMessage("The notification was tapped!");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Method to show a notification
    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent())
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    // Method to create a pending intent for the notification
    private PendingIntent getPendingIntent() {
        Intent notificationIntent = new Intent(this, ConfirmationDetail.class);
        Intent intent = getIntent();
        if (intent != null){
            String restaurantName = intent.getStringExtra("restaurantName");
            String date = intent.getStringExtra("date");
            String timeSlot = intent.getStringExtra("timeSlot");
            String noOfPerson = intent.getStringExtra("numberOfPerson");
            String contactNo = intent.getStringExtra("contactNumber");

            notificationIntent.putExtra("restaurantName",restaurantName);
            notificationIntent.putExtra("date",date);
            notificationIntent.putExtra("timeSlot",timeSlot);
            notificationIntent.putExtra("numberOfPerson",noOfPerson);
            notificationIntent.putExtra("contactNumber",contactNo);
        }

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}