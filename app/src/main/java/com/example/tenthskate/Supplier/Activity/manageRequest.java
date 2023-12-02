package com.example.tenthskate.Supplier.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tenthskate.Clients.Activities.LoginActivity;
import com.example.tenthskate.Clients.Activities.Singleton;
import com.example.tenthskate.R;
import com.example.tenthskate.Supplier.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class manageRequest extends AppCompatActivity {

    private ImageView manageProductImage;
    private EditText manageProductName, manageProductDescription, manageProductPrice,price;
    private TextView manageProductCategory, txtProductInfo;
    private ProgressDialog loadingBar;
    private String productID, retProductCategory,useronsession,id;

    private Button updateButton;
    private DatabaseReference productsRef, categoryRef,inventoryToConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_request);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        useronsession = getIntent().getStringExtra("username");

        inventoryToConfirm= FirebaseDatabase.getInstance().getReference().child("all_confirmed_supplies");

        productsRef = FirebaseDatabase.getInstance().getReference().child("allRequests").child(Prevalent.currentOnlineSupplier.getName());
        categoryRef = FirebaseDatabase.getInstance().getReference("SupplierCategory").child(Prevalent.currentOnlineSupplier.getName());
        productID = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("retProductID")).toString();

        id = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("ID")).toString();

        loadingBar=new ProgressDialog(manageRequest.this);

        price =  findViewById(R.id.rice);
        manageProductName = (EditText) findViewById(R.id.manageProductName);
       //manageProductCode = (EditText) findViewById(R.id.manageProductCode);
        manageProductDescription = (EditText) findViewById(R.id.manageProductDescription);
        manageProductPrice = (EditText) findViewById(R.id.manageProductPrice);
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
                String retProductPrice = Objects.requireNonNull(snapshot.child("productPrice").getValue()).toString();
            //    String retProductCuttedPrice = Objects.requireNonNull(snapshot.child("productCuttedPrice").getValue()).toString();
                String retProductID = Objects.requireNonNull(snapshot.child("productID").getValue()).toString();

                manageProductName.setText(retProductName);
              //  manageProductCode.setText(retProductCode);
                manageProductDescription.setText(retProductDescription);
                manageProductPrice.setText(retProductPrice);
              //  manageProductCuttedPrice.setText(retProductCuttedPrice);
                manageProductCategory.setText(retProductCategory);
                txtProductInfo.setText(retProductID);
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
        if (manageProductPrice.getText().toString().isEmpty()){
            manageProductPrice.setError("Product pcks required");
        }
//        if (manageProductCuttedPrice.getText().toString().isEmpty()){
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
           productsRef.child(productID).child("status").setValue("inveApproved");

         //   productsRef.child(productID).child("status").setValue("Approved");
           productsRef.child(productID).child("Supplier").setValue(Prevalent.currentOnlineSupplier.getName());
            productsRef.child(productID).child("price").setValue(price.getText().toString());

            //String uploadID= tofinance.push().getKey();

            inventoryToConfirm.child(productID).child("Supplier").setValue(Prevalent.currentOnlineSupplier.getName());
            inventoryToConfirm.child(productID).child("status").setValue("inveApproved");
            inventoryToConfirm.child(productID).child("price").setValue(price.getText().toString());
            inventoryToConfirm.child(productID).child("productName").setValue(manageProductName.getText().toString());
            inventoryToConfirm.child(productID).child("productPrice").setValue(manageProductPrice.getText().toString()); //quantity
            inventoryToConfirm.child(productID).child("productID").setValue(productID);
            inventoryToConfirm.child(productID).child("productCategory").setValue(manageProductCategory.getText().toString());
            inventoryToConfirm.child(productID).child("ID").setValue(id);
            inventoryToConfirm.child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());

            admin(price.getText().toString(), manageProductDescription.getText().toString());
            manageProductName.setText("");
           // manageProductCode.setText("");
            manageProductDescription.setText("");
            manageProductPrice.setText("");
           // manageProductCuttedPrice.setText("");
            manageProductCategory.setText("");
            txtProductInfo.setText("");
            updateButton.setBackgroundColor(Color.GREEN);
            updateButton.setText("Price Set");
            loadingBar.dismiss();
            sendUserToMainActivity();
        }
    }

    private void admin(final String price, final String desc) {

        final ProgressDialog progressDialog = new ProgressDialog(manageRequest.this);
        progressDialog.setTitle("Approving");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, LoginActivity.
                ip+"supply-orders.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("successfully")) {
                    Toast.makeText(manageRequest.this, "approved", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(manageRequest1.this, LoginActivity.class));
                    progressDialog.dismiss();
                    // finish();
                } else {
                    Toast.makeText(manageRequest.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(manageRequest.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("price", price);
                param.put("description",desc);
               // param.put("name", name);
               // param.put("code", code);
                param.put("status", "1");

                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton.getmInstance(manageRequest.this).addToRequestQueue(request);


    }

    private void sendUserToMainActivity() {
        //productsRef.child(productID).removeValue();
        Intent mainIntent=new Intent(manageRequest.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}