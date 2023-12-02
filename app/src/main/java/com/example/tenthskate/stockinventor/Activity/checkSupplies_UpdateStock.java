package com.example.tenthskate.stockinventor.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class checkSupplies_UpdateStock extends AppCompatActivity {
    public FirebaseDatabase firebaseDatabase;
    private ImageView manageProductImage;
    String m;
    private EditText manageProductName, manageProductDescription, manageProductPrice,price1;
    private TextView manageProductCategory, txtProductInfo;
    private ProgressDialog loadingBar;
    private String productID, retProductCategory,retProductDescription,retProductPrice,retProductID,retProductName,iD;
    private String  price,suppliername;
    public DatabaseReference databaseReference;
    private Button updateButton;
    private DatabaseReference productsRef, categoryRef,pref,catref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_confirm_supplies_updatestock);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        productsRef = FirebaseDatabase.getInstance().getReference().child("all_confirmed_supplies");
     //   categoryRef = FirebaseDatabase.getInstance().getReference("all_supplied_supplies_suppliesCategory");

        pref = FirebaseDatabase.getInstance().getReference().child("allProducts");
        catref = FirebaseDatabase.getInstance().getReference("productsCategory");

        productID = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("retProductID")).toString();
        iD = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("ID")).toString();
        loadingBar=new ProgressDialog(checkSupplies_UpdateStock.this);
        price1 =  findViewById(R.id.supliername);
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
                    Toast.makeText(checkSupplies_UpdateStock.this,"DATABASE ERROR\t !",Toast.LENGTH_LONG).show();
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
                updateButton.setText("STOCK UPDATED");
            }
        });
    }

    private void updateInformation() {
        if (manageProductName.getText().toString().isEmpty()){
            manageProductName.setError("Product name required");
        }
//        if (manageProductCode.getText().toString().isEmpty()){
//            manageProductCode.setError("Product code required");
//        }
        if (manageProductDescription.getText().toString().isEmpty()){
            manageProductDescription.setError("Product description required");
        }
        if (manageProductPrice.getText().toString().isEmpty()){
            manageProductPrice.setError("Product quantity required");
        }
        else {
            loadingBar.setTitle("Updating");
            loadingBar.setMessage("Please wait, your information is updating...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            productsRef.child(productID).child("status").setValue("Complete");
            Toast.makeText(this, iD+"Successfully RESTOCK!", Toast.LENGTH_SHORT).show();

            int mm = Integer.parseInt(m.trim());
            int mk = Integer.parseInt(manageProductPrice.getText().toString().trim());
            int sum = mm + mk;
            pref.child(iD).child("quantity").setValue(String.valueOf(sum));
            catref.child(retProductCategory).child(iD).child("quantity").setValue(String.valueOf(sum));


            /*productsRef.child(suppliername).child(productID).child("status").setValue("PAID");
            productsRef.child(suppliername).child(productID).child("price").setValue(price);
            productsRef.child(suppliername).child(productID).child("productName").setValue(manageProductName.getText().toString());
            productsRef.child(suppliername).child(productID).child("quantity").setValue(manageProductPrice.getText().toString()); //quantity
            productsRef.child(suppliername).child(productID).child("productID").setValue(productID);
            productsRef.child(suppliername).child(productID).child("ID").setValue(iD);
            productsRef.child(suppliername).child(productID).child("productDescription").setValue(manageProductDescription.getText().toString());
            productsRef.child(suppliername).child(productID).child("productCategory").setValue(manageProductCategory.getText().toString());
*/

            manageProductName.setText("");
            // manageProductCode.setText("");
            manageProductDescription.setText("");
            manageProductPrice.setText("");
            // manageProductCuttedPrice.setText("");
            manageProductCategory.setText("");
            txtProductInfo.setText("");
            loadingBar.dismiss();


          //  admin(retProductCategory,retProductDescription,retProductName,retProductPrice,price,"1");
            Toast.makeText(this,"Stock Updated Accordingly", Toast.LENGTH_SHORT).show();

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


                    Toast.makeText(checkSupplies_UpdateStock.this, "yes", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
                else{
                    Toast.makeText(checkSupplies_UpdateStock.this, "userName", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendUserToMainActivity() {
        Intent mainIntent=new Intent(checkSupplies_UpdateStock.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void admin(final String category,final String description, final String name, final String price,final String qprice,
                       final String status) {

        final ProgressDialog progressDialog = new ProgressDialog(checkSupplies_UpdateStock.this);
        progressDialog.setTitle("Approving");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.
                POST,LoginActivity.ip+"supply-orders.php", new Response.Listener<String>() {
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


    }
}