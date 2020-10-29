package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.e_comretail.Details.OrderDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ViewOrder extends AppCompatActivity {
    private Button TrackOrder, CancelOrder, ReOrder;
    private DatabaseReference ref;
    private TextView userName, userAddress, userLandmark, userCity, userZip, userState, userPhone, itemName, hsnCode, quantity, totalAmount, itemHighlights;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ref = FirebaseDatabase.getInstance().getReference().child("OrderDetails/");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("OrderID");
        String UserAddress = intent.getStringExtra("UserAddress");
        String UserState = intent.getStringExtra("UserState");
        String UserLandmark = intent.getStringExtra("UserLandmark");
        String UserName = intent.getStringExtra("UserName");
        String Measurement = intent.getStringExtra("Measurement");
        String UserCity = intent.getStringExtra("UserCity");
        String UserZip = intent.getStringExtra("UserZip");
        String UserPhone = intent.getStringExtra("UserPhone");
        String ItemName = intent.getStringExtra("ItemName");
        String HsnCode = intent.getStringExtra("HsnCode");
        String GstRate = intent.getStringExtra("GstRate");
        String Quantity = intent.getStringExtra("Quantity");
        String AmountPayable = intent.getStringExtra("AmountPayable");
        final String OrderStatus = intent.getStringExtra("OrderStatus");
        final String DeliveryTime = intent.getStringExtra("DeliveryTime");

        TrackOrder = findViewById(R.id.track_order);
        CancelOrder = findViewById(R.id.cancel_order);
        ReOrder = findViewById(R.id.reorder);
        ReOrder.setVisibility(View.GONE);
        userName = findViewById(R.id.name);
        userAddress = findViewById(R.id.address);
        userLandmark = findViewById(R.id.landmark);
        userCity = findViewById(R.id.city);
        userZip = findViewById(R.id.zip);
        userState = findViewById(R.id.state);
        userPhone = findViewById(R.id.phone);
        itemName = findViewById(R.id.item_name);
        hsnCode = findViewById(R.id.hsn_code);
        quantity = findViewById(R.id.quantity);
        totalAmount = findViewById(R.id.amount_payable);
        itemHighlights = findViewById(R.id.item_highlights);

        userName.setText(UserName);
        userAddress.setText(UserAddress);
        userState.setText(UserState);
        userCity.setText(UserCity);
        userZip.setText(UserZip);
        userLandmark.setText(UserLandmark);
        userPhone.setText(UserPhone);
        itemName.setText(ItemName);
        hsnCode.setText("HSN Code: " + HsnCode + "  GST Rate: " + GstRate);
        quantity.setText(Quantity);
        totalAmount.setText(AmountPayable);
        itemHighlights.setText(Measurement);
        if (OrderStatus.equals("Cancelled")){
            CancelOrder.setVisibility(View.GONE);
            TrackOrder.setVisibility(View.GONE);
            ReOrder.setVisibility(View.VISIBLE);
        }
        if (OrderStatus.equals("Shipped")){
            CancelOrder.setVisibility(View.GONE);
        }
        CancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(orderId).child("orderStatus").setValue("Cancelled");
                CancelOrder.setVisibility(View.GONE);
                TrackOrder.setVisibility(View.GONE);
                Intent intent1 = new Intent(ViewOrder.this, AfterOrderActivity.class);
                intent1.putExtra("Title", "Order Cancelled!");
                intent1.putExtra("Desc", "Thanks you for shopping with us! \n your order was cancelled successfully.");
                startActivity(intent1);
                finish();
            }
        });
        ReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(orderId).child("orderStatus").setValue("Order Placed");
                ReOrder.setVisibility(View.GONE);
                Intent intent1 = new Intent(ViewOrder.this, AfterOrderActivity.class);
                intent1.putExtra("Title", "Re-order Successful!");
                intent1.putExtra("Desc", "Thanks you for shopping with us! \n your order was placed again successfully.");
                startActivity(intent1);
                finish();
            }
        });
        TrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ViewOrder.this, TrackOrderActivity.class);
                intent2.putExtra("orderStatus", OrderStatus);
                intent2.putExtra("DeliveryTime", DeliveryTime);
                startActivity(intent2);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}