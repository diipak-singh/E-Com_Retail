package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_comretail.Details.ItemDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class OfferItemsDisplay extends AppCompatActivity {
    private DatabaseReference ref;
    private ArrayList<ItemDetails> list;

    //widget declaration
    private ImageView certified;
    private TextView productName, productFinalPrice, productPrice, productOffer;
    int pos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_items_display);
        Intent intent = getIntent();
        String Category = intent.getStringExtra("Category");
        String SubCategory = intent.getStringExtra("SubCategory");
        String ProductId = intent.getStringExtra("ProductId");
        String Position = intent.getStringExtra("Position");
        pos = Integer.parseInt(Position);
        ref = FirebaseDatabase.getInstance().getReference().child("Items").child(Category).child(SubCategory).child(ProductId).getParent();

        //widget initialization
        productName = findViewById(R.id.product_name);
        productFinalPrice = findViewById(R.id.product_final_price);
        productPrice = findViewById(R.id.product_price);
        productOffer = findViewById(R.id.offer);
        certified = findViewById(R.id.imageView);

        RetriveData();
    }

    public void RetriveData() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(ItemDetails.class));
                        }
                        String imageUrl = list.get(pos).getImageUrl();
                        String imageUrl2 = list.get(pos).getImageUrl2();
                        String imageUrl3 = list.get(pos).getImageUrl3();
                        String imageUrl4 = list.get(pos).getImageUrl4();
                        String imageUrl5 = list.get(pos).getImageUrl5();
                        String imageUrl6 = list.get(pos).getImageUrl6();
//                        Toast.makeText(OfferItemsDisplay.this, imageUrl, Toast.LENGTH_SHORT).show();
                        final String[] images = {imageUrl, imageUrl2, imageUrl3, imageUrl3, imageUrl4, imageUrl4,imageUrl6};
                        CarouselView carouselView = findViewById(R.id.carousel);
                        carouselView.setPageCount(images.length);
                        carouselView.setImageListener(new ImageListener() {
                            @Override
                            public void setImageForPosition(int position, ImageView imageView) {
                                Glide.with(OfferItemsDisplay.this).load(images[position]).into(imageView);
                            }
                        });

                        productName.setText(list.get(pos).getItemName());
                        productPrice.setText(list.get(pos).getItemPrice());
                        productOffer.setText(list.get(pos).getOffer());
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