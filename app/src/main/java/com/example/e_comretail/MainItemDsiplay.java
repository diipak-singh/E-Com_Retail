package com.example.e_comretail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.CartDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainItemDsiplay extends AppCompatActivity {

    private TextView itemName, itemPrice, itemFinalPrice, discount, description, measurement, Stock, QuantityText;
    private CarouselView carouselView;
    private ImageView isCertifeid;
    private Button buyNow, AddToCart;
    private EditText quantity;
    private ImageButton add, substract;
    int q = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_item_dsiplay);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        final String ItemName = intent.getStringExtra("ItemName");
        String ItemPrice = intent.getStringExtra("ItemPrice");
        final String Gst = intent.getStringExtra("Gst");
        String Desc = intent.getStringExtra("Desc");
        String IsCertified = intent.getStringExtra("IsCertified");
        final String Measurement = intent.getStringExtra("Measurement");
        final String Discount = intent.getStringExtra("Discount");
        final String stock = intent.getStringExtra("Stock");
        final String ItemCode = intent.getStringExtra("ItemCode");
        final String HsnCode = intent.getStringExtra("HsnCode");
        final ArrayList<String> imageList = intent.getStringArrayListExtra("images");

        assert stock != null;
        int stock1 = Integer.parseInt(stock);

        add = findViewById(R.id.add);
        add.setVisibility(View.INVISIBLE);
        substract = findViewById(R.id.substract);
        substract.setVisibility(View.INVISIBLE);
        itemName = findViewById(R.id.product_name);
        itemPrice = findViewById(R.id.product_price);
        itemPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        itemFinalPrice = findViewById(R.id.product_final_price);
        discount = findViewById(R.id.cart_total_show);
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
        AddToCart = findViewById(R.id.add_to_cart);
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
            Stock.setTextColor(Color.GREEN);
            buyNow.setVisibility(View.VISIBLE);
            quantity.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            substract.setVisibility(View.VISIBLE);
            QuantityText.setVisibility(View.VISIBLE);
        } else {
            Stock.setText("Out Of Stock");
            Stock.setTextColor(Color.RED);
            buyNow.setVisibility(View.INVISIBLE);
            quantity.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            substract.setVisibility(View.INVISIBLE);
            QuantityText.setVisibility(View.INVISIBLE);
        }

        quantity.setText(String.valueOf(q));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity.setText(String.valueOf(++q));
            }
        });
        substract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q > 1)
                    quantity.setText(String.valueOf(--q));
            }
        });


        itemName.setText(ItemName);

        description.setText(Desc);
        measurement.setText(Measurement);
        //Some Calculation to get final product price
        final int ItemPriceAftrGst = Integer.parseInt(ItemPrice) + (Integer.parseInt(ItemPrice) * Integer.parseInt(Gst) / 100);
        final int ItemPriceAftrDiscount = ItemPriceAftrGst - (ItemPriceAftrGst * Integer.parseInt(Discount) / 100);

        itemPrice.setText(String.valueOf(ItemPriceAftrGst));
        itemFinalPrice.setText("₹" + String.valueOf(ItemPriceAftrDiscount));
        discount.setText(Discount + "%");

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText().toString().trim().equals("")) {
                    quantity.setText("1");
                }
                int itemQuantity = Integer.parseInt(removeLeadingZeroes(quantity.getText().toString().trim()));
                int TotalPrice = ItemPriceAftrGst * itemQuantity;
                int DiscountInRupee = (ItemPriceAftrGst * Integer.parseInt(Discount) / 100) * itemQuantity;
                int AmountPayable = ItemPriceAftrDiscount * itemQuantity;
                int deliveryCharge = 0;
                if (AmountPayable < 500){
                    deliveryCharge = 30;
                    AmountPayable = AmountPayable + deliveryCharge;
                }
                Intent intent = new Intent(MainItemDsiplay.this, OrderSummaryActivity.class);
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("Measurements", Measurement);
                intent.putExtra("Quantity", String.valueOf(itemQuantity));
                intent.putExtra("TotalPrice", String.valueOf(TotalPrice));
                intent.putExtra("Discount", String.valueOf(DiscountInRupee));
                intent.putExtra("AmountPayable", String.valueOf(AmountPayable));
                intent.putExtra("DeliveryCharge", String.valueOf(deliveryCharge));
                intent.putExtra("ItemCode", ItemCode);
                intent.putExtra("HsnCode", HsnCode);
                intent.putExtra("GstRate", Gst);
                intent.putExtra("TotalItems", "1");
                intent.putExtra("Image", imageList.get(0));
                startActivity(intent);
            }
        });

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText().toString().trim().equals("")) {
                    quantity.setText("1");
                }
                int itemQuantity = Integer.parseInt(removeLeadingZeroes(quantity.getText().toString().trim()));
                int TotalPrice = ItemPriceAftrGst * itemQuantity;
                int AmountPayable = ItemPriceAftrDiscount * itemQuantity;

                DatabaseReference dRefCart = FirebaseDatabase.getInstance().getReference().child("Cart Details/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                String itemId = dRefCart.push().getKey();
                CartDetails cartDetails = new CartDetails(ItemCode,
                        stock,
                        ItemName,
                        String.valueOf(TotalPrice),
                        Measurement,
                        String.valueOf(itemQuantity),
                        Discount,
                        String.valueOf(AmountPayable),
                        imageList.get(0),
                        itemId);
                dRefCart.child(itemId).setValue(cartDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(MainItemDsiplay.this, "Item added to cart Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public static String removeLeadingZeroes(String str) {
        String strPattern = "^0+(?!$)";
        str = str.replaceAll(strPattern, "");
        return str;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}