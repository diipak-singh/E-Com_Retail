package com.example.e_comretail.Offer;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.ItemDetails;
import com.example.e_comretail.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;

public class OfferItemsDisplay extends AppCompatActivity {
    private ArrayList<ItemDetails> list;

    private TextView itemName, itemPrice, itemFinalPrice, offer, description, measurement, Stock, QuantityText;
    private CarouselView carouselView;
    private ImageView isCertifeid;
    private Button buyNow;
    private Spinner quantity;

    String[] strQuantity = new String[]{"1", "2", "3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_items_display);

        Intent intent = getIntent();
        String ItemName = intent.getStringExtra("ItemName");
        String ItemPrice = intent.getStringExtra("ItemPrice");
        String Gst = intent.getStringExtra("Gst");
        String Offer = intent.getStringExtra("Offer");
        String Desc = intent.getStringExtra("Desc");
        String IsCertified = intent.getStringExtra("IsCertified");
        String Measurement = intent.getStringExtra("Measurement");
        String Discount = intent.getStringExtra("Discount");
        String stock = intent.getStringExtra("Stock");
        final ArrayList<String> imageList = intent.getStringArrayListExtra("images");

        assert stock != null;
        int stock1 = Integer.parseInt(stock);

        //Some Calculation to get final product price
        Double gstRate1 = Double.valueOf(Gst);
        Double ProductOffer1 = Double.valueOf(Offer);
        Double ProdcutDiscount = Double.valueOf(Discount);
        Double ProductActualPrice1 = Double.valueOf(ItemPrice);
        Double PriceAfterGst = ProductActualPrice1 + (ProductActualPrice1 * gstRate1 / 100);
        int PriceAfterDiscount = (int) (PriceAfterGst - (PriceAfterGst * ProdcutDiscount / 100));
        int ProductFinalPrice = (int) (PriceAfterDiscount - (PriceAfterDiscount * ProductOffer1 / 100));

        //Storing final product price in String to show in textview
        String ProductFinalPrice1 = String.valueOf(ProductFinalPrice);
        String PriceAfterDiscount1 = String.valueOf(PriceAfterDiscount);

        itemName = findViewById(R.id.product_name);
        itemPrice = findViewById(R.id.product_price);
        itemPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        itemFinalPrice = findViewById(R.id.product_final_price);
        offer = findViewById(R.id.offer);
        description = findViewById(R.id.description);
        isCertifeid = findViewById(R.id.certified);
        measurement = findViewById(R.id.measurement);
        Stock = findViewById(R.id.stock);

        QuantityText = findViewById(R.id.quantity_text);
        QuantityText.setVisibility(View.INVISIBLE);
        quantity = findViewById(R.id.quantity);
        quantity.setVisibility(View.INVISIBLE);
        buyNow = findViewById(R.id.buy);
        buyNow.setVisibility(View.INVISIBLE);
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(imageList.size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(OfferItemsDisplay.this)
                        .load(imageList.get(position))
                        .into(imageView);
            }
        });
        if (IsCertified.equals("Yes")) {
            isCertifeid.setImageResource(R.drawable.certified);
        } else {
            isCertifeid.setVisibility(View.GONE);
        }

        if (stock1 > 0) {
            Stock.setText("In Stock");
            Stock.setTextColor(getResources().getColor(R.color.inStock));
            buyNow.setVisibility(View.VISIBLE);
            quantity.setVisibility(View.VISIBLE);
            QuantityText.setVisibility(View.VISIBLE);
        } else {
            Stock.setText("Out Of Stock");
            Stock.setTextColor(getResources().getColor(R.color.outOfStock));
            buyNow.setVisibility(View.INVISIBLE);
            quantity.setVisibility(View.INVISIBLE);
            QuantityText.setVisibility(View.INVISIBLE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strQuantity);
        quantity.setAdapter(adapter);

        itemName.setText(ItemName);
        itemPrice.setText(PriceAfterDiscount1);
        itemFinalPrice.setText("₹" + ProductFinalPrice1);
        offer.setText(Offer + "%");
        description.setText(Desc);
        measurement.setText(Measurement);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferItemsDisplay.this, OfferOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}