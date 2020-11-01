package com.example.e_comretail.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Details.AddressDetails;
import com.example.e_comretail.R;
import com.example.e_comretail.ViewAddress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private ArrayList<AddressDetails> list;
    private Context context;

    public AddressAdapter(ArrayList<AddressDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_card_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        holder.Address.setText(list.get(position).getAddress());
        holder.Landmark.setText(list.get(position).getLandmark());
        holder.City.setText(list.get(position).getCity());
        holder.State.setText(list.get(position).getState());
        holder.Zip.setText(list.get(position).getZip());
        holder.Phone.setText(list.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Address, Landmark, City, State, Zip, Phone;
        private ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address = itemView.findViewById(R.id.address);
            Landmark = itemView.findViewById(R.id.landmark);
            City = itemView.findViewById(R.id.city);
            State = itemView.findViewById(R.id.state);
            Zip = itemView.findViewById(R.id.zip);
            Phone = itemView.findViewById(R.id.phone);

        }
    }
}
