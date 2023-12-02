package com.example.tenthskate.stockinventor.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tenthskate.Driver.Fragment.displaymessage;
import com.example.tenthskate.stockinventor.Fragment.DelivererdSto;
import com.example.tenthskate.stockinventor.Fragment.OrdersFragment;
import com.example.tenthskate.stockinventor.Fragment.ProductsFragment;
import com.example.tenthskate.stockinventor.Fragment.deliveredReq;

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
//            case 3:
//                return new stocksupplied_confirm_add_Comment();
            case 3:
                return new deliveredReq();
            //messages uncomment
            case 4:
                displaymessage fragment = new displaymessage();
                Bundle bundle = new Bundle();
                bundle.putString("user", "Inventory Manager");
                fragment.setArguments(bundle);
                return fragment;
//            case 2:
//                return new MessageFragment();
            default:
                return new OrdersFragment();
        }
    }

    @Override
    public int getCount() {
//change to 3 for messages
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "N_O";
            case 1:
                return "Stock";
                case 2:
                return "D.Orders";

//            case 3:
//                return "Supps";

            case 3:
                return "Add";
            case 4:
                return "Chat";
            default:
                return null;
        }
    }
}
