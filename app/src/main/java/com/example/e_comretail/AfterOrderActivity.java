package com.example.e_comretail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class AfterOrderActivity extends AppCompatActivity {
    private Button goBack;
    private ImageView payment_successful_image;
    private TextView title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thank You");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String Title = intent.getStringExtra("Title");
        String Desc = intent.getStringExtra("Desc");

        goBack = findViewById(R.id.go_back);
        title  =findViewById(R.id.success);
        desc = findViewById(R.id.textView4);

        title.setText(Title);
        desc.setText(Desc);

        payment_successful_image = findViewById(R.id.payment_successful_gif);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterOrderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Glide.with(this).asGif().load(R.drawable.payment_successful).into(payment_successful_image);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}