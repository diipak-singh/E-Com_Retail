package com.example.e_comretail.Offer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.e_comretail.AddNewAddress;
import com.example.e_comretail.Details.AddressDetails;
import com.example.e_comretail.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class OfferOrderActivity extends AppCompatActivity {
    private ArrayList<AddressDetails> addressList;
    private DatabaseReference ref;
    private TextView Address, Landmark, City, State, Zip, Phone;
    private TextView itemName, measurement, quantity, totalPrice, discount, amountPayable;
    private CardView addressCard;
    private Button shippingAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_order);
        Intent intent = getIntent();
        String ItemName = intent.getStringExtra("ItemName");
        String Measurement = intent.getStringExtra("Measurements");
        String Quantity = intent.getStringExtra("Quantity");
        String TotalPrice = intent.getStringExtra("TotalPrice");
        String Discount = intent.getStringExtra("Discount");
        String AmountPayable = intent.getStringExtra("AmountPayable");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Summary");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference().child("Address/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        initAddressList();

        Address = findViewById(R.id.address);
        Landmark = findViewById(R.id.landmark);
        City = findViewById(R.id.city);
        State = findViewById(R.id.state);
        Zip = findViewById(R.id.zip);
        Phone = findViewById(R.id.phone);
        addressCard = findViewById(R.id.cardView3);

        itemName = findViewById(R.id.item_name);
        measurement = findViewById(R.id.item_highlights);
        quantity = findViewById(R.id.quantity);
        totalPrice = findViewById(R.id.item_price);
        discount = findViewById(R.id.discount);
        amountPayable = findViewById(R.id.amount_payable);

        itemName.setText(ItemName);
        measurement.setText(Measurement);
        quantity.setText(Quantity);
        totalPrice.setText(TotalPrice);
        discount.setText(Discount);
        amountPayable.setText(AmountPayable);

        addressCard.setVisibility(View.GONE);
        shippingAddress = findViewById(R.id.shipping_address);
        shippingAddress.setText("Change Shipping Address");
        shippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferOrderActivity.this, AddNewAddress.class);
                startActivity(intent);
            }
        });
    }

    public void initAddressList() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        addressList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            addressList.add(ds.getValue(AddressDetails.class));
                        }
                        if (addressList.size() > 0){
                            addressCard.setVisibility(View.VISIBLE);
                            Address.setText(addressList.get(0).getAddress());
                            Landmark.setText(addressList.get(0).getLandmark());
                            City.setText(addressList.get(0).getCity());
                            State.setText(addressList.get(0).getState());
                            Zip.setText(addressList.get(0).getZip());
                            Phone.setText(addressList.get(0).getPhone());
                            shippingAddress.setVisibility(View.GONE);

                        }else{
                            addressCard.setVisibility(View.GONE);
                            shippingAddress.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}