package com.example.travelapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.API.UserApi;
import com.example.travelapp.Domain.UserDomain;
import com.example.travelapp.R;
import com.example.travelapp.databinding.ActivityProfileBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy email từ Intent
        String userEmail = getIntent().getStringExtra("email");

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.154.1:9092") // Thay thế bằng URL cơ sở của API
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit.create(UserApi.class);

        fetchUserData(userEmail);

        ImageView backBtn = findViewById(R.id.backBtn3);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại MainActivity khi nhấn nút back
                onBackPressed();
            }
        });
    }

    private void fetchUserData(String userEmail) {
        // Gửi yêu cầu API để lấy thông tin người dùng theo email
        Call<UserDomain> call = userApi.getUserByEmail(userEmail);
        call.enqueue(new Callback<UserDomain>() {
            @Override
            public void onResponse(Call<UserDomain> call, Response<UserDomain> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDomain user = response.body();
                    displayUserData(user);
                } else {
                    showToast("Failed to fetch user data or user not found.");
                }
            }

            @Override
            public void onFailure(Call<UserDomain> call, Throwable t) {
                Log.e("API_CALL", "Call failed: " + t.getMessage());
                showToast("Failed to fetch user data: " + t.getMessage());
            }
        });
    }


    private void displayUserData(UserDomain user) {
        binding.nameTxt.setText(user.getName());
        binding.fullnameTxt.setText(user.getFullName());
        binding.emailTxt.setText(user.getEmail());
        binding.phoneTxt.setText(user.getPhone());
        binding.addressTxt.setText(user.getAddress());
    }

    private void showToast(String message) {
        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
