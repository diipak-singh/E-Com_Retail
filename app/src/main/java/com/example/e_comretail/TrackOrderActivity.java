package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class TrackOrderActivity extends AppCompatActivity {
    View view_order_placed, view_order_confirmed, view_order_processed, view_order_pickup, con_divider, ready_divider, placed_divider;
    ImageView img_orderconfirmed, orderprocessed, orderpickup;
    TextView textorderpickup, text_confirmed, textorderprocessed, estimatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Track Order");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        view_order_placed = findViewById(R.id.view_order_placed);
        view_order_confirmed = findViewById(R.id.view_order_confirmed);
        view_order_processed = findViewById(R.id.view_order_processed);
        view_order_pickup = findViewById(R.id.view_order_pickup);
        placed_divider = findViewById(R.id.placed_divider);
        con_divider = findViewById(R.id.con_divider);
        ready_divider = findViewById(R.id.ready_divider);

        textorderpickup = findViewById(R.id.textorderpickup);
        text_confirmed = findViewById(R.id.text_confirmed);
        textorderprocessed = findViewById(R.id.textorderprocessed);

        img_orderconfirmed = findViewById(R.id.img_orderconfirmed);
        orderprocessed = findViewById(R.id.orderprocessed);
        orderpickup = findViewById(R.id.orderpickup);

        estimatedTime = findViewById(R.id.textView3);
        Intent intent = getIntent();
        String orderStatus = intent.getStringExtra("orderStatus");
        String DeliveryTime = intent.getStringExtra("DeliveryTime");

        estimatedTime.setText(DeliveryTime);
        getOrderStatus(orderStatus);
    }

    private void getOrderStatus(String orderStatus) {
        if (orderStatus.equals("Order Placed")) {
            float alfa = (float) 0.5;
            setStatus(alfa);

        } else if (orderStatus.equals("Packed")) {
            float alfa = (float) 1;
            setStatus1(alfa);


        } else if (orderStatus.equals("Shipped")) {
            float alfa = (float) 1;
            setStatus2(alfa);


        } else if (orderStatus.equals("Delivered")) {
            float alfa = (float) 1;
            setStatus3(alfa);
        }
    }

    private void setStatus(float alfa) {
        float myf = (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderprocessed.setAlpha(alfa);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        placed_divider.setAlpha(alfa);
        img_orderconfirmed.setAlpha(alfa);
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderpickup.setAlpha(alfa);

        textorderpickup.setAlpha(myf);


    }

    private void setStatus1(float alfa) {
        float myf = (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(myf);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        img_orderconfirmed.setAlpha(alfa);

        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(myf);
        view_order_pickup.setAlpha(myf);
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        orderpickup.setAlpha(myf);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        textorderpickup.setAlpha(myf);
    }

    private void setStatus2(float alfa) {
        float myf = (float) 0.5;
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(alfa);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        img_orderconfirmed.setAlpha(alfa);

        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_current));
        textorderpickup.setAlpha(myf);
        orderpickup.setAlpha(myf);

    }

    private void setStatus3(float alfa) {
        view_order_placed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        view_order_confirmed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        orderprocessed.setAlpha(alfa);
        view_order_processed.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        con_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));

        img_orderconfirmed.setAlpha(alfa);
        placed_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        text_confirmed.setAlpha(alfa);
        textorderprocessed.setAlpha(alfa);
        view_order_pickup.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        ready_divider.setBackground(getResources().getDrawable(R.drawable.shape_status_completed));
        textorderpickup.setAlpha(alfa);
        orderpickup.setAlpha(alfa);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}