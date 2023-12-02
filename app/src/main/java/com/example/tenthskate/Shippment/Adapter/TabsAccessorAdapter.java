package com.example.tenthskate.Shippment.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tenthskate.Driver.Fragment.displaymessage;
import com.example.tenthskate.Shippment.Fragment.DelivererdSto;
import com.example.tenthskate.Shippment.Fragment.OrdersFragment;
import com.example.tenthskate.Shippment.Fragment.ProductsFragment;

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
                return new ProductsFragment();
                //messages uncomment
            case 2:
                return new DelivererdSto();
            case 3:
                displaymessage fragment = new displaymessage();
                Bundle bundle = new Bundle();
                bundle.putString("user", "Shipment Manager");
                fragment.setArguments(bundle);
                return fragment;
            //messages uncomment

//            case 2:
//                return new MessageFragment();
            default:
                return new OrdersFragment();
        }
    }

    @Override
    public int getCount() {
//change to 3 for messages
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Incoming";
            case 1:
                return "Intransit";
                case 2:
                return "Delivered";
            case 3:
                return "Messages";

            default:
                return null;
        }
    }
}
