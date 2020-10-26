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
import com.example.e_comretail.Details.OfferDetails;
import com.example.e_comretail.R;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private ArrayList<OfferDetails> list;
    private Context context;

    public OfferAdapter(ArrayList<OfferDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_card_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ViewHolder holder, int position) {
        String ProductImage = list.get(position).getImageUrl();
        String ProductName = list.get(position).getItemName();
        String ItemPrice = list.get(position).getItemPrice();
        String Offer = list.get(position).getOffer();
        String OfferEndDate = list.get(position).getOfferdate();
        String Gst = list.get(position).getGstrate().trim();
        String Discount = list.get(position).getDiscount();

        // Some Calculation to get final product price
        int ItemPriceAftrGst = Integer.parseInt(ItemPrice) + (Integer.parseInt(ItemPrice) * Integer.parseInt(Gst) / 100);
        int ItemPriceAftrDiscount = ItemPriceAftrGst - (ItemPriceAftrGst * Integer.parseInt(Discount) / 100);
        int itemPriceAfterOffer = ItemPriceAftrDiscount - (ItemPriceAftrDiscount * Integer.parseInt(Offer) / 100);

        // placing values in holder
        Glide.with(context).load(ProductImage).into(holder.productImage);
        holder.productName.setText(ProductName);
        holder.productFinalPrice.setText("₹" + String.valueOf(itemPriceAfterOffer));
        holder.productPrice.setText(String.valueOf(ItemPriceAftrDiscount));
        holder.productPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productOffer.setText(Offer + "%");
        holder.offerEndDate.setText("Offer Ends On: " + OfferEndDate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, productOffer, productFinalPrice, offerEndDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageView2);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.actual_price);
            productFinalPrice = itemView.findViewById(R.id.final_price);
            productOffer = itemView.findViewById(R.id.offer);
            offerEndDate = itemView.findViewById(R.id.offer_end_date);
        }
    }
}
