package com.example.tenthskate.Driver.Adapter;
//package in.Rumishe.Driver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenthskate.Driver.Activity.ChatActivity;
import com.example.tenthskate.Driver.Class.flightModel;
import com.example.tenthskate.R;

import java.util.List;

//import in.Rumishe.AppMain.Activity.LoginActivity;
//import in.Rumishe.AppMain.Activity.Singleton;
//import in.Rumishe.AppMain.R;
//import in.Rumishe.Driver.Activity.ChatActivity;
//import in.Rumishe.Driver.Class.flightModel;

public class receAdapter extends RecyclerView.Adapter<receAdapter.ProductViewHolder> {


    private Context mCtx;
    String status;
    private List<flightModel> productList;

    public receAdapter(Context mCtx, List<flightModel> productList, String status) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.status = status;

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.message, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        flightModel product = productList.get(position);


        holder.c.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                Intent MainIntent = new Intent(mCtx, ChatActivity.class);
                MainIntent.putExtra("choice",product.getSender());
                MainIntent.putExtra("current",status);
                mCtx.startActivity(MainIntent);

            }

        });
        holder.name.setText(product.getSender());
        //holder.email.setText(product.getEmail());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView name, email;
        CardView c;
        //ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.pilot);
            //email = itemView.findViewById(R.id.email);

            c = itemView.findViewById(R.id.c);
        }
    }


}
