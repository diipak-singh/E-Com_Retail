package com.example.e_comretail.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Details.ItemDetails;
import com.example.e_comretail.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemDisplayAdapter extends RecyclerView.Adapter<ItemDisplayAdapter.ViewHolder>{
    private ArrayList<ItemDetails> list;
    private Context context;

    public ItemDisplayAdapter(ArrayList<ItemDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDisplayAdapter.ViewHolder holder, int position) {
        String ProductImage = list.get(position).getImageUrl();
        String ProductName = list.get(position).getItemName();
        String ProductActualPrice = list.get(position).getItemPrice();
        String ProductOffer = list.get(position).getOffer();
        String OfferEndDate = list.get(position).getOfferdate();
        String gstRate = list.get(position).getGstrate().trim();
        String isCertified = list.get(position).getIsCertified();

        //Some Calculation to get final product price
        Double gstRate1 = Double.valueOf(gstRate);
        Double ProductOffer1 = Double.valueOf(ProductOffer);
        Double ProductActualPrice1 = Double.valueOf(ProductActualPrice);
        Double PriceAfterGst = ProductActualPrice1 + (ProductActualPrice1 * gstRate1 / 100);
        Double ProductFinalPrice = PriceAfterGst - (PriceAfterGst * ProductOffer1 / 100);

        //Storing final product price in String to show in textview
        String ProductFinalPrice1 = String.valueOf(ProductFinalPrice);
        String PriceAfterGst1 = String.valueOf(PriceAfterGst);

        Picasso.get().load(ProductImage).into(holder.productImage1);
//        if (isCertified.equals("Yes")){
//            holder.certified.setImageResource(R.drawable.certified);
//        }
        holder.productName.setText(ProductName);
        holder.productFinalPrice.setText(ProductFinalPrice1);
        holder.productPrice.setText(PriceAfterGst1);
        holder.productOffer.setText(ProductOffer);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage1, certified;
        private TextView productName, productFinalPrice , productPrice, productOffer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage1 = itemView.findViewById(R.id.imageView3);
            productName = itemView.findViewById(R.id.product_name);
            productFinalPrice = itemView.findViewById(R.id.product_final_price);
            productPrice = itemView.findViewById(R.id.product_price);
            productOffer = itemView.findViewById(R.id.offer);
            certified = itemView.findViewById(R.id.imageView);

        }
    }
}
