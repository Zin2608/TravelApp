package com.example.travelapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelapp.Activity.DetailActivity;
import com.example.travelapp.Domain.PropertyDomain;
import com.example.travelapp.R;
import com.example.travelapp.databinding.ViewholderItemBinding;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final ArrayList<PropertyDomain> items;
    private final Context context;

    public ItemsAdapter(ArrayList<PropertyDomain> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderItemBinding binding = ViewholderItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PropertyDomain item = items.get(position);
        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.priceTxt.setText("$" + item.getPrice());
        holder.binding.typeTxt.setText(item.getType());
        holder.binding.addressTxt.setText(item.getAddress());
        holder.binding.scoreTxt.setText(String.valueOf(item.getScore()));
        holder.binding.bedTxt.setText(String.valueOf(item.getBed()));
        holder.binding.bathTxt.setText(String.valueOf(item.getBath()));
        // Sử dụng Glide để tải ảnh từ URL được xây dựng
        Glide.with(context)
                .load(item.getPicPath())  // Sử dụng đường dẫn đầy đủ từ picPath
                .into(holder.binding.pic);

        // Sự kiện click cho pic
        holder.binding.titleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
            }
        });
        // Thiết lập ảnh nguồn (drawable) cho nút favorite dựa trên trạng thái yêu thích của mục
        if (item.isFav()) {
            // Nếu mục được đánh dấu là yêu thích, sử dụng ảnh nguồn favorite2
            holder.binding.favbtn.setImageResource(R.drawable.favorite2);
        } else {
            // Nếu mục không được đánh dấu là yêu thích, sử dụng ảnh nguồn favorite
            holder.binding.favbtn.setImageResource(R.drawable.favorite);
        }

        // Sự kiện click cho nút favorite
        holder.binding.favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảo ngược trạng thái của mục là yêu thích
                item.setFav(!item.isFav());

                // Cập nhật giao diện người dùng để phản ánh trạng thái mới
                if (item.isFav()) {
                    // Nếu mục được đánh dấu là yêu thích, thực hiện các hành động tương ứng (ví dụ: thay đổi ảnh nguồn của nút)
                    holder.binding.favbtn.setImageResource(R.drawable.favorite2);
                } else {
                    // Nếu mục không được đánh dấu là yêu thích, thực hiện các hành động tương ứng (ví dụ: thay đổi ảnh nguồn của nút)
                    holder.binding.favbtn.setImageResource(R.drawable.favorite);
                }

                // Hiển thị thông báo
                Toast.makeText(context, "Favorite button clicked", Toast.LENGTH_SHORT).show();

                // Notify the adapter that data has changed
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderItemBinding binding;

        public ViewHolder(ViewholderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
