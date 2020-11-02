package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.OfferAdapter;
import com.example.e_comretail.Details.OfferDetails;
import com.example.e_comretail.Offer.OfferItemsDisplay;
import com.example.e_comretail.Offer.OfferRupeeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class OfferPercentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<OfferDetails> list;
    private DatabaseReference ref;
    String LowerRange;
    String UpperRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_percent);

        Intent intent = getIntent();
        LowerRange = intent.getStringExtra("LowerRange");
        UpperRange = intent.getStringExtra("UpperRange");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Offer Between " + LowerRange + "%" + " and " + UpperRange + "%");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference().child("Offer/");
        ref.keepSynced(true);
        recyclerView = findViewById(R.id.recycler_offer_percent);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //get all 6 images in array list
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

                //load all itemDetails and then send in intent
                String ItemName = list.get(position).getItemName();
                String ItemPrice = list.get(position).getItemPrice();
                String Gst = list.get(position).getGstrate();
                String Offer = list.get(position).getOffer();
                String Desc = list.get(position).getItemDesc();
                String IsCertified = list.get(position).getIsCertified();
                String Measurement = list.get(position).getMeasurement();
                String Discount = list.get(position).getDiscount();
                String Stock = list.get(position).getStock();
                String ItemCode = list.get(position).getItemcode();

                Intent intent = new Intent(OfferPercentActivity.this, OfferItemsDisplay.class);
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("ItemPrice", ItemPrice);
                intent.putExtra("Gst", Gst);
                intent.putExtra("Offer", Offer);
                intent.putExtra("Desc", Desc);
                intent.putExtra("IsCertified", IsCertified);
                intent.putExtra("Measurement", Measurement);
                intent.putExtra("Discount", Discount);
                intent.putExtra("Stock", Stock);
                intent.putExtra("ItemCode", ItemCode);
                intent.putExtra("HsnCode", list.get(position).getHsncode());
                intent.putExtra("GstRate", list.get(position).getGstrate());
                intent.putStringArrayListExtra("images", imageList);
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
                            if (Integer.parseInt(ds.getValue(OfferDetails.class).getOffer()) >= Integer.parseInt(LowerRange)
                                    && Integer.parseInt(ds.getValue(OfferDetails.class).getOffer()) <= Integer.parseInt(UpperRange))
                                list.add(ds.getValue(OfferDetails.class));
                        }
                        //item to be deleted when offer end date comes
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                        Date date = new Date();
                        String currentDate = formatter.format(date);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getOfferdate().equals(currentDate)) {
                                ref.child(list.get(i).getItemId()).removeValue();
                            }
                        }
                        OfferAdapter offerAdapter = new OfferAdapter(list, OfferPercentActivity.this);
                        recyclerView.setAdapter(offerAdapter);
                        offerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(OfferPercentActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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