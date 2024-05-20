package com.example.travelapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.API.UserApi;
import com.example.travelapp.UserRequest;
import com.example.travelapp.databinding.ActivitySignInBinding;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.154.1:9092")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        userApi = retrofit.create(UserApi.class);

        binding.signinBtn.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = binding.youremailTxt.getText().toString().trim();
        String password = binding.yourpasswordTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo một đối tượng JSON chứa email và mật khẩu
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Chuyển đối tượng JSON thành chuỗi
        String requestBody = jsonObject.toString();

        // Tạo một RequestBody từ chuỗi JSON
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);

        // Gửi yêu cầu Retrofit với RequestBody này
        Call<String> call = userApi.loginUser(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body();
                    Toast.makeText(SignInActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (message.equals("Login successful")) {
                        // Navigate to MainActivity if login is successful
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish(); // Finish SignInActivity
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Failed to login. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("API_CALL", "Response code: " + response.code() + ", Response message: " + response.message());
                    try {
                        Log.e("API_CALL", "Error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        Log.e("API_CALL", "Error parsing error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("API_CALL", "Login call failed: " + t.getMessage());
                Toast.makeText(SignInActivity.this, "Failed to login. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
