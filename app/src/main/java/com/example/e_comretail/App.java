package com.example.e_comretail;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        keepSynced();
    }
    public void keepSynced(){
        DatabaseReference ItemsRef = FirebaseDatabase.getInstance().getReference("Items");
        ItemsRef.keepSynced(true);

        DatabaseReference Category = FirebaseDatabase.getInstance().getReference("Category");
        Category.keepSynced(true);

        DatabaseReference subCategory = FirebaseDatabase.getInstance().getReference("Sub Category");
        subCategory.keepSynced(true);

        DatabaseReference allItems = FirebaseDatabase.getInstance().getReference("All Items");
        allItems.keepSynced(true);
    }
}
