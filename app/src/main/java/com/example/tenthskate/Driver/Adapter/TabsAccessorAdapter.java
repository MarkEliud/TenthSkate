package com.example.tenthskate.Driver.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tenthskate.Driver.Fragment.DeliveredFragment;
import com.example.tenthskate.Driver.Fragment.displaymessage;
import com.example.tenthskate.Driver.Fragment.incomingFragment;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new incomingFragment();
            case 1:
                return new DeliveredFragment();
                //messages uncomment
            case 2:
                displaymessage fragment = new displaymessage();
                Bundle bundle = new Bundle();
                bundle.putString("user", "Driver");
                fragment.setArguments(bundle);
                return fragment;
            // return new displaymessage();
            //  break;
            default:
                return new incomingFragment();
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
                return "Incoming";
            case 1:
                return "Delivered";
            case 2:
                return "Messages";
            default:
                return null;
        }
    }
}
