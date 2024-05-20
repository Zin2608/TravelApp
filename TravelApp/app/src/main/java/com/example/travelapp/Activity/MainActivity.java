package com.example.travelapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.travelapp.Adapter.ItemsAdapter;
import com.example.travelapp.API.PropertyApi;
import com.example.travelapp.Domain.PropertyDomain;
import com.example.travelapp.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private ArrayList<PropertyDomain> allItems;
    private RecyclerView.Adapter adapterRecom, adapterNear;
    private PropertyApi propertyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.154.1:9092") // Thay thế bằng URL cơ sở của API
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        propertyApi = retrofit.create(PropertyApi.class);

        initLocation();
        initTypeButtons();
        initBottomNavigationButtons();
        fetchProperties();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void fetchProperties() {
        Call<List<PropertyDomain>> call = propertyApi.getProperties();
        call.enqueue(new Callback<List<PropertyDomain>>() {
            @Override
            public void onResponse(Call<List<PropertyDomain>> call, Response<List<PropertyDomain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xử lý khi nhận được phản hồi thành công
                    List<PropertyDomain> propertiesList = response.body();
                    if (!propertiesList.isEmpty()) {
                        allItems = new ArrayList<>(propertiesList);
                        initList();
                    } else {
                        // Hiển thị thông báo khi danh sách trả về rỗng
                        showToast("No properties found.");
                    }
                } else {
                    // Xử lý khi phản hồi không thành công
                    int statusCode = response.code();
                    switch (statusCode) {
                        case 404:
                            // Hiển thị thông báo khi không tìm thấy tài nguyên
                            showToast("Resource not found.");
                            break;
                        case 500:
                            // Hiển thị thông báo khi có lỗi máy chủ
                            showToast("Server error. Please try again later.");
                            break;
                        default:
                            // Hiển thị thông báo lỗi mặc định cho các trường hợp khác
                            showToast("An error occurred. Please try again.");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PropertyDomain>> call, Throwable t) {
                // Log ra thông tin về lỗi
                Log.e("API_CALL", "Call failed: " + t.getMessage());
                t.printStackTrace();
                // Xử lý lỗi theo nhu cầu của bạn, ví dụ:
                Toast.makeText(MainActivity.this, "Call failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    // Phương thức hiển thị thông báo Toast
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    private void initList() {
        // Filter items with score greater than 4.5 for recommended list
        ArrayList<PropertyDomain> recommendedItems = (ArrayList<PropertyDomain>) allItems.stream()
                .filter(item -> item.getScore() > 4.5)
                .collect(Collectors.toList());

        binding.recommendedView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterRecom = new ItemsAdapter(recommendedItems, this);
        binding.recommendedView.setAdapter(adapterRecom);

        // Initially show all items in nearView
        updateNearView(allItems);
    }

    private void initTypeButtons() {
        binding.cat1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByType("House");
            }
        });

        binding.cat2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByType("Apartment");
            }
        });

        binding.cat3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByType("Villa");
            }
        });

        binding.cat4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByType("Bangola");
            }
        });

        binding.cat5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByType("Empty land");
            }
        });
    }

    private void filterByType(String type) {
        if (allItems != null) {
            ArrayList<PropertyDomain> filteredItems;
            if (type.equals("All")) {
                filteredItems = new ArrayList<>(allItems);
            } else {
                filteredItems = (ArrayList<PropertyDomain>) allItems.stream()
                        .filter(item -> item.getType().equalsIgnoreCase(type))
                        .collect(Collectors.toList());
            }
            updateNearView(filteredItems);
        } else {
            // Handle the case when allItems is null
        }
    }

    private void initLocation() {
        String[] items = new String[]{"Los Angeles, USA", "New York, USA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.locationspin.setAdapter(adapter);

        binding.locationspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = parent.getItemAtPosition(position).toString();
                filterNearView(selectedLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optionally handle this case
            }
        });
    }

    private void filterNearView(String location) {
        if (allItems != null) {
            ArrayList<PropertyDomain> filteredItems = (ArrayList<PropertyDomain>) allItems.stream()
                    .filter(item -> item.getAddress().contains(location.split(",")[0]))
                    .collect(Collectors.toList());
            updateNearView(filteredItems);
        } else {
            // Xử lý trường hợp khi allItems là null
        }
    }


    private void updateNearView(ArrayList<PropertyDomain> items) {
        binding.nearView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterNear = new ItemsAdapter(items, this);
        binding.nearView.setAdapter(adapterNear);
    }

    private void initBottomNavigationButtons() {
        binding.bottom1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Home
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.bottom2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Explorer
                Intent intent = new Intent(MainActivity.this, ExplorerActivity.class);
                startActivity(intent);
            }
        });

        binding.bottom3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Favorite
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        binding.bottom4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Chat
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        binding.bottom5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Profile
                String userEmail = getIntent().getStringExtra("email");
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("email", userEmail);
                startActivity(intent);
            }
        });
    }
}
