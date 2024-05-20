package com.example.travelapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.travelapp.API.UserApi;
import com.example.travelapp.Domain.UserDomain;
import com.example.travelapp.R;
import com.example.travelapp.UserRequest;
import com.example.travelapp.databinding.ActivitySignInBinding;
import com.example.travelapp.databinding.ActivitySignUpBinding;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.154.1:9092")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        userApi = retrofit.create(UserApi.class);

        binding.signupBtn.setOnClickListener(v -> addUser());
    }
    private void addUser() {
        // Ở đây, bạn cần lấy dữ liệu từ các trường nhập liệu và tạo một đối tượng UserRequest
        // sau đó gửi yêu cầu đăng ký thông qua userApi

        // Ví dụ:
        String name = binding.signupyournameTxt.getText().toString();
        String fullName = binding.signupyourfullnameTxt.getText().toString();
        String email = binding.signupyouremailTxt.getText().toString();
        String phone = binding.signupyourphoneTxt.getText().toString();
        String address = binding.signupyouraddressTxt.getText().toString();
        String password = binding.signupyourpasswordTxt.getText().toString();

        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setFullName(fullName);
        userRequest.setEmail(email);
        userRequest.setPhone(phone);
        userRequest.setAddress(address);
        userRequest.setPassword(password);

        // Gửi yêu cầu đăng ký thông qua Retrofit
        Call<UserDomain> call = userApi.addUser(userRequest);
        call.enqueue(new Callback<UserDomain>() {
            @Override
            public void onResponse(Call<UserDomain> call, Response<UserDomain> response) {
                // Xử lý phản hồi thành công từ server
                if (response.isSuccessful()) {
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Xử lý trường hợp đăng ký không thành công (ví dụ: hiển thị thông báo lỗi)
                }
            }

            @Override
            public void onFailure(Call<UserDomain> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối hoặc lỗi khác
            }
        });
    }

}