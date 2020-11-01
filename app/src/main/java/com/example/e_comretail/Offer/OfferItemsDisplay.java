package com.example.e_comretail.Offer;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.e_comretail.CartActivity;
import com.example.e_comretail.Details.ItemDetails;
import com.example.e_comretail.MainItemDsiplay;
import com.example.e_comretail.OrderSummaryActivity;
import com.example.e_comretail.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;

public class OfferItemsDisplay extends AppCompatActivity {

    private TextView itemName, itemPrice, itemFinalPrice, offer, description, measurement, Stock, QuantityText;
    private CarouselView carouselView;
    private ImageView isCertifeid;
    private Button buyNow;
    private EditText quantity;
    private ImageButton add, substract, GoBack, GotoCart;
    int q = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_items_display);

        Intent intent = getIntent();
        final String ItemName = intent.getStringExtra("ItemName");
        final String ItemPrice = intent.getStringExtra("ItemPrice");
        final String Gst = intent.getStringExtra("Gst");
        final String Offer = intent.getStringExtra("Offer");
        String Desc = intent.getStringExtra("Desc");
        String IsCertified = intent.getStringExtra("IsCertified");
        final String Measurement = intent.getStringExtra("Measurement");
        final String Discount = intent.getStringExtra("Discount");
        String stock = intent.getStringExtra("Stock");
        final String ItemCode = intent.getStringExtra("ItemCode");
        final String HsnCode = intent.getStringExtra("HsnCode");
        final ArrayList<String> imageList = intent.getStringArrayListExtra("images");

        assert stock != null;
        int stock1 = Integer.parseInt(stock);

        GoBack = findViewById(R.id.go_back);
        GotoCart = findViewById(R.id.go_to_cart);
        add = findViewById(R.id.add);
        substract = findViewById(R.id.substract);
        add.setVisibility(View.INVISIBLE);
        substract.setVisibility(View.INVISIBLE);
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
            add.setVisibility(View.VISIBLE);
            substract.setVisibility(View.VISIBLE);
        } else {
            Stock.setText("Out Of Stock");
            Stock.setTextColor(getResources().getColor(R.color.outOfStock));
            buyNow.setVisibility(View.INVISIBLE);
            quantity.setVisibility(View.INVISIBLE);
            QuantityText.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            substract.setVisibility(View.INVISIBLE);
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
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        GotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferItemsDisplay.this, CartActivity.class);
                startActivity(intent);
            }
        });

        itemName.setText(ItemName);
        offer.setText(Offer + "%" +" OFF");
        measurement.setText(Measurement);
        description.setText(Desc);

        //some calculation before setting
        // the itemPrice...
        final int ItemPriceAftrGst = Integer.parseInt(ItemPrice) + (Integer.parseInt(ItemPrice) * Integer.parseInt(Gst) / 100);
        final int ItemPriceAftrDiscount = ItemPriceAftrGst - (ItemPriceAftrGst * Integer.parseInt(Discount) / 100);
        final int itemPriceAfterOffer = ItemPriceAftrDiscount - (ItemPriceAftrDiscount * Integer.parseInt(Offer) / 100);
        itemPrice.setText(String.valueOf(ItemPriceAftrDiscount));
        itemFinalPrice.setText("₹" + Integer.valueOf(itemPriceAfterOffer));


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText().toString().trim().equals("")) {
                    quantity.setText("1");
                }
                int itemQuantity = Integer.parseInt(removeLeadingZeroes(quantity.getText().toString().trim()));
                int TotalPrice = ItemPriceAftrGst * itemQuantity;
                int OfferInRupee = (ItemPriceAftrGst * Integer.parseInt(Offer) / 100);
                int DiscountInRupee = (OfferInRupee + (ItemPriceAftrGst * Integer.parseInt(Discount) / 100)) * itemQuantity;
                int AmountPayable = itemPriceAfterOffer * itemQuantity;
                int deliveryCharge = 0;
                if (AmountPayable < 500){
                    deliveryCharge = 30;
                    AmountPayable = AmountPayable + deliveryCharge;
                }
                Intent intent = new Intent(OfferItemsDisplay.this, OrderSummaryActivity.class);
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
    }

    public static String removeLeadingZeroes(String str) {
        String strPattern = "^0+(?!$)";
        str = str.replaceAll(strPattern, "");
        return str;
    }

}