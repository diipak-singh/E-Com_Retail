package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.AllCategoryAdapter;
import com.example.e_comretail.Adapter.ItemDisplayAdapter;
import com.example.e_comretail.Details.AllCategoryDetails;
import com.example.e_comretail.Details.ItemDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OfferItemsDisplay extends AppCompatActivity {
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private ArrayList<ItemDetails> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_items_display);
        Intent intent = getIntent();
        String Category = intent.getStringExtra("Category");
        String SubCategory = intent.getStringExtra("SubCategory");
        String ProductId = intent.getStringExtra("ProductId");
        ref = FirebaseDatabase.getInstance().getReference().child("Items").child(Category).child(SubCategory).child(ProductId);
        recyclerView = findViewById(R.id.recycler_item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(ItemDetails.class));
                        }
                        ItemDisplayAdapter itemDisplayAdapter = new ItemDisplayAdapter(list, OfferItemsDisplay.this);
                        recyclerView.setAdapter(itemDisplayAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(OfferItemsDisplay.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}