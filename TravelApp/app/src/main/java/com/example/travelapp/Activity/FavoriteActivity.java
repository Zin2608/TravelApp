package com.example.travelapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelapp.API.PropertyApi;
import com.example.travelapp.Adapter.FavoriteAdapter;
import com.example.travelapp.Domain.PropertyDomain;
import com.example.travelapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFav;
    private FavoriteAdapter favoriteAdapter;
    private PropertyApi propertyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerViewFav = findViewById(R.id.recyclerViewFav);
        recyclerViewFav.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.154.1:9092") // Thay thế bằng URL cơ sở của API
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        propertyApi = retrofit.create(PropertyApi.class);

        fetchFavoriteProperties();

        ImageView backBtn = findViewById(R.id.backBtn2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại MainActivity khi nhấn nút back
            }
        });
    }

    private void fetchFavoriteProperties() {
        Call<List<PropertyDomain>> call = propertyApi.getFavoriteProperties();
        call.enqueue(new Callback<List<PropertyDomain>>() {
            @Override
            public void onResponse(Call<List<PropertyDomain>> call, Response<List<PropertyDomain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PropertyDomain> favoritePropertiesList = response.body();
                    if (!favoritePropertiesList.isEmpty()) {
                        favoriteAdapter = new FavoriteAdapter(new ArrayList<>(favoritePropertiesList), FavoriteActivity.this, propertyApi);
                        recyclerViewFav.setAdapter(favoriteAdapter);
                    } else {
                        showToast("No favorite properties found.");
                    }
                } else {
                    int statusCode = response.code();
                    switch (statusCode) {
                        case 404:
                            showToast("Resource not found.");
                            break;
                        case 500:
                            showToast("Server error. Please try again later.");
                            break;
                        default:
                            showToast("An error occurred. Please try again.");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PropertyDomain>> call, Throwable t) {
                showToast("Call failed: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(FavoriteActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
