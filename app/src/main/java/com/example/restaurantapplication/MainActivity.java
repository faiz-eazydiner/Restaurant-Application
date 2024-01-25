package com.example.restaurantapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button signInButton, signUpButton;

    // Retrofit
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://localhost:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Initialize UI components
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signInButton = findViewById(R.id.btnSignIn);
        signUpButton = findViewById(R.id.btnSignUp);

        // Set click listener for the sign-in button
        signInButton.setOnClickListener(v -> {

            // Testing..
            Intent intent = new Intent(MainActivity.this, RestaurantList.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Sign-in successful", Toast.LENGTH_SHORT).show();

            // Get entered email and password
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Perform basic validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                // Call your Retrofit API method for sign-in
                HashMap<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);

                Call<LoginResponse> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            // Sign-in successful, handle the response accordingly
                            Intent intent = new Intent(MainActivity.this, RestaurantList.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Sign-in successful", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle unsuccessful sign-in, show an error message, etc.
                            Toast.makeText(MainActivity.this, "Sign-in failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Handle network failure, show an error message, etc.
                        Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set click listener for the sign-up button (you can implement sign-up logic similarly)
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement sign-up logic here
                // For example, you can navigate to a sign-up activity or fragment
                Intent signUpIntent = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUpIntent);
                Toast.makeText(MainActivity.this, "Redirecting to Sign Up", Toast.LENGTH_SHORT).show();
            }
        });
    }
}