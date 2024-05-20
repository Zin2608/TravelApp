package com.example.travelapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelapp.API.PropertyApi;
import com.example.travelapp.Adapter.ItemsAdapter;
import com.example.travelapp.Domain.PropertyDomain;
import com.example.travelapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerViewChat;
    private ItemsAdapter itemsAdapter;
    private PropertyApi propertyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));

        // Nhận thông tin property từ Intent
        List<PropertyDomain> selectedProperties = (List<PropertyDomain>) getIntent().getSerializableExtra("selectedProperties");
        // Hiển thị danh sách properties
        displayProperties(selectedProperties);

        ImageView backBtn = findViewById(R.id.backBtn4);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại MainActivity khi nhấn nút back
                onBackPressed();
            }
        });
    }

    private void displayProperties(List<PropertyDomain> properties) {
        if (properties != null && !properties.isEmpty()) {
            ArrayList<PropertyDomain> propertiesArrayList = new ArrayList<>(properties); // Chuyển đổi List thành ArrayList
            itemsAdapter = new ItemsAdapter(propertiesArrayList, ChatActivity.this);
            recyclerViewChat.setAdapter(itemsAdapter);
        } else {
            // Hiển thị một thông báo hoặc thực hiện hành động phù hợp nếu danh sách properties là trống
            showToast("No properties to display.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(ChatActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}