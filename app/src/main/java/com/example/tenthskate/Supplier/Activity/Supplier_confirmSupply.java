package com.example.tenthskate.Supplier.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tenthskate.Clients.Activities.FeedbackActivity;
import com.example.tenthskate.R;
import com.example.tenthskate.Supplier.Prevalent.Prevalent;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Supplier_confirmSupply extends AppCompatActivity {

    private ImageView manageProductImage;
    private EditText manageProductName, manageProductDescription,manageProductpaycode, manageProductquantity,price;
    private TextView manageProductCategory, txtProductInfo;
    private ProgressDialog loadingBar;
    private String productID, retProductCategory,useronsession,id;
    private Button updateButton;

    private DatabaseReference productsRef, categoryRef,toinventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplier_confirmpaid_supply);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        useronsession = getIntent().getStringExtra("username");

        toinventory = FirebaseDatabase.getInstance().getReference().child("all_paid_supplies").child(Prevalent.currentOnlineSupplier.getName());

        productsRef = FirebaseDatabase.getInstance().getReference().child("all_confirmed_supplies").child(Prevalent.currentOnlineSupplier.getName());
        //categoryRef = FirebaseDatabase.getInstance().getReference("SupplierCategory");
        productID = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("retProductID")).toString();

        id = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("ID")).toString();

        loadingBar=new ProgressDialog(Supplier_confirmSupply.this);

        price =  findViewById(R.id.rice);
        manageProductName = (EditText) findViewById(R.id.manageProductName);
        manageProductpaycode = (EditText) findViewById(R.id.mpesacode);
        manageProductDescription = (EditText) findViewById(R.id.manageProductDescription);
        manageProductquantity = (EditText) findViewById(R.id.manageProductquantity);
        //manageProductCuttedPrice = (EditText) findViewById(R.id.manageProductCuttedPrice);
        manageProductCategory = (TextView) findViewById(R.id.manageProductCategory);
        updateButton = (Button) findViewById(R.id.updateButton);
        txtProductInfo = (TextView) findViewById(R.id.txtProductInfo);

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String retProductName = Objects.requireNonNull(snapshot.child("productName").getValue()).toString();
                retProductCategory = Objects.requireNonNull(snapshot.child("productCategory").getValue()).toString();
                //String retProductCode = Objects.requireNonNull(snapshot.child("productCode").getValue()).toString();
                String retProductDescription = Objects.requireNonNull(snapshot.child("productDescription").getValue()).toString();
                //   String retProductImageUrl = Objects.requireNonNull(snapshot.child("productImageUrl").getValue()).toString();
                String retProductquantity = Objects.requireNonNull(snapshot.child("quantity").getValue()).toString();
                String retProductPrice = Objects.requireNonNull(snapshot.child("price").getValue()).toString();
                String retProductID = Objects.requireNonNull(snapshot.child("productID").getValue()).toString();
                String retProductpaycode = Objects.requireNonNull(snapshot.child("mpesa").getValue()).toString();

                manageProductName.setText(retProductName);
                 manageProductpaycode.setText(retProductpaycode);
                manageProductDescription.setText(retProductDescription);
                manageProductquantity.setText(retProductquantity);
                //  manageProductCuttedPrice.setText(retProductCuttedPrice);
                manageProductCategory.setText(retProductCategory);
                txtProductInfo.setText(retProductID);
                price.setText(retProductPrice);
//                Picasso.get()
//                        .load(retProductImageUrl)
//                        .placeholder(R.drawable.image_preview)
//                        .fit()
//                        .centerCrop()
//                        .into(manageProductImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInformation();
            }
        });
    }

    private void updateInformation() {
        if (manageProductName.getText().toString().isEmpty()){
            manageProductName.setError("Product name required");
        }
        if (price.getText().toString().isEmpty()){
            price.setError("price  required");
        }
        if (manageProductDescription.getText().toString().isEmpty()){
            manageProductDescription.setError("Product description required");
        }
        if (manageProductquantity.getText().toString().isEmpty()){
            manageProductquantity.setError("Product quantity required");
        }
//        if (manaP.getText().toString().isEmpty()){
//            manageProductCuttedPrice.setError("Product cutted price required");
//        }
        else {
            loadingBar.setTitle("Updating");
            loadingBar.setMessage("Please wait, your information is updating...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

//            categoryRef.child(retProductCategory).child(productID).child("productName").setValue(manageProductName.getText().toString());
//          //  categoryRef.child(retProductCategory).child(productID).child("productCode").setValue(manageProductCode.getText().toString());
//            categoryRef.child(retProductCategory).child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());
//            categoryRef.child(retProductCategory).child(productID).child("productPrice").setValue(manageProductPrice.getText().toString());
//            //categoryRef.child(retProductCategory).child(productID).child("productCuttedPrice").setValue(manageProductCuttedPrice.getText().toString());
//
//            productsRef.child(productID).child("productName").setValue(manageProductName.getText().toString());
//           // productsRef.child(productID).child("productCode").setValue(manageProductCode.getText().toString());
//            productsRef.child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());
//            productsRef.child(productID).child("productPrice").setValue(manageProductPrice.getText().toString());
            productsRef.child(productID).child("status").setValue("confirmed Payment");
            productsRef.child(productID).child("Supplier").setValue(Prevalent.currentOnlineSupplier.getName());
            productsRef.child(productID).child("price").setValue(price.getText().toString());

            //String uploadID= tofinance.push().getKey();

            toinventory.child(productID).child("Supplier").setValue(Prevalent.currentOnlineSupplier.getName());
            toinventory.child(productID).child("status").setValue("confirmed Payment");
            toinventory.child(productID).child("mpesa").setValue(manageProductpaycode.getText().toString());
            toinventory.child(productID).child("price").setValue(price.getText().toString());
            toinventory.child(productID).child("productName").setValue(manageProductName.getText().toString());
            toinventory.child(productID).child("quantity").setValue(manageProductquantity.getText().toString()); //quantity
            toinventory.child(productID).child("productID").setValue(productID);
            toinventory.child(productID).child("productCategory").setValue(manageProductCategory.getText().toString());
            toinventory.child(productID).child("ID").setValue(id);
            toinventory.child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());


            manageProductName.setText("");
            // manageProductCode.setText("");
            manageProductDescription.setText("");
            manageProductquantity.setText("");
            // manageProductCuttedPrice.setText("");
            manageProductCategory.setText("Payment Approved");
            txtProductInfo.setBackgroundColor(Color.GREEN);
            updateButton.setBackgroundColor(Color.RED);
            loadingBar.dismiss();
            sendUserToMainActivity();
        }
    }

    private void sendUserToMainActivity() {
        //productsRef.child(productID).removeValue();
        View View = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(View, "Approved \t you can notify inventory", 10000)
                .setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mainIntent=new Intent(Supplier_confirmSupply.this, FeedbackActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                });
        snackbar.getView().setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        snackbar.show();
        Intent mainIntent=new Intent(Supplier_confirmSupply.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}