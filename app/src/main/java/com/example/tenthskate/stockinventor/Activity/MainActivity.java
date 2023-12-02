package com.example.tenthskate.stockinventor.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.tenthskate.Clients.Activities.LoginActivity;
import com.example.tenthskate.R;
import com.example.tenthskate.stockinventor.Adapter.TabsAccessorAdapter;
import com.example.tenthskate.stockinventor.Fragment.stocksupplied_confirm_add_Comment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("inventory");

        ViewPager myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        TabsAccessorAdapter myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);

        TabLayout myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.conf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.approvesupps:
                stocksupplied_confirm_add_Comment fragment = new stocksupplied_confirm_add_Comment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_approved_supplies, fragment);
                transaction.addToBackStack(null); // Optional: Add this line if you want to add the fragment to the back stack
                transaction.commit();
                return true;

            case R.id.req:
                Intent manage = new Intent(MainActivity.this,
                        com.example.tenthskate.SupplyAuto.Activity.MainActivity.class);

                startActivity(manage);
                return true;
            case R.id.logou:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Inventory Logout");
                builder.setMessage("ArE you sure you want to logout your from inventory account ?")
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
}