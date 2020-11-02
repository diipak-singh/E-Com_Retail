package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.RatingAdapter;
import com.example.e_comretail.Details.RatingDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ViewRating extends AppCompatActivity {
    String ItemCode;
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private ArrayList<RatingDetails> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rating);
        Intent intent = getIntent();
        ItemCode = intent.getStringExtra("ItemCode");
        recyclerView = findViewById(R.id.recycler_rating);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Rating");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference().child("Review Details");
        initRating();
    }

    public void initRating() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ItemCode.equals(ds.getValue(RatingDetails.class).getProductCode())) {
                                list.add(ds.getValue(RatingDetails.class));
                            }
                        }
                        RatingAdapter ratingAdapter = new RatingAdapter(list, ViewRating.this);
                        recyclerView.setAdapter(ratingAdapter);
                        ratingAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

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