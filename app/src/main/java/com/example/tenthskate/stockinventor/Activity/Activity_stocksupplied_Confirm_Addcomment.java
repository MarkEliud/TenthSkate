package com.example.tenthskate.stockinventor.Activity;


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

import com.example.tenthskate.Driver.Fragment.displaymessage;
import com.example.tenthskate.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.Objects;

public class Activity_stocksupplied_Confirm_Addcomment extends AppCompatActivity {
    public FirebaseDatabase firebaseDatabase;
    private ImageView manageProductImage;
    String m;
    private EditText manageProductName,manageProductInventoryComments,AmountOFstockSupplied, manageProductDescription, manageProductPrice,price1;
    private TextView manageProductCategory, txtProductInfo;
    private ProgressDialog loadingBar;
    private String productID, retProductCategory,retProductDescription,retProductPrice,retProductID,retProductName,iD;
    private String  StockSuppliedprice,suppliername;
    public DatabaseReference databaseReference;
    private Button updateButton;
    private DatabaseReference productsRef,tofinance, categoryRef,pref,catref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliedstock_adding_inventory_coment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        tofinance = FirebaseDatabase.getInstance().getReference().child("all_confirmed_supplies");


        productsRef = FirebaseDatabase.getInstance().getReference().child("all_confirmed_supplies");
        categoryRef = FirebaseDatabase.getInstance().getReference("all_confirmed_supplies_suppliesCategory");

        pref = FirebaseDatabase.getInstance().getReference().child("allProducts");
        catref = FirebaseDatabase.getInstance().getReference("productsCategory");

        productID = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("retProductID")).toString();
        iD = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("ID")).toString();
        loadingBar=new ProgressDialog(Activity_stocksupplied_Confirm_Addcomment.this);
        price1 =  findViewById(R.id.supliername);
        AmountOFstockSupplied = findViewById(R.id.suplierAmount);
        manageProductInventoryComments = findViewById(R.id.manageProductInventoryComments);
        //    manageProductImage = (ImageView) findViewById(R.id.manageProductImage);
        manageProductName = (EditText) findViewById(R.id.manageProductName);
        // manageProductCode = (EditText) findViewById(R.id.manageProductCode);
        manageProductDescription = (EditText) findViewById(R.id.manageProductDescription);
        manageProductPrice = (EditText) findViewById(R.id.manageProductquantit);
        //manageProductCuttedPrice = (EditText) findViewById(R.id.manageProductCuttedPrice);
        manageProductCategory = (TextView) findViewById(R.id.manageProductCategory);
        updateButton = (Button) findViewById(R.id.updateButton);
        txtProductInfo = (TextView) findViewById(R.id.txtProductInfo);

        productsRef.child(productID).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //price = Objects.requireNonNull(snapshot.child("price").getValue()).toString();

                    retProductName = Objects.requireNonNull(snapshot.child("productName").getValue()).toString();
                    retProductCategory = Objects.requireNonNull(snapshot.child("productCategory").getValue()).toString();
                    //String retProductCode = Objects.requireNonNull(snapshot.child("productCode").getValue()).toString();
                    retProductDescription = Objects.requireNonNull(snapshot.child("productDescription").getValue()).toString();
                    //   String retProductImageUrl = Objects.requireNonNull(snapshot.child("productImageUrl").getValue()).toString();
                    retProductPrice = Objects.requireNonNull(snapshot.child("productPrice").getValue()).toString(); //quantity
                    StockSuppliedprice=Objects.requireNonNull(snapshot.child("price").getValue().toString());
                    AmountOFstockSupplied.setText(StockSuppliedprice);
                    //    String retProductCuttedPrice = Objects.requireNonNull(snapshot.child("productCuttedPrice").getValue()).toString();
                    retProductID = Objects.requireNonNull(snapshot.child("productID").getValue()).toString();
                    suppliername = Objects.requireNonNull(snapshot.child("Supplier").getValue()).toString();
                    price1.setText(suppliername);
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
                }else {
                    Toast.makeText(Activity_stocksupplied_Confirm_Addcomment.this,"DATABASE ERROR\t !",Toast.LENGTH_LONG).show();
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference df;
        df=FirebaseDatabase.getInstance().getReference("allProducts").child(iD);
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot:snapshot.getChildren()){

                    m=snapshot.child("quantity").getValue(String.class);
                    //Toast.makeText(manageRequest1.this, m, Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInformation();
                updateButton.setBackgroundColor(getResources().getColor(R.color.productPriceGreen));
                updateButton.setEnabled(false);
                updateButton.setText("Comment Submitted");
            }
        });
    }

    private void updateInformation() {
        if(manageProductInventoryComments.getText().toString().isEmpty()){
            manageProductInventoryComments.setError("Inventory's Comment Required");
        }
        else if(manageProductInventoryComments.getText().toString().trim().equals("Rejected")){
            View View = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(View, "You Have Rejected this supply.Tell Supplier Why", 20000)
                    .setAction("OKAY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //notify supplier why reject order

                            displaymessage fragment = new displaymessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("user", "Inventory Manager");
                            fragment.setArguments(bundle);
                            //sendUserToMainActivity();
                        }
                    });
            snackbar.getView().setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));

            snackbar.show();
        }
        else if (manageProductName.getText().toString().isEmpty()){
            manageProductName.setError("Product name required");
        }
        else if (AmountOFstockSupplied.getText().toString().isEmpty()){
            AmountOFstockSupplied.setError("price  required");
        }
        else if (manageProductDescription.getText().toString().isEmpty()){
            manageProductDescription.setError("Product description required");
        }
        else if (manageProductPrice.getText().toString().isEmpty()){
            manageProductPrice.setError("Product price required");
        }
