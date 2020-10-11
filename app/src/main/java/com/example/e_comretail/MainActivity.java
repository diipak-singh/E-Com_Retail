package com.example.e_comretail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FirebaseUser user;
    private TextView navUsername;
    private ImageView navUserpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);

        user = FirebaseAuth.getInstance().getCurrentUser();

        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.user_name);
        navUserpic = headerView.findViewById(R.id.profile_Image);
        navUsername.setText(R.string.app_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        checkLogin();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_category:
                Intent intent = new Intent(MainActivity.this, AllCategory.class);
                startActivity(intent);
                break;
            case R.id.nav_offers:
                Intent intent2 = new Intent(MainActivity.this, OfferActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_my_order:
                Toast.makeText(this, "My Orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_wishlist:
                Toast.makeText(this, "Wishlist", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_account:
                Intent intent1 = new Intent(MainActivity.this, MyAccount.class);
                startActivity(intent1);
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLogin();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkLogin();
    }

    public void checkLogin() {
        if (user != null) {
            String userName= user.getDisplayName();
            String lastName = "";
            String firstName= "";
            if(userName.split("\\w+").length>1){

                lastName = userName.substring(userName.lastIndexOf(" ")+1);
                firstName = userName.substring(0, userName.lastIndexOf(' '));
            }
            else{
                firstName = userName;
            }

            navUsername.setText("Hello, "+firstName);
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(navUserpic);
        } else {
            navUsername.setText(R.string.app_name);
        }
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}