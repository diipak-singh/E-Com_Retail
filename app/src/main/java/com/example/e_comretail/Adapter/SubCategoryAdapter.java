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
import com.example.e_comretail.Details.SubCategoryDetails;
import com.example.e_comretail.R;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private ArrayList<SubCategoryDetails> list;
    private Context context;

    public SubCategoryAdapter(ArrayList<SubCategoryDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_card_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder holder, int position) {
        String image = list.get(position).getSubCategoryPhoto();
        Glide.with(context).load(image).into(holder.imageView);
        holder.subCategoryName.setText(list.get(position).getSubCategoryName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView subCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView7);
            subCategoryName = itemView.findViewById(R.id.sub_category_name);
        }
    }
}
