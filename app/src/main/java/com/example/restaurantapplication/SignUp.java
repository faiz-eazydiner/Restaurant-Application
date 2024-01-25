package com.example.restaurantapplication;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText;
    private Button signUpButton;

    // Retrofit
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://localhost:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Initialize UI components
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.emailSignup);
        passwordEditText = findViewById(R.id.passwordSignup);
        signUpButton = findViewById(R.id.signup);

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get entered name, email, and password
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Perform basic validation
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call your Retrofit API method for sign-up
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", name);
                    map.put("email", email);
                    map.put("password", password);

                    Call<Void> call = retrofitInterface.executeSignup(map);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // Sign-up successful, handle the response accordingly
                                Toast.makeText(SignUp.this, "Sign-up successful", Toast.LENGTH_SHORT).show();

                                // You may want to navigate to the main activity or perform other actions after sign-up
                                // For example, uncomment the following line to finish the current activity:
                                 finish();
                            } else {
                                // Handle unsuccessful sign-up, show an error message, etc.
                                Toast.makeText(SignUp.this, "Sign-up failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Handle network failure, show an error message, etc.
                            Toast.makeText(SignUp.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}