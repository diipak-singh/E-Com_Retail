package com.example.e_comretail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.e_comretail.Details.AddressDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddNewAddress extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private EditText Email, Phone, Address, Landmark, City, State, Zip;
    private Button save;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        layout = findViewById(R.id.layout_add_new_address);
        save = findViewById(R.id.save);
        Email = findViewById(R.id.user_mail);
        Phone = findViewById(R.id.user_phone);
        Address = findViewById(R.id.user_address);
        Landmark = findViewById(R.id.user_landmark);
        City = findViewById(R.id.user_city);
        State = findViewById(R.id.user_state);
        Zip = findViewById(R.id.user_zip);


        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Address");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        Email.setText(user.getEmail());
        City.setText("Varanasi");
        State.setText("Uttar Pradesh");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = Phone.getText().toString();
                String address = Address.getText().toString();
                String landmark = Landmark.getText().toString();
                if (phone.equals("") || address.equals("") || landmark.equals("")) {
                    Toast.makeText(AddNewAddress.this, "Enter All Details Before saving", Toast.LENGTH_SHORT).show();
                } else {
                    UploadAddress();
                    save.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void UploadAddress() {
        String emailId = Email.getText().toString();
        String phone = Phone.getText().toString();
        String address = Address.getText().toString();
        String landmark = Landmark.getText().toString();
        String city = City.getText().toString();
        String state = State.getText().toString();
        String zip = Zip.getText().toString();
        String userName = user.getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Address/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        String itemId = databaseReference.push().getKey();
        AddressDetails addressDetails = new AddressDetails(userName, emailId, phone, address, landmark, city, state, zip, itemId);
        assert itemId != null;
        databaseReference.child(itemId).setValue(addressDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(AddNewAddress.this,
                            "Address Saved Successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddNewAddress.this,
                            "Unable to Save, Try Again Later",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}