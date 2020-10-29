package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Adapter.HorizontalCategoryAdapter;
import com.example.e_comretail.Details.AllCategoryDetails;
import com.example.e_comretail.Details.ItemDetails;
import com.example.e_comretail.Offer.OfferActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private EditText search;
    private RecyclerView recyclerView;
    private ArrayList<AllCategoryDetails> categoryList;
    private ArrayList<ItemDetails> recentList;
    private DatabaseReference categoryRef, recentRef;

    private ClickableViewPager viewPager;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    private CardView Male, Female, Kids;
    private Button ViewAll1, ViewAll2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRef = FirebaseDatabase.getInstance().getReference().child("Category/");
        categoryRef.keepSynced(true);

        recentRef = FirebaseDatabase.getInstance().getReference().child("All Items/");

        viewPager = view.findViewById(R.id.view_pager_recently_added);
        Male = view.findViewById(R.id.male);
        Female = view.findViewById(R.id.female);
        Kids = view.findViewById(R.id.kids);
        ViewAll1 = view.findViewById(R.id.view_all1);
        ViewAll2 = view.findViewById(R.id.button);

        recyclerView = view.findViewById(R.id.recycler__horizontal_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        getCategoryData();
        getRecentData();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), SubCategory.class);
                intent.putExtra("SubCategory", categoryList.get(position).getCategoryname());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        viewPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String image1 = recentList.get(position).getImageUrl();
                String image2 = recentList.get(position).getImageUrl2();
                String image3 = recentList.get(position).getImageUrl3();
                String image4 = recentList.get(position).getImageUrl4();
                String image5 = recentList.get(position).getImageUrl5();
                String image6 = recentList.get(position).getImageUrl6();
                ArrayList<String> imageList = new ArrayList<>();
                imageList.add(image1);
                imageList.add(image2);
                imageList.add(image3);
                imageList.add(image4);
                imageList.add(image5);
                imageList.add(image6);

                String ItemName = recentList.get(position).getItemName();
                String ItemPrice = recentList.get(position).getItemPrice();
                String Gst = recentList.get(position).getGstrate();
                String Desc = recentList.get(position).getItemDesc();
                String IsCertified = recentList.get(position).getIsCertified();
                String Measurement = recentList.get(position).getMeasurement();
                String Discount = recentList.get(position).getDiscount();
                String Stock = recentList.get(position).getStock();
                String ItemCode = recentList.get(position).getItemcode();

                Intent intent = new Intent(getActivity(), MainItemDsiplay.class);
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("ItemPrice", ItemPrice);
                intent.putExtra("Gst", Gst);
                intent.putExtra("Desc", Desc);
                intent.putExtra("IsCertified", IsCertified);
                intent.putExtra("Measurement", Measurement);
                intent.putExtra("Discount", Discount);
                intent.putExtra("Stock", Stock);
                intent.putExtra("ItemCode", ItemCode);
                intent.putExtra("HsnCode", recentList.get(position).getHsncode());
                intent.putStringArrayListExtra("images", imageList);
                startActivity(intent);
            }
        });

        Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenderActivity.class);
                intent.putExtra("Gender", "Male");
                startActivity(intent);
            }
        });
        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenderActivity.class);
                intent.putExtra("Gender", "Female");
                startActivity(intent);
            }
        });
        Kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenderActivity.class);
                intent.putExtra("Gender", "Kid");
                startActivity(intent);
            }
        });
        ViewAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubCategory.class);
                intent.putExtra("SubCategory", "Fashion");
                startActivity(intent);
            }
        });
        ViewAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OfferActivity.class);
                startActivity(intent);
            }
        });

        search = view.findViewById(R.id.input_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void getCategoryData() {
        if (categoryRef != null) {
            categoryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        categoryList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            categoryList.add(ds.getValue(AllCategoryDetails.class));
                        }
                        HorizontalCategoryAdapter horizontalCategoryAdapter = new HorizontalCategoryAdapter(categoryList, getContext());
                        recyclerView.setAdapter(horizontalCategoryAdapter);
                        horizontalCategoryAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getRecentData() {
        if (recentRef != null) {
            recentRef.limitToFirst(6).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        recentList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            recentList.add(ds.getValue(ItemDetails.class));
                        }
                        ViewPagerAdapter adapter = new ViewPagerAdapter(recentList, getActivity());
                        viewPager.setAdapter(adapter);

                        /*After setting the adapter use the timer */
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == recentList.size() - 1) {
                                    currentPage = 0;
                                }
                                viewPager.setCurrentItem(currentPage++, true);
                            }
                        };

                        timer = new Timer(); // This will create a new Thread
                        timer.schedule(new TimerTask() { // task to be scheduled
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}