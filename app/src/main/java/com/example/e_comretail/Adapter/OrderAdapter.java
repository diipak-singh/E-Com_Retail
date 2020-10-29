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
import com.example.e_comretail.Details.OrderDetails;
import com.example.e_comretail.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<OrderDetails> list;
    private Context context;

    public OrderAdapter(ArrayList<OrderDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.OrderStatus.setText(list.get(position).getOrderStatus());
        holder.ItemName.setText(list.get(position).getItemName());
        holder.Quantity.setText("Quantity: "+list.get(position).getItemQuantity());
        holder.OrderDate.setText("Order Date: "+list.get(position).getOrderDate());
        Glide.with(context).load(list.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView OrderStatus, ItemName, Quantity, OrderDate;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            OrderStatus = itemView.findViewById(R.id.order_status);
            ItemName = itemView.findViewById(R.id.item_name);
            Quantity = itemView.findViewById(R.id.quantity);
            OrderDate = itemView.findViewById(R.id.order_date);
        }
    }
}
