package com.example.e_comretail.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.CartDetails;
import com.example.e_comretail.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<CartDetails> list;
    private Context context;

    public CartAdapter(ArrayList<CartDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(list.get(position).getItemName());
        holder.measurement.setText(list.get(position).getMeasurement());
        Glide.with(context).load(list.get(position).getItemImage()).into(holder.image);
        String strQuantity = list.get(position).getItemQuantity();
        String Discount = list.get(position).getDiscount();
        holder.quantity.setText("Qty: "+ strQuantity);
        holder.discount.setText(Discount+"%");
        holder.itemprice.setText(list.get(position).getItemPrice());
        holder.itemprice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.finalprice.setText(list.get(position).getAmountPayable());
        String Stock  = list.get(position).getStock();
        if (Stock.equals("0")){
            holder.stock.setText("Out of Stock");
            holder.stock.setTextColor(Color.RED);
        }else{
            holder.stock.setText("");
//            holder.stock.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView itemName, measurement, stock, quantity, discount, itemprice, finalprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView4);
            itemName = itemView.findViewById(R.id.item_name);
            measurement = itemView.findViewById(R.id.measurement);
            stock= itemView.findViewById(R.id.stock);
            quantity = itemView.findViewById(R.id.quantity);
            discount = itemView.findViewById(R.id.cart_total_show);
            itemprice = itemView.findViewById(R.id.actual_price);
            finalprice = itemView.findViewById(R.id.final_price);
        }
    }
    public static String removeLeadingZeroes(String str) {
        String strPattern = "^0+(?!$)";
        str = str.replaceAll(strPattern, "");
        return str;
    }
}
