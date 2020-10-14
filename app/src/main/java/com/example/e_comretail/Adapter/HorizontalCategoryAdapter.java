package com.example.e_comretail.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.AllCategoryDetails;
import com.example.e_comretail.R;

import java.util.ArrayList;

public class HorizontalCategoryAdapter extends RecyclerView.Adapter<HorizontalCategoryAdapter.ViewHolder> {
    private ArrayList<AllCategoryDetails> list;
    private Context context;

    public HorizontalCategoryAdapter(ArrayList<AllCategoryDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HorizontalCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_category_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalCategoryAdapter.ViewHolder holder, int position) {
        String categoryImage = list.get(position).getCategotyphoto();
        String categoryName = list.get(position).getCategoryname();
        Glide.with(context).load(categoryImage).into(holder.CategoryImage);
        holder.CategoryName.setText(categoryName);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView CategoryImage;
        private TextView CategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryName = itemView.findViewById(R.id.category_name);
            CategoryImage = itemView.findViewById(R.id.category_image);
        }
    }
}