//        if (manageProductCuttedPrice.getText().toString().isEmpty()){
//            manageProductCuttedPrice.setError("Product cutted price required");
//        }
        else {
            loadingBar.setTitle("Submitting");
            loadingBar.setMessage("Please wait, your information is being Processed...");
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
            productsRef.child(productID).child("status").setValue(manageProductInventoryComments.getText().toString().trim());
            productsRef.child(productID).child("Supplier").setValue(suppliername);
            productsRef.child(productID).child("price").setValue(AmountOFstockSupplied.getText().toString());

            //String uploadID= tofinance.push().getKey();

            tofinance.child(productID).child("Supplier").setValue(suppliername);
            tofinance.child(productID).child("status").setValue(manageProductInventoryComments.getText().toString().trim());
            tofinance.child(productID).child("price").setValue(StockSuppliedprice);
            tofinance.child(productID).child("productName").setValue(manageProductName.getText().toString());
            tofinance.child(productID).child("productPrice").setValue(manageProductPrice.getText().toString()); //quantity
            tofinance.child(productID).child("productID").setValue(productID);
            tofinance.child(productID).child("productCategory").setValue(manageProductCategory.getText().toString());
            tofinance.child(productID).child("ID").setValue(iD);
            tofinance.child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());


            manageProductName.setText("");
            // manageProductCode.setText("");
            manageProductDescription.setText("");
            manageProductInventoryComments.setText("SUBMITTED");
            // manageProductCuttedPrice.setText("");
            manageProductCategory.setText("");
            txtProductInfo.setBackgroundColor(Color.GREEN);
            updateButton.setText("DONE");
            loadingBar.dismiss();

            View View = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(View, "Comment Forwarded to finance Successfully", 20000)
                    .setAction("OKAY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //submit supplies

                        }
                    });
            snackbar.getView().setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
            snackbar.show();

            sendUserToMainActivity();



        }
    }

    private void fb(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        //databaseReference = firebaseDatabase.getReference("UserD").child("Drivers").child(userName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {


                    Toast.makeText(Activity_stocksupplied_Confirm_Addcomment.this, "yes", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
                else{
                    Toast.makeText(Activity_stocksupplied_Confirm_Addcomment.this, "userName", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendUserToMainActivity() {
        Intent mainIntent=new Intent(Activity_stocksupplied_Confirm_Addcomment.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    /*private void admin(final String category,final String description, final String name, final String price,final String qprice,
                       final String status) {

        final ProgressDialog progressDialog = new ProgressDialog(Activity_stocksupplied_Confirm_Addcomment.this);
        progressDialog.setTitle("Approving");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,"http://"+ LoginActivity.ip+"/tenth/empire/supply-orders.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("successfully")) {
                    Toast.makeText(checkSupplies_UpdateStock.this, "approved", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(manageRequest1.this, LoginActivity.class));
                    progressDialog.dismiss();
                    // finish();
                } else {
                    Toast.makeText(checkSupplies_UpdateStock.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(checkSupplies_UpdateStock.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        Singleton.getmInstance(checkSupplies_UpdateStock.this).addToRequestQueue(request);


    }*/
}

