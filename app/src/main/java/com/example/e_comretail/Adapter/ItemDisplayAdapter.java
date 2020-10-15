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
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.itemPhoto);
        holder.itemName.setText(list.get(position).getItemName());
        holder.measurement.setText(list.get(position).getMeasurement());

        String Gst = list.get(position).getGstrate();
        Double Gst1 = Double.valueOf(Gst);

        String itemPrice = list.get(position).getItemPrice();
        Double itemPrice1 = Double.valueOf(itemPrice);

        String Discount = list.get(position).getDiscount();
        Double Discount1 = Double.valueOf(Discount);

        int PriceAfterGst = (int) (itemPrice1 + (itemPrice1 * Gst1 / 100));
        int FinalPrice = (int) (PriceAfterGst - (PriceAfterGst * Discount1 / 100));

        holder.actualPrice.setText(String.valueOf(PriceAfterGst));
        holder.actualPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.finalPrice.setText("₹" + String.valueOf(FinalPrice));
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
            discount = itemView.findViewById(R.id.discount);
        }
    }
}
