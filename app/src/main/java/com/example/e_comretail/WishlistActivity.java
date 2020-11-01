package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.ItemDisplayAdapter;
import com.example.e_comretail.Adapter.WishListAdapter;
import com.example.e_comretail.Details.ItemDetails;
import com.example.e_comretail.Details.WishlistDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class WishlistActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private ArrayList<WishlistDetails> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My WishList");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recycler_wishlist);
        ref = FirebaseDatabase.getInstance().getReference().child("WishList Details/"
                + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        initList();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String image1 = list.get(position).getImage1();
                String image2 = list.get(position).getImage2();
                String image3 = list.get(position).getImage3();
                String image4 = list.get(position).getImage4();
                String image5 = list.get(position).getImage5();
                String image6 = list.get(position).getImage6();
                ArrayList<String> imageList = new ArrayList<>();
                imageList.add(image1);
                imageList.add(image2);
                imageList.add(image3);
                imageList.add(image4);
                imageList.add(image5);
                imageList.add(image6);

                String ItemName = list.get(position).getItemName();
                String ItemPrice = list.get(position).getItemPrice();
                String Gst = list.get(position).getGst();
                String Desc = list.get(position).getDesc();
                String IsCertified = list.get(position).getIsCertified();
                String Measurement = list.get(position).getMeasurement();
                String Discount = list.get(position).getDiscount();
                String Stock = list.get(position).getStock();
                String ItemCode = list.get(position).getItemCode();

                Intent intent = new Intent(WishlistActivity.this, MainItemDsiplay.class);
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("ItemPrice", ItemPrice);
                intent.putExtra("Gst", Gst);
                intent.putExtra("Desc", Desc);
                intent.putExtra("IsCertified", IsCertified);
                intent.putExtra("Measurement", Measurement);
                intent.putExtra("Discount", Discount);
                intent.putExtra("Stock", Stock);
                intent.putExtra("ItemCode", ItemCode);
                intent.putExtra("HsnCode", list.get(position).getHsnCode());
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
                            list.add(ds.getValue(WishlistDetails.class));
                        }
                        WishListAdapter wishListAdapter = new WishListAdapter(list, WishlistActivity.this);
                        recyclerView.setAdapter(wishListAdapter);
                        wishListAdapter.notifyDataSetChanged();
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