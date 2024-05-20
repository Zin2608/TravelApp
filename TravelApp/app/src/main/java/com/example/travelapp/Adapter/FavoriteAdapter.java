package com.example.travelapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.travelapp.API.PropertyApi;
import com.example.travelapp.Domain.PropertyDomain;
import com.example.travelapp.R;
import com.example.travelapp.databinding.ViewholderItemBinding;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private final ArrayList<PropertyDomain> favoriteItems;
    private final Context context;
    private final PropertyApi propertyApi;

    public FavoriteAdapter(ArrayList<PropertyDomain> favoriteItems, Context context, PropertyApi propertyApi) {
        this.favoriteItems = favoriteItems;
        this.context = context;
        this.propertyApi = propertyApi;
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
        PropertyDomain item = favoriteItems.get(position);
        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.priceTxt.setText("$" + item.getPrice());
        holder.binding.typeTxt.setText(item.getType());
        holder.binding.addressTxt.setText(item.getAddress());
        holder.binding.scoreTxt.setText(String.valueOf(item.getScore()));
        holder.binding.bedTxt.setText(String.valueOf(item.getBed()));
        holder.binding.bathTxt.setText(String.valueOf(item.getBath()));
        Glide.with(context)
                .load(item.getPicPath())
                .into(holder.binding.pic);

        // Update favorite button visibility
        updateFav(item.isFav(), holder);

        // Set onClickListener for favorite button
        holder.binding.favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle favorite status
                boolean newFavStatus = !item.isFav();
                item.setFav(newFavStatus);
                // Update favorite button visibility
                updateFav(newFavStatus, holder);
                // Send API request to update favorite status
                propertyApi.updateFavoriteStatus(item.getTitle(), newFavStatus).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()) {
                            // Revert the change if the request failed
                            item.setFav(!newFavStatus);
                            updateFav(!newFavStatus, holder);
                            Toast.makeText(context, "Failed to update favorite status", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Revert the change if the request failed
                        item.setFav(!newFavStatus);
                        updateFav(!newFavStatus, holder);
                        Toast.makeText(context, "Call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderItemBinding binding;

        public ViewHolder(ViewholderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Method to update favorite button visibility
    private void updateFav(boolean isFavorite, ViewHolder holder) {
        if (isFavorite) {
            holder.binding.favbtn.setImageResource(R.drawable.favorite2);
        } else {
            holder.binding.favbtn.setImageResource(R.drawable.favorite);
        }
    }
}