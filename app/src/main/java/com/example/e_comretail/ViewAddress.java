package com.example.e_comretail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.AddressAdapter;
import com.example.e_comretail.Details.AddressDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ViewAddress extends AppCompatActivity {
    private Button AddAddress;
    private DatabaseReference databaseReference;
    private ArrayList<AddressDetails> list;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_address);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("View Address");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Address/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        recyclerView = findViewById(R.id.recycler_address);
        AddAddress = findViewById(R.id.add_address);
        AddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAddress.this, AddNewAddress.class);
                startActivity(intent);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewAddress.this);
                String[] options = {"Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                databaseReference.child(list.get(position).getItemId()).removeValue();
                                Toast.makeText(ViewAddress.this,
                                        "Address Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                break;
//                            default:
//
//                                break;

                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(AddressDetails.class));
                        }
                        int listSize = list.size();
                        if (listSize > 0) {
                            AddAddress.setVisibility(View.GONE);

                        }
                        AddressAdapter addressAdapter = new AddressAdapter(list, ViewAddress.this);
                        recyclerView.setAdapter(addressAdapter);
                        addressAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ViewAddress.this, "Loading Failed, Check your Internet Connection", Toast.LENGTH_SHORT).show();
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