package com.example.restaurantapplication;
import com.google.gson.annotations.SerializedName;
public class LoginResponse {
    // Add fields corresponding to the expected response
    @SerializedName("token")
    private String token;

    // Add getters and setters if needed

    public String getToken() {
        return token;
    }
}
