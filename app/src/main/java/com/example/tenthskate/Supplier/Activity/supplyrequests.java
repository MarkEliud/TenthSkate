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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tenthskate.Clients.Activities.FeedbackActivity;
import com.example.tenthskate.Clients.Activities.LoginActivity;
import com.example.tenthskate.Clients.Activities.Singleton;
import com.example.tenthskate.R;
import com.example.tenthskate.Supplier.Prevalent.Prevalent;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class supplyrequests extends AppCompatActivity {

    private ImageView manageProductImage;
    private EditText manageProductName, manageProductDescription, manageProductPrice,price;
    private TextView manageProductCategory, txtProductInfo;
    private ProgressDialog loadingBar;
    private String productID, retProductCategory,useronsession,id,retProductquatty;

    private String  retProductDescription,retProductPrice,retProductID,retProductName,iD;

    private Button updateButton;

    private DatabaseReference productsRef, categoryRef,toinventoryUpdatestock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplynow_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        toinventoryUpdatestock = FirebaseDatabase.getInstance().getReference().child("all_supplied_supplies");
        productsRef = FirebaseDatabase.getInstance().getReference().child("allRequests").child(Prevalent.currentOnlineSupplier.getName());
        //categoryRef = FirebaseDatabase.getInstance().getReference("SupplierCategory");
        productID = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("retProductID")).toString();
        id = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("ID")).toString();
        loadingBar=new ProgressDialog(supplyrequests.this);
        //price =  findViewById(R.id.rice);
        manageProductName = (EditText) findViewById(R.id.manageProductName);
        //manageProductCode = (EditText) findViewById(R.id.manageProductCode);
        manageProductDescription = (EditText) findViewById(R.id.manageProductDescription);
        manageProductPrice = (EditText) findViewById(R.id.manageProductquantity);
        //manageProductCuttedPrice = (EditText) findViewById(R.id.manageProductCuttedPrice);
        manageProductCategory = (TextView) findViewById(R.id.manageProductCategory);
        updateButton = (Button) findViewById(R.id.updateButton);
        txtProductInfo = (TextView) findViewById(R.id.txtProductInfo);

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                     retProductName = Objects.requireNonNull(snapshot.child("productName").getValue()).toString();
                    retProductCategory = Objects.requireNonNull(snapshot.child("productCategory").getValue()).toString();
                    //String retmpesaCode = Objects.requireNonNull(snapshot.child("mpesa").getValue()).toString();
                     retProductDescription = Objects.requireNonNull(snapshot.child("productDescription").getValue()).toString();
                    //String retProductImageUrl = Objects.requireNonNull(snapshot.child("productImageUrl").getValue()).toString();
                     retProductPrice = Objects.requireNonNull(snapshot.child("productPrice").getValue()).toString();
                     retProductquatty = Objects.requireNonNull(snapshot.child("quantity").getValue()).toString();
                     retProductID = Objects.requireNonNull(snapshot.child("productID").getValue()).toString();

                    manageProductName.setText(retProductName);
                     //manageProductCode.setText(retProductCode);
                    manageProductDescription.setText(retProductDescription);
                    manageProductPrice.setText(retProductquatty);
                    //manageProductCuttedPrice.setText(retProductCuttedPrice);
                    manageProductCategory.setText(retProductCategory);
                    txtProductInfo.setText(retProductID);
                    /*Picasso.get()
                            .load(retProductImageUrl)
                            .placeholder(R.drawable.image_preview)
                            .fit()
                            .centerCrop()
                            .into(manageProductImage);*/
                } else {
                    Toast.makeText(supplyrequests.this, "No records", Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInformation();
            }
        });
    }private void updateInformation() {

        admin(retProductCategory,retProductDescription,retProductName,retProductPrice,retProductquatty,"1");
        if (manageProductName.getText().toString().isEmpty()){
            manageProductName.setError("Product name required");
        }
        if (manageProductDescription.getText().toString().isEmpty()){
            manageProductDescription.setError("Product description required");
        }
        if (manageProductPrice.getText().toString().isEmpty()){
            manageProductPrice.setError("Product quantity required");
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
            productsRef.child(productID).child("productName").setValue(manageProductName.getText().toString());
//           // productsRef.child(productID).child("productCode").setValue(manageProductCode.getText().toString());
            productsRef.child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());
//            productsRef.child(productID).child("productPrice").setValue(manageProductPrice.getText().toString());
            productsRef.child(productID).child("status").setValue("Delivered");
            productsRef.child(productID).child("Supplier").setValue(Prevalent.currentOnlineSupplier.getName());
            //productsRef.child(productID).child("price").setValue(price.getText().toString());



//            toinventoryUpdatestock.child(productID).child("Supplier").setValue(Prevalent.currentOnlineSupplier.getName());
//            toinventoryUpdatestock.child(productID).child("status").setValue("Delivered");
//            //toinventoryUpdatestock.child(productID).child("price").setValue(price.getText().toString());
//            toinventoryUpdatestock.child(productID).child("productName").setValue(manageProductName.getText().toString());
//            toinventoryUpdatestock.child(productID).child("quantity").setValue(manageProductPrice.getText().toString()); //quantity
//            toinventoryUpdatestock.child(productID).child("productID").setValue(productID);
//            toinventoryUpdatestock.child(productID).child("productCategory").setValue(manageProductCategory.getText().toString());
//            toinventoryUpdatestock.child(productID).child("ID").setValue(id);
//            toinventoryUpdatestock.child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());


            manageProductName.setText("");
            // manageProductCode.setText("");
            manageProductDescription.setText("");
            manageProductPrice.setText("");
            // manageProductCuttedPrice.setText("");
            manageProductCategory.setText("");
            txtProductInfo.setText("Delivered Supply");
            updateButton.setBackgroundColor(Color.GREEN);

            loadingBar.dismiss();

            sendUserToMainActivity();
        }
    }

    private void sendUserToMainActivity() {
        //productsRef.child(productID).removeValue();
        //Intent mainIntent=new Intent(supplyrequests.this,MainActivity.class);
        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(mainIntent);
        //finish();
        View View = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(View, "Successfully Supplied\t send note to inventory", 20000)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mainIntent=new Intent(supplyrequests.this, FeedbackActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                    }
                });
        snackbar.getView().setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        snackbar.show();
        Intent mainIntent=new Intent(supplyrequests.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    private void admin(final String category,final String description, final String name, final String price,final String qprice,
                       final String status) {

        final ProgressDialog progressDialog = new ProgressDialog(supplyrequests.this);
        progressDialog.setTitle("Approving");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.
                POST, LoginActivity.ip+"supply-orders.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("successfully")) {
                    Toast.makeText(supplyrequests.this, "approved", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(manageRequest1.this, LoginActivity.class));
                    progressDialog.dismiss();
                    // finish();
                } else {
                    Toast.makeText(supplyrequests.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(supplyrequests.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("category", category);
                param.put("description",description);
                param.put("name", name);
                param.put("price", price);
                param.put("qprice", qprice);
                param.put("status", status);

                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton.getmInstance(supplyrequests.this).addToRequestQueue(request);


    }
}
