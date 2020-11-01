package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.AllCategoryAdapter;
import com.example.e_comretail.Adapter.OrderAdapter;
import com.example.e_comretail.Details.AllCategoryDetails;
import com.example.e_comretail.Details.OrderDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class OrderDisplayActivity extends AppCompatActivity {
    private ArrayList<OrderDetails> list;
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Orders");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recycler_order);
        ref = FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        initOrderList();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(OrderDisplayActivity.this, ViewOrder.class);
                intent.putExtra("OrderID", list.get(position).getOrderID());
                intent.putExtra("ItemCode", list.get(position).getItemCode());
                intent.putExtra("UserAddress", list.get(position).getUserAddress());
                intent.putExtra("UserState", list.get(position).getUserState());
                intent.putExtra("UserLandmark", list.get(position).getUserLandMark());
                intent.putExtra("UserName", list.get(position).getUserName());
                intent.putExtra("Measurement", list.get(position).getMeasurement());
                intent.putExtra("UserCity", list.get(position).getUserCity());
                intent.putExtra("UserZip", list.get(position).getUserZip());
                intent.putExtra("UserPhone", list.get(position).getUserPhone());
                intent.putExtra("ItemName", list.get(position).getItemName());
                intent.putExtra("HsnCode", list.get(position).getHsnCode());
                intent.putExtra("GstRate", list.get(position).getGstRate());
                intent.putExtra("Quantity", list.get(position).getItemQuantity());
                intent.putExtra("AmountPayable", list.get(position).getTotalAmount());
                intent.putExtra("OrderStatus", list.get(position).getOrderStatus());
                intent.putExtra("DeliveryTime", list.get(position).getEstimatedTime());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public void initOrderList() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.getUid().equals(ds.getValue(OrderDetails.class).getUserID())){
                                list.add(ds.getValue(OrderDetails.class));
                            }
                        }
                        Collections.reverse(list);
                        OrderAdapter orderAdapter = new OrderAdapter(list, OrderDisplayActivity.this);
                        recyclerView.setAdapter(orderAdapter);
                        orderAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}