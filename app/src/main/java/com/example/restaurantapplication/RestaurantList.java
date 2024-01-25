package com.example.restaurantapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapplication.databinding.ActivityRestaurantListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RestaurantList extends AppCompatActivity {
    private SearchView searchView;
    ActivityRestaurantListBinding binding;
    ListAdapter listAdapter;
    ArrayList<ListData> dataArrayList = new ArrayList<>();
    ListData listData;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRestaurantListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = binding.recyclerView;  // Correct reference to RecyclerView

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        int[] imageList = {R.drawable.maggi, R.drawable.cake, R.drawable.burger, R.drawable.fries, R.drawable.pancake, R.drawable.pizza, R.drawable.noodles};
        int[] descriptionList = {R.string.pastaIngredients, R.string.description, R.string.cakeIngredients, R.string.pancakeIngredients, R.string.pizzaIngredients, R.string.burgerIngredients, R.string.friesIngredients};
        int[] descList = {R.string.pastaDesc, R.string.maggieDesc, R.string.cakeDesc, R.string.pancakeDesc, R.string.pizzaDesc, R.string.burgerDesc, R.string.friesDesc};
        String[] nameList = {"Bubby's Restaurant", "Maggi' Restaurant", "Cake Cafe", "Pancake's Cafe", "Pizza Hut", "Mc Donald's", "Fries club"};
        String[] cityList = {"New Delhi", "Mumbai", "Bangalore", "New Delhi", "Kolkata", "Mumbai", "New Delhi"};

        for (int i = 0; i < imageList.length; i++) {
            listData = new ListData(nameList[i], cityList[i], descriptionList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }

        listAdapter = new ListAdapter(dataArrayList);
        recyclerView.setAdapter(listAdapter);

        // Remove the old click listener, as RecyclerView uses a different way to handle item clicks
        listAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(RestaurantList.this, DetailedActivity.class);
            intent.putExtra("name", nameList[position]);
            intent.putExtra("time", cityList[position]);
            intent.putExtra("ingredients", descriptionList[position]);
            intent.putExtra("desc", descList[position]);
            intent.putExtra("image", imageList[position]);
            startActivity(intent);
        });

        FloatingActionButton fabConfirmBooking = findViewById(R.id.fabConfirmBooking);
        if (BookingStatusManager.getInstance().isBookingConfirmed()) {
            fabConfirmBooking.setOnClickListener(view -> {
                // Start the ConfirmationDetailActivity
                Intent confirmationIntent = new Intent(RestaurantList.this, ConfirmationDetail.class);
                // Add any necessary data to the intent
                startActivity(confirmationIntent);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        // Use androidx.appcompat.widget.SearchView here
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");

        // Implement SearchView listener if needed
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                listAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text changes
                listAdapter.filter(newText);
                return true;
            }
        });
        return true;
    }
}