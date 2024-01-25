package com.example.restaurantapplication;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface RetrofitInterface {
    @POST("customers")  // Replace with the actual login endpoint
    Call<LoginResponse> executeLogin(@Body HashMap<String, String> map);

    // Add other API methods here
    // For example, sign-up method:
     @POST("customers")
     Call<Void> executeSignup(@Body HashMap<String, String> map);
}
