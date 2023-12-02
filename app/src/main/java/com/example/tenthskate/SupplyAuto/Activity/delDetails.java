package com.example.tenthskate.SupplyAuto.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tenthskate.Clients.Activities.LoginActivity;
import com.example.tenthskate.Clients.Activities.Singleton;
import com.example.tenthskate.R;
import com.example.tenthskate.stockinventor.Class.apload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class delDetails extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 1;
    private String category;
    private ProgressDialog loadingBar;

    //  private StorageReference mStorageProductImagesRef;
    private DatabaseReference mDatabaseProductsRef, mDatabaseCategoryRef, pref,catref;
    private Uri imgUrl;

    private ImageView imgPreview;
    private String categor,name,desc,pId,price,math;
    private EditText  productCode, productDescription, productPrice, productQuantity;
    private ProgressBar uploadProgress;
    TextView productName;
    private StorageTask mUploadTask;
    private Spinner supplierSpinner;
    private DatabaseReference mDatabaseSuppliersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestactivity);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarq);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Request Supplies");
        // Initialize the spinner and database reference for suppliers
        supplierSpinner = findViewById(R.id.supplierSpinner);
        mDatabaseSuppliersRef = FirebaseDatabase.getInstance().getReference("employee").child("Suppliers");

        // Fetch the list of suppliers from the database
        mDatabaseSuppliersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> supplierList = new ArrayList<>();
                for (DataSnapshot supplierSnapshot : dataSnapshot.getChildren()) {
                    // Get the supplier name from the HashMap
                    HashMap<String, Object> supplierData = (HashMap<String, Object>) supplierSnapshot.getValue();
                    String supplierName = (String) supplierData.get("name"); // Assuming "name" is the key for the supplier name
                    if (supplierName != null) {
                        supplierList.add(supplierName);
                    }
                }

                // Create an ArrayAdapter using the list of suppliers and a default spinner layout
                ArrayAdapter<String> adapter = new ArrayAdapter<>(delDetails.this, android.R.layout.simple_spinner_item, supplierList);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                supplierSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur during data retrieval
                Toast.makeText(delDetails.this, "Error fetching suppliers.", Toast.LENGTH_SHORT).show();
            }
        });
//get details from suppyauto main screen
        Intent intent = getIntent();
        categor = intent.getStringExtra("category");
        name = intent.getStringExtra("name");
        desc = intent.getStringExtra("desc");
        pId = intent.getStringExtra("pId");
        // mStorageProductImagesRef = FirebaseStorage.getInstance().getReference("products");
        mDatabaseCategoryRef = FirebaseDatabase.getInstance().getReference("SupplierCategory");
        mDatabaseProductsRef = FirebaseDatabase.getInstance().getReference("allRequests");

        pref = FirebaseDatabase.getInstance().getReference("allProducts");
        catref = FirebaseDatabase.getInstance().getReference("productsCategory");

        TextView cat = findViewById(R.id.cat);
        cat.setText(categor);

        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        FloatingActionButton chooseImage = (FloatingActionButton) findViewById(R.id.chooseImage);
        uploadProgress = (ProgressBar) findViewById(R.id.uploadProgress);
        loadingBar = new ProgressDialog(this);

        productName = findViewById(R.id.uploadProductName);
        productName.setText(name);
        //productCode = (EditText) findViewById(R.id.uploadProductCode);
        productDescription = (EditText)findViewById(R.id.uploadProductDescription);
        productDescription.setText(desc);
        productPrice = (EditText) findViewById(R.id.uploadProductPrice);
        // productQuantity = (EditText) findViewById(R.id.uploadProductQuantity);



        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(delDetails.this, "Upload in progress", Toast.LENGTH_LONG).show();
                }

                else if (productPrice.getText().toString().isEmpty()){
                    productPrice.setError("Product price required");
                }


                else
                    uploadProduct();
            }

        });


    }
    private void uploadProduct() {

        loadingBar.setTitle("Submitting Request");
        loadingBar.setMessage("Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        String selectedSupplier = supplierSpinner.getSelectedItem().toString();

        String uploadID = mDatabaseProductsRef.push().getKey();
        assert uploadID != null;

        String searchableString = productName.getText().toString().trim()
                .replaceAll(" ","");

        apload upload = new apload(
                productName.getText().toString().trim(),
                // productCode.getText().toString().trim(),
                productDescription.getText().toString().trim(),

                categor,
                productPrice.getText().toString().trim(),
                // productQuantity.getText().toString().trim(),
                uploadID,searchableString.toLowerCase());

        //mDatabaseCategoryRef.child(categor).child(uploadID).setValue(upload);
        //mDatabaseProductsRef.child(uploadID).setValue(upload);

        //selected sup set product details
        //mDatabaseCategoryRef.child(selectedSupplier).child(categor).setValue(upload);
        mDatabaseProductsRef.child(selectedSupplier).child(uploadID).setValue(upload);

        //mDatabaseProductsRef.child(uploadID).child("status").setValue("pending");
        //mDatabaseCategoryRef.child(uploadID).child(categor).child("status").setValue("Pending");
        //selected set status supplier
        mDatabaseProductsRef.child(selectedSupplier).child(uploadID).child("status").setValue("pending");
        //mDatabaseCategoryRef.child(selectedSupplier).child(categor).child("status").setValue("pending");

        //mDatabaseProductsRef.child(uploadID).child("ID").setValue(pId);
        //mDatabaseCategoryRef.child(uploadID).child(categor).child("ID").setValue(pId);
        //selected sup set id
        mDatabaseProductsRef.child(selectedSupplier).child(uploadID).child("ID").setValue(pId);
        mDatabaseProductsRef.child(selectedSupplier).child(uploadID).child("Supplier").setValue(selectedSupplier);

        //mDatabaseCategoryRef.child(selectedSupplier).child(categor).child("ID").setValue(pId);

        admin(categor,desc,name,productPrice.getText().toString().trim());

        Toast.makeText(this,
                "Request Submitted successfully", Toast.LENGTH_LONG).show();
        // imgPreview.setImageResource(R.drawable.image_preview);
        productName.setText("");
        //  productCode.setText("");
        productDescription.setText("");
        productPrice.setText("");
        //   productQuantity.setText("");
        loadingBar.dismiss();
    }
    private void admin(final String category, final String description, final String name, final String price) {

        final ProgressDialog progressDialog = new ProgressDialog(delDetails.this);
        progressDialog.setTitle("Approving");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,LoginActivity.
                ip+"supply-orders.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("successfully")) {
                    Toast.makeText(delDetails.this, "approved", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(manageRequest1.this, LoginActivity.class));
                    progressDialog.dismiss();
                    // finish();
                } else {
                    Toast.makeText(delDetails.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(delDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("category", category);
                param.put("description",description);
                param.put("name", name);
                param.put("quantity", price);
                param.put("status", "0");

                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton.getmInstance(delDetails.this).addToRequestQueue(request);


    }
}