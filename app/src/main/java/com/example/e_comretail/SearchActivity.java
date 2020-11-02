package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.ItemDisplayAdapter;
import com.example.e_comretail.Details.ItemDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    private DatabaseReference ref, ref2;
    private RecyclerView recyclerView;
    private ArrayList<ItemDetails> list;
    ArrayList<ItemDetails> myList;
    private SearchView inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ref = FirebaseDatabase.getInstance().getReference().child("All Items");
        recyclerView = findViewById(R.id.recycler_search);
        inputSearch = findViewById(R.id.input_search);
        inputSearch.setIconifiedByDefault(false);
        inputSearch.setFocusable(true);
        inputSearch.setIconified(false);
        inputSearch.clearFocus();
        inputSearch.requestFocusFromTouch();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String image1 = myList.get(position).getImageUrl();
                String image2 = myList.get(position).getImageUrl2();
                String image3 = myList.get(position).getImageUrl3();
                String image4 = myList.get(position).getImageUrl4();
                String image5 = myList.get(position).getImageUrl5();
                String image6 = myList.get(position).getImageUrl6();
                ArrayList<String> imageList = new ArrayList<>();
                imageList.add(image1);
                imageList.add(image2);
                imageList.add(image3);
                imageList.add(image4);
                imageList.add(image5);
                imageList.add(image6);

                String ItemName = myList.get(position).getItemName();
                String ItemPrice = myList.get(position).getItemPrice();
                String Gst = myList.get(position).getGstrate();
                String Desc = myList.get(position).getItemDesc();
                String IsCertified = myList.get(position).getIsCertified();
                String Measurement = myList.get(position).getMeasurement();
                String Discount = myList.get(position).getDiscount();
                String Stock = myList.get(position).getStock();
                String ItemCode = myList.get(position).getItemcode();

                Intent intent = new Intent(SearchActivity.this, MainItemDsiplay.class);
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("ItemPrice", ItemPrice);
                intent.putExtra("Gst", Gst);
                intent.putExtra("Desc", Desc);
                intent.putExtra("IsCertified", IsCertified);
                intent.putExtra("Measurement", Measurement);
                intent.putExtra("Discount", Discount);
                intent.putExtra("Stock", Stock);
                intent.putExtra("ItemCode", ItemCode);
                intent.putExtra("HsnCode", myList.get(position).getHsncode());
                intent.putStringArrayListExtra("images", imageList);
                startActivity(intent);

                //creating recent searches
                ref2 = FirebaseDatabase.getInstance().getReference().child("Recent Search/"
                        + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                String itemId = ref2.push().getKey();
                ItemDetails itemDetails = new ItemDetails(ItemName,
                        list.get(position).getKeywords(),
                        ItemPrice,
                        list.get(position).getItemDesc(),
                        image1,
                        image2,
                        image3,
                        image4,
                        image5,
                        image6,
                        Stock,
                        Discount,
                        list.get(position).getCostprice(),
                        list.get(position).getItemdate(),
                        list.get(position).getItemcode(),
                        list.get(position).getIsCertified(),
                        list.get(position).getHsncode(),
                        list.get(position).getGstrate(),
                        list.get(position).getCategory(),
                        list.get(position).getSub_category(),
                        list.get(position).getGender(),
                        list.get(position).getMrpprice(),
                        list.get(position).getCompanyname(),
                        list.get(position).getMeasurement(),
                        list.get(position).getItemId());
                ref2.child(itemId).setValue(itemDetails);
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
                    list = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        list.add(ds.getValue(ItemDetails.class));
                    }
                    ItemDisplayAdapter itemDisplayAdapter = new ItemDisplayAdapter(list, getApplicationContext());
                    recyclerView.setAdapter(itemDisplayAdapter);
                    recyclerView.setVisibility(View.GONE);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (inputSearch != null) {
            inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
            inputSearch.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }
            });
        }
    }

    private void search(String str) {
        myList = new ArrayList<>();
        for (ItemDetails object : list) {
            if (object.getKeywords().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }
        }
        ItemDisplayAdapter itemDisplayAdapter = new ItemDisplayAdapter(myList, SearchActivity.this);
        recyclerView.setAdapter(itemDisplayAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        if (myList.size() == list.size()) {
            recyclerView.setVisibility(View.GONE);
        }
    }
}