package com.example.tenthskate.Finance.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tenthskate.Driver.Fragment.displaymessage;
import com.example.tenthskate.Finance.Fragment.ApprovedFragment;
import com.example.tenthskate.Finance.Fragment.OrdersFragment;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OrdersFragment();
            case 1:
                return new ApprovedFragment();
            case 2:
                displaymessage fragment = new displaymessage();
                Bundle bundle = new Bundle();
                bundle.putString("user", "Finance");
                fragment.setArguments(bundle);
                return fragment;
            // return new displaymessage();
            default:
                return new OrdersFragment();
        }
    }

    @Override
    public int getCount() {
//change to 3 for messages
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "New Orders";
            case 1:
                return "Approved";
            case 2:
                return "Messages Threads";
            default:
                return null;
        }
    }
}
