package com.example.e_comretail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MyAccount extends AppCompatActivity {
    private FirebaseUser user;
    private ImageView UserPic;
    private TextView UserName, Address, MyOrder, MyCart, MyWishList;
    private ImageButton logout;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Account");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        UserPic = findViewById(R.id.user_pic);
        UserName = findViewById(R.id.user_name);
        logout = findViewById(R.id.logout);
        Address = findViewById(R.id.tv_my_addresses);
        MyOrder = findViewById(R.id.tv_orders);
        MyCart = findViewById(R.id.tv_cart);
        MyWishList = findViewById(R.id.tv_wishlist);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        googleSignInClient = GoogleSignIn.getClient((this), GoogleSignInOptions.DEFAULT_SIGN_IN);
        assert user != null;
        String userName = user.getDisplayName();
        UserName.setText(userName);
        Glide.with(this)
                .load(user.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(UserPic);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        (MyAccount.this));
                builder.setMessage("Are you sure, you want to logout from the current session?");
                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                builder.setPositiveButton("LOGOUT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                logout();
                            }
                        });
                builder.show();
            }
        });
        Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, ViewAddress.class);
                startActivity(intent);
            }
        });
        MyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, OrderDisplayActivity.class);
                startActivity(intent);
            }
        });
        MyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, CartActivity.class);
                startActivity(intent);
            }
        });
        MyWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, WishlistActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logout() {
        firebaseAuth.signOut();
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener((this),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Google Sign In failed, update UI appropriately
                        restart();
                        Toast.makeText(MyAccount.this, "Signed out of google", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void restart() {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}