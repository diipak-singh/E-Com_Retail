package com.example.e_comretail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_comretail.Details.ChatDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    EditText editText;
    RelativeLayout addBtn;
    DatabaseReference ref;
    private FirebaseUser user;
    FirebaseRecyclerAdapter<ChatDetails, chat_rec> adapter;
    private ImageButton call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String strMessage = intent.getStringExtra("message");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chat Support");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(strMessage);
        addBtn = (RelativeLayout) findViewById(R.id.addBtn);
        call = findViewById(R.id.call);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("Chat Details/").child(user.getUid());
        ref.keepSynced(true);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8192883912"));
                startActivity(intent);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString().trim();

                if (!message.equals("")) {

                    ChatDetails chatDetails = new ChatDetails(user.getDisplayName(), user.getEmail(), user.getUid(), message, "", getCurrentDate(), getCurrentTime());
                    ref.push().setValue(chatDetails);

                    editText.setText("");

                }
            }
        });

        adapter = new FirebaseRecyclerAdapter<ChatDetails, chat_rec>(ChatDetails.class, R.layout.msglist, chat_rec.class, ref) {
            @Override
            protected void populateViewHolder(chat_rec viewHolder, ChatDetails model, int position) {

                if (model.getAdminMessage().equals("")) {
                    viewHolder.rightText.setText(model.getUserMessage());
                    viewHolder.rightDate.setText(model.getMessageDate() + " • " + model.getMessageTime());

                    viewHolder.rightLayout.setVisibility(View.VISIBLE);
                    viewHolder.leftLayout.setVisibility(View.GONE);
                } else {
                    viewHolder.leftText.setText(model.getAdminMessage());
                    viewHolder.leftDate.setText(model.getMessageDate() + " • " + model.getMessageTime());

                    viewHolder.rightLayout.setVisibility(View.GONE);
                    viewHolder.leftLayout.setVisibility(View.VISIBLE);
                }
            }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int msgCount = adapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                        (positionStart >= (msgCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);

                }

            }
        });

        recyclerView.setAdapter(adapter);

    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy ");
        String strDate = mdformat.format(calendar.getTime());
        //display(strDate);
        return strDate;
    }

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("hh:mm aa");
        String strTime = mdformat.format(calendar.getTime());
        return strTime;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}