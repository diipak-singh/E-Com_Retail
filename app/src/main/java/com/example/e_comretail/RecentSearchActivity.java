package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.ItemDisplayAdapter;
import com.example.e_comretail.Details.ItemDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class RecentSearchActivity extends AppCompatActivity {
    private ArrayList<ItemDetails> list;
    private DatabaseReference ref;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search History");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference().child("Recent Search/"
                + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        recyclerView = findViewById(R.id.recycler_recent);
        initList();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String image1 = list.get(position).getImageUrl();
                String image2 = list.get(position).getImageUrl2();
                String image3 = list.get(position).getImageUrl3();
                String image4 = list.get(position).getImageUrl4();
                String image5 = list.get(position).getImageUrl5();
                String image6 = list.get(position).getImageUrl6();
                ArrayList<String> imageList = new ArrayList<>();
                imageList.add(image1);
                imageList.add(image2);
                imageList.add(image3);
                imageList.add(image4);
                imageList.add(image5);
                imageList.add(image6);

                String ItemName = list.get(position).getItemName();
                String ItemPrice = list.get(position).getItemPrice();
                String Gst = list.get(position).getGstrate();
                String Desc = list.get(position).getItemDesc();
                String IsCertified = list.get(position).getIsCertified();
                String Measurement = list.get(position).getMeasurement();
                String Discount = list.get(position).getDiscount();
                String Stock = list.get(position).getStock();
                String ItemCode = list.get(position).getItemcode();

                Intent intent = new Intent(RecentSearchActivity.this, MainItemDsiplay.class);
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("ItemPrice", ItemPrice);
                intent.putExtra("Gst", Gst);
                intent.putExtra("Desc", Desc);
                intent.putExtra("IsCertified", IsCertified);
                intent.putExtra("Measurement", Measurement);
                intent.putExtra("Discount", Discount);
                intent.putExtra("Stock", Stock);
                intent.putExtra("ItemCode", ItemCode);
                intent.putExtra("HsnCode", list.get(position).getHsncode());
                intent.putStringArrayListExtra("images", imageList);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public void initList() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(ItemDetails.class));
                        }
                        ItemDisplayAdapter itemDisplayAdapter = new ItemDisplayAdapter(list, RecentSearchActivity.this);
                        recyclerView.setAdapter(itemDisplayAdapter);
                        itemDisplayAdapter.notifyDataSetChanged();
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