package com.example.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.travelapp.Activity.MainActivity;
import com.example.travelapp.Activity.SignInActivity;
import com.example.travelapp.Activity.SignUpActivity;
import com.example.travelapp.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startbtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, SignUpActivity.class)));
        binding.signinTxt.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, SignInActivity.class)));

        // Set flags for full screen layout
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
