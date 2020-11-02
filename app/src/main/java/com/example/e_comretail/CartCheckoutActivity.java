package com.example.e_comretail;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.e_comretail.Details.AddressDetails;
import com.example.e_comretail.Details.CartDetails;
import com.example.e_comretail.Details.OrderDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CartCheckoutActivity extends AppCompatActivity {
    private ArrayList<AddressDetails> addressList;
    private DatabaseReference ref;
    private TextView Address, Landmark, City, State, Zip, Phone;
    private TextView totalItems, totalPrice, discount, amountPayable, deliveryCharges;
    private CardView addressCard;
    private Button shippingAddress;
    private ProgressBar progressBar;

    private ImageView paymentModeGif;
    private RadioButton payViaUPI, payViaCOD;
    private Button place_order;

    private final String upiID = "9616727773@upi"; //ToDO: UPI id.
    private final String note = "Paying to E-Com_Retail";
    private final String name = "E-Com_Retail";
    final int UPI_PAYMENT = 0;

    private ArrayList<CartDetails> cartList;
    private int totalPayableAmount = 0;
    int deliveryCharge = 0;
    private final String INR = "₹";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Summary");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ref = FirebaseDatabase.getInstance().getReference().child("Address/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        initAddressList();

        cartList = (ArrayList<CartDetails>) getIntent().getSerializableExtra("cartDetails");

        payViaUPI = findViewById(R.id.radio_upi_payment);
        payViaCOD = findViewById(R.id.radio_cash_on_delivery);
        place_order = findViewById(R.id.button_place_order);
        place_order.setVisibility(View.INVISIBLE);

        Address = findViewById(R.id.address);
        Landmark = findViewById(R.id.landmark);
        City = findViewById(R.id.city);
        State = findViewById(R.id.state);
        Zip = findViewById(R.id.zip);
        Phone = findViewById(R.id.phone);
        addressCard = findViewById(R.id.cardView3);

        totalItems = findViewById(R.id.total_items);
        totalPrice = findViewById(R.id.item_price);
        discount = findViewById(R.id.cart_total_show);
        amountPayable = findViewById(R.id.amount_payable_show);
        deliveryCharges = findViewById(R.id.delivery_charges_show);

        paymentModeGif = findViewById(R.id.payment_gif);
        Glide.with(this).asGif().load(R.drawable.payment).into(paymentModeGif);

        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.GONE);

        calculateBillingDetails();

        addressCard.setVisibility(View.GONE);
        shippingAddress = findViewById(R.id.shipping_address);
        shippingAddress.setText("Change Shipping Address");
        shippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartCheckoutActivity.this, AddNewAddress.class);
                startActivity(intent);
            }
        });

        payViaCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payViaCOD.setChecked(true);
                payViaUPI.setChecked(false);
            }
        });
        payViaUPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payViaCOD.setChecked(false);
                payViaUPI.setChecked(true);
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payViaCOD.isChecked()) {
                    makeOrderCOD();
                } else if (payViaUPI.isChecked()) {
                    payUsingUPI(String.valueOf(totalPayableAmount),
                            upiID,
                            name,
                            note);
                } else
                    Toast.makeText(getApplicationContext(), "Please select a payment method.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void initAddressList() {
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        addressList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            addressList.add(ds.getValue(AddressDetails.class));
                        }
                        if (addressList.size() > 0) {
                            addressCard.setVisibility(View.VISIBLE);
                            Address.setText(addressList.get(0).getAddress());
                            Landmark.setText(addressList.get(0).getLandmark());
                            City.setText(addressList.get(0).getCity());
                            State.setText(addressList.get(0).getState());
                            Zip.setText(addressList.get(0).getZip());
                            Phone.setText(addressList.get(0).getPhone());
                            shippingAddress.setVisibility(View.GONE);
                            place_order.setVisibility(View.VISIBLE);
                        } else {
                            addressCard.setVisibility(View.GONE);
                            shippingAddress.setVisibility(View.VISIBLE);
                            place_order.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void calculateBillingDetails() {
        totalItems.setText("Total items: " + cartList.size());

        for (CartDetails cd : cartList) {
            totalPayableAmount += Integer.parseInt(cd.getAmountPayable());
        }
        totalPrice.setText(String.valueOf(INR + totalPayableAmount));
        if (totalPayableAmount < 500) {
            deliveryCharge = 30;
        }
        deliveryCharges.setText(String.valueOf(deliveryCharge));
        amountPayable.setText(String.valueOf(totalPayableAmount + deliveryCharge));
    }

    private void payUsingUPI(String amount, String upiId, String name, String note) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(CartCheckoutActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(CartCheckoutActivity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(CartCheckoutActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                makeOrderUPI();
                //Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(CartCheckoutActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartCheckoutActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CartCheckoutActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(CartCheckoutActivity context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void makeOrderUPI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("OrderDetails");

        final String paidVia = "Paid via UPI.";
        final String OrderStatus = "Order Placed";
        String totalAmount = amountPayable.getText().toString();
        String orderDate = getCurrentDate();
        String orderTime = getCurrentTime();
        String userName = user != null ? user.getDisplayName() : null;
        String userMail = user != null ? user.getEmail() : null;
        String userID = user != null ? user.getUid() : null;
        String userPhone = Phone.getText().toString().trim();
        String userAddress = Address.getText().toString().trim();
        String userLandMark = Landmark.getText().toString().trim();
        String userCity = City.getText().toString().trim();
        String userState = State.getText().toString().trim();
        String userZip = Zip.getText().toString().trim();
        String EstimatedTime = "2 Days";

        for (CartDetails cd : cartList) {
            String orderId = databaseReference.push().getKey();

            OrderDetails od = new OrderDetails(
                    cd.getItemName(),
                    cd.getItemCode(),
                    cd.getMeasurement(),
                    cd.getItemImage(),
                    OrderStatus,
                    "",
                    EstimatedTime,
                    String.valueOf(totalPayableAmount),
                    cd.getHsnCode(),//ToDo Update this and GST rate
                    cd.getGstRate(),
                    cd.getItemQuantity(),
                    orderTime,
                    orderDate,
                    userName,
                    userMail,
                    userPhone,
                    userAddress,
                    userLandMark,
                    userCity,
                    userState,
                    userZip,
                    userID,
                    paidVia,
                    orderId
            );
            databaseReference.child(orderId).setValue(od).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        Toast.makeText(getApplicationContext(), "Placing Orders...", Toast.LENGTH_SHORT).show();
                        //ToDo: show dialog here instead of toast.
                        Toast.makeText(getApplicationContext(), "Placing Orders...", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        ShowAfterOrder();
    }

    private void makeOrderCOD() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("OrderDetails");

        final String paidVia = "Paid via COD.";
        final String OrderStatus = "Order Placed";
        String totalAmount = amountPayable.getText().toString();
        String orderDate = getCurrentDate();
        String orderTime = getCurrentTime();
        String userName = user != null ? user.getDisplayName() : null;
        String userMail = user != null ? user.getEmail() : null;
        String userID = user != null ? user.getUid() : null;
        String userPhone = Phone.getText().toString().trim();
        String userAddress = Address.getText().toString().trim();
        String userLandMark = Landmark.getText().toString().trim();
        String userCity = City.getText().toString().trim();
        String userState = State.getText().toString().trim();
        String userZip = Zip.getText().toString().trim();
        String EstimatedTime = "2 Days";

        for (CartDetails cd : cartList) {
            String orderId = databaseReference.push().getKey();

            OrderDetails od = new OrderDetails(
                    cd.getItemName(),
                    cd.getItemCode(),
                    cd.getMeasurement(),
                    cd.getItemImage(),
                    OrderStatus,
                    "",
                    EstimatedTime,
                    String.valueOf(totalPayableAmount),
                    cd.getHsnCode(),//ToDo Update this
                    cd.getGstRate(),
                    cd.getItemQuantity(),
                    orderTime,
                    orderDate,
                    userName,
                    userMail,
                    userPhone,
                    userAddress,
                    userLandMark,
                    userCity,
                    userState,
                    userZip,
                    userID,
                    paidVia,
                    orderId
            );
            databaseReference.child(orderId).setValue(od).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        //ToDo: show dialog here instead of toast.
                        Toast.makeText(getApplicationContext(), "Placing Orders...", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        ShowAfterOrder();
    }

    public void ShowAfterOrder(){
        progressBar.setVisibility(View.INVISIBLE);
        DeleteCart();
        Intent intent = new Intent(CartCheckoutActivity.this, AfterOrderActivity.class);
        startActivity(intent);
        intent.putExtra("Title", "Order Placed!");
        intent.putExtra("Desc", "Thanks you for shopping with us! \n your order was placed successfully.");
        finish();
    }
    public void DeleteCart(){
        DatabaseReference CartRef = FirebaseDatabase.getInstance().getReference().child("Cart Details/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        CartRef.removeValue();
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
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strTime = mdformat.format(calendar.getTime());
        return strTime;
    }

}