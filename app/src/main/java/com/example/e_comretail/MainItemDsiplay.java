package com.example.e_comretail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.ItemDetails;
import com.example.e_comretail.Offer.OfferItemsDisplay;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainItemDsiplay extends AppCompatActivity {

    private ArrayList<ItemDetails> list;

    private TextView itemName, itemPrice, itemFinalPrice, discount, description, measurement, Stock, QuantityText;
    private CarouselView carouselView;
    private ImageView isCertifeid;
    private Button buyNow;
    private EditText quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_item_dsiplay);
        Intent intent = getIntent();
        String ItemName = intent.getStringExtra("ItemName");
        String ItemPrice = intent.getStringExtra("ItemPrice");
        String Gst = intent.getStringExtra("Gst");
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
        Double ProdcutDiscount = Double.valueOf(Discount);
        Double ProductActualPrice1 = Double.valueOf(ItemPrice);
        int PriceAfterGst = (int) (ProductActualPrice1 + (ProductActualPrice1 * gstRate1 / 100));
        int PriceAfterDiscount = (int) (PriceAfterGst - (PriceAfterGst * ProdcutDiscount / 100));

        //Storing final product price in String to show in textview
        String PriceAfterDiscount1 = String.valueOf(PriceAfterDiscount);
        String PriceAfterGst1 = String.valueOf(PriceAfterGst);

        itemName = findViewById(R.id.product_name);
        itemPrice = findViewById(R.id.product_price);
        itemPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        itemFinalPrice = findViewById(R.id.product_final_price);
        discount = findViewById(R.id.discount);
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
                Glide.with(MainItemDsiplay.this)
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

        itemName.setText(ItemName);
        itemPrice.setText(PriceAfterGst1);
        itemFinalPrice.setText("₹" + PriceAfterDiscount1);
        discount.setText(Discount + "%");
        description.setText(Desc);
        measurement.setText(Measurement);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}