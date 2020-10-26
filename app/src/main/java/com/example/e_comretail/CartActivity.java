package com.example.e_comretail;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.CartAdapter;
import com.example.e_comretail.Details.CartDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private ArrayList<CartDetails> list;
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private TextView totalItems, totalPrice, totalDiscount, deliveryCharges, amountPayable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ref = FirebaseDatabase.getInstance().getReference().child("Cart Details/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Cart");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recycler_cart);
        initCart();
        totalItems = findViewById(R.id.total_items);
        totalPrice = findViewById(R.id.total_price);
        totalDiscount = findViewById(R.id.discount);
        deliveryCharges = findViewById(R.id.delivery_charges);
        amountPayable = findViewById(R.id.amount_payable);
    }

    public void initCart() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(CartDetails.class));
                        }
                        CartAdapter cartAdapter = new CartAdapter(list, CartActivity.this);
                        recyclerView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(CartActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}