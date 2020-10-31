package com.example.e_comretail.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.ItemDetails;
import com.example.e_comretail.R;

import java.util.ArrayList;

public class ItemDisplayAdapter extends RecyclerView.Adapter<ItemDisplayAdapter.ViewHolder> {
    private ArrayList<ItemDetails> list;
    private Context context;

    public ItemDisplayAdapter(ArrayList<ItemDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDisplayAdapter.ViewHolder holder, int position) {
        String ItemPrice = list.get(position).getItemPrice();
        String Gst = list.get(position).getGstrate();
        String Discount = list.get(position).getDiscount();
        //some calculation
        int ItemPriceAftrGst = Integer.parseInt(ItemPrice) + (Integer.parseInt(ItemPrice) * Integer.parseInt(Gst) / 100);
        int ItemPriceAftrDiscount = ItemPriceAftrGst - (ItemPriceAftrGst * Integer.parseInt(Discount) / 100);

        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.itemPhoto);
        holder.itemName.setText(list.get(position).getItemName());
        holder.measurement.setText(list.get(position).getMeasurement());
        holder.actualPrice.setText(String.valueOf(ItemPriceAftrGst));
        holder.actualPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.finalPrice.setText("₹" + String.valueOf(ItemPriceAftrDiscount));
        holder.discount.setText(Discount + "%");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemPhoto;
        private TextView itemName, measurement, finalPrice, actualPrice, discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPhoto = itemView.findViewById(R.id.imageView4);
            itemName = itemView.findViewById(R.id.item_name);
            measurement = itemView.findViewById(R.id.measurement);
            finalPrice = itemView.findViewById(R.id.final_price);
            actualPrice = itemView.findViewById(R.id.actual_price);
            discount = itemView.findViewById(R.id.cart_total_show);
        }
    }
}
