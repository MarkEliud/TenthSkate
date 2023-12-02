package com.example.tenthskate.Finance.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.tenthskate.Clients.Activities.LoginActivity;
import com.example.tenthskate.Finance.Adapter.TabsAccessorAdapter;
import com.example.tenthskate.Finance.Class.Order;
import com.example.tenthskate.R;
import com.example.tenthskate.stockinventor.Activity.requestd;
import com.example.tenthskate.stockinventor.Class.Upload;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Finance");

        ViewPager myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        TabsAccessorAdapter myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);

        TabLayout myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.realf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appr:
                Intent manageProductIntent = new Intent(MainActivity.this, requestd.class);
                // manageProductIntent.putExtra("retProductID", uploadCur.getProductID());
                startActivity(manageProductIntent);
                return true;
            case R.id.pdf:
               // load();
                return true;
            case R.id.logou:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("finance Logout");
                builder.setMessage("Are you sure you want to logout your from finance account ?")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                //DeleteToken();
                                //     FirebaseAuth.getInstance().signOut();
                                Intent intent_signout = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent_signout);
                                finish();
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void load(){
         List<Order> mUploads;

        mUploads=new ArrayList<>();
        final DatabaseReference mDatabaseProductsRef = FirebaseDatabase.getInstance().getReference("Orders");
        Query query = mDatabaseProductsRef.orderByChild("status").equalTo("Approved");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mUploads.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Order order = postSnapshot.getValue(Order.class);
                        mUploads.add(order);
                    }
                   // mAdapter = new deliverdAdapter(fragmentActivity, mUploads);
                    //mRecyclerView.setAdapter(mAdapter);

                    //progressBar.setVisibility(View.INVISIBLE);

                    // Generate PDF report
                    generatePdfReport(mUploads);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            //    progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void generatePdfReport(List<Order> orders) {
        try {
            // Create a new PDF document
            Document document = new Document();
            String pdfFilePath = Environment.getExternalStorageDirectory() + "/OrderReport.pdf";

            // Initialize PDF writer and open the document
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();

            // Add content to the PDF document
            PdfPTable table = new PdfPTable(6); // 6 columns for name, date, items, value, pesa, status
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Add table headers
            table.addCell("Name");
            table.addCell("Date");
            table.addCell("Items");
            table.addCell("Value");
            table.addCell("Pesa");
            table.addCell("Status");

            // Loop through the orders and add data to the table
            double totalValue = 0.0;
            for (Order order : orders) {
                table.addCell(order.getFullName());
                table.addCell(order.getDate());

                // Create a StringBuilder to store the items information
                StringBuilder items = new StringBuilder();
                List<Upload> orderItems = order.getItems();
                for (int i = 0; i < orderItems.size(); i++) {
                    Upload item = orderItems.get(i);
                    items.append(item.getProductName());
                    if (i < orderItems.size() - 1) {
                        items.append(", ");
                    }
                }
                table.addCell(items.toString());

                table.addCell(order.getValue());
                table.addCell(order.getPesa());
                table.addCell(order.getStatus());

                // Calculate the total value
                totalValue += Double.parseDouble(order.getValue());
            }

            // Add the total value row
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("Total Value");
            table.addCell(String.valueOf(totalValue));
            table.addCell("");

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            // Open the generated PDF
            File pdfFile = new File(pdfFilePath);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile), "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}