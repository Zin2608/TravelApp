package com.example.travelapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelapp.Domain.PropertyDomain;
import com.example.travelapp.R;
import com.example.travelapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    private PropertyDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();
        setVariable();
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());

        if (object != null) {
            Glide.with(this)
                    .load(object.getPicPath())  // Sử dụng đường dẫn đầy đủ từ picPath
                    .into(binding.picDetail);

            binding.titleTxt.setText(object.getTitle() + " in " + object.getAddress());
            binding.typeTxt.setText(object.getType());
            binding.descriptionTxt.setText(object.getDescription());
            binding.priceTxt.setText("$" + object.getPrice());
            binding.bedTxt.setText(String.valueOf(object.getBed()));
            binding.bathTxt.setText(String.valueOf(object.getBath()));
            binding.sizeTxt.setText(String.valueOf(object.getArea()));
            binding.scoreTxt.setText(String.valueOf(object.getScore()));
            binding.scoreTxt.setText(String.valueOf(object.getScore()));

            if (object.isGarage()) {
                binding.garageTxt.setText("Car garage");
            } else {
                binding.garageTxt.setText("No-Car garage");
            }
        }
        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "Room booked!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getBundles() {
        object = (PropertyDomain) getIntent().getSerializableExtra("object");
    }
}
