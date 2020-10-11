package com.example.e_comretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_comretail.Adapter.AllCategoryAdapter;
import com.example.e_comretail.Adapter.OfferAdapter;
import com.example.e_comretail.Details.AllCategoryDetails;
import com.example.e_comretail.Details.OfferDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class OfferActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<OfferDetails> list;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Offer Zone");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference().child("Offer/");
        ref.keepSynced(true);
        recyclerView = findViewById(R.id.recycler_offer);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,recyclerView,  new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(OfferActivity.this, OfferItemsDisplay.class);
                intent.putExtra("Category", list.get(position).getOffercategory());
                intent.putExtra("SubCategory", list.get(position).getOfferSubCategory());
                intent.putExtra("ProductId", list.get(position).getProductID());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
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
                            list.add(ds.getValue(OfferDetails.class));
                        }
                        OfferAdapter offerAdapter = new OfferAdapter(list, OfferActivity.this);
                        recyclerView.setAdapter(offerAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(OfferActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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