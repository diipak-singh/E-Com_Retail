package com.example.e_comretail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.e_comretail.Details.RatingDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class RatingActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference ref;
    private RatingBar ratingBar;
    private EditText review;
    private Button Submit;
    String ItemCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Intent intent = getIntent();
        ItemCode = intent.getStringExtra("ItemCode");
        ratingBar = findViewById(R.id.ratingBar);
        review = findViewById(R.id.review);
        Submit = findViewById(R.id.submit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Rating");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("Review Details/");
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit.setVisibility(View.GONE);
                closekeyboard();
                String strRating = String.valueOf(ratingBar.getRating());
                if (review.getText().toString().trim().isEmpty() || strRating.isEmpty()) {
                    Toast.makeText(RatingActivity.this, "Enter Values first", Toast.LENGTH_SHORT).show();
                } else {
                    UploadRating();
                }
                finish();
            }
        });
    }

    public void UploadRating() {
        String strRating = String.valueOf(ratingBar.getRating());
        String strReview = review.getText().toString().trim();
        String userId = user.getUid();
        String itemCode = ItemCode;
        String userName = user.getDisplayName();
        String itemId = ref.push().getKey();
        RatingDetails ratingDetails = new RatingDetails(itemCode, userId, userName, getCurrentDate(), strRating, strReview, itemId);
        assert itemId != null;
        ref.child(itemId).setValue(ratingDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RatingActivity.this, "Rating added successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RatingActivity.this, "Unable to save!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closekeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy ");
        String strDate = mdformat.format(calendar.getTime());
        //display(strDate);
        return strDate;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}