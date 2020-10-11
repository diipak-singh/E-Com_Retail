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

import com.example.e_comretail.Details.OfferDetails;
import com.example.e_comretail.R;
import com.squareup.picasso.Picasso;

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
        String ProductImage = list.get(position).getProductImageUrl();
        String ProductName = list.get(position).getProductname();
        String ProductActualPrice = list.get(position).getProductprice();
        String ProductOffer = list.get(position).getOffermade();
        String OfferEndDate = list.get(position).getOfferenddate();
        String gstRate = list.get(position).getGstRate().trim();

        //Some Calculation to get final product price
        Double gstRate1 = Double.valueOf(gstRate);
        Double ProductOffer1 = Double.valueOf(ProductOffer);
        Double ProductActualPrice1 = Double.valueOf(ProductActualPrice);
        Double PriceAfterGst = ProductActualPrice1 + (ProductActualPrice1 * gstRate1 / 100);
        Double ProductFinalPrice = PriceAfterGst - (PriceAfterGst * ProductOffer1 / 100);

        //Storing final product price in String to show in textview
        String ProductFinalPrice1 = String.valueOf(ProductFinalPrice);

        // placing values in holder
        Picasso.get().load(ProductImage).into(holder.productImage);
        holder.productName.setText(ProductName);
        holder.productFinalPrice.setText("₹" + ProductFinalPrice1);
        holder.productActualPrice.setText(ProductActualPrice);
        holder.productActualPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productOffer.setText(ProductOffer + "%");
        holder.offerEndDate.setText("Offer Ends On: " + OfferEndDate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productActualPrice, productOffer, productFinalPrice, offerEndDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageView2);
            productName = itemView.findViewById(R.id.product_name);
            productActualPrice = itemView.findViewById(R.id.actual_price);
            productFinalPrice = itemView.findViewById(R.id.final_price);
            productOffer = itemView.findViewById(R.id.offer);
            offerEndDate = itemView.findViewById(R.id.offer_end_date);
        }
    }
}
