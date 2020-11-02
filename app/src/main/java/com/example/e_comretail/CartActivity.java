package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.CartAdapter;
import com.example.e_comretail.Details.CartDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity implements Serializable {
    private ArrayList<CartDetails> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private TextView totalItems, cartSum, deliveryCharges, amountPayable;
    long cartTotal = 0;
    int deliveryCharge = 0;
    private final String INR = "₹";
    private Button placeOrder;
    private CardView cardView;

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
        totalItems = findViewById(R.id.total_items_show);
        cartSum = findViewById(R.id.cart_total_show);
        deliveryCharges = findViewById(R.id.delivery_charges_show);
        amountPayable = findViewById(R.id.amount_payable_show);
        cardView = findViewById(R.id.cardView4);
        placeOrder = findViewById(R.id.place_order);
        placeOrder.setVisibility(View.GONE);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CartCheckoutActivity.class);
                intent.putExtra("cartDetails", list);
                startActivity(intent);
            }
        });
        initCart();
    }

    public void initCart() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (list.size() > 0)
                        list.clear();
                    cartTotal = 0;

                    if (dataSnapshot.exists()) {
                        //Todo : this is good to check whether snapshot exists or not instead of checking ref!=null
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(CartDetails.class));
                            cartTotal = cartTotal + Integer.parseInt(ds.getValue(CartDetails.class).getAmountPayable());
                        }

                        totalItems.setText(String.valueOf(list.size()));
                        cartSum.setText(INR + String.valueOf(cartTotal));
                        if (cartTotal < 500) {
                            deliveryCharge = 30;
                            deliveryCharges.setText("+" + INR + String.valueOf(deliveryCharge));
                        } else {
                            deliveryCharges.setText("FREE");
                        }
                        placeOrder.setVisibility(View.VISIBLE);
                        amountPayable.setText(INR + String.valueOf((cartTotal + deliveryCharge)));

                        CartAdapter cartAdapter = new CartAdapter(list, CartActivity.this);
                        recyclerView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    } else {
                        placeOrder.setVisibility(View.GONE);
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