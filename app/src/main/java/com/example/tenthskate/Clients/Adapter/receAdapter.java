package com.example.tenthskate.Clients.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenthskate.R;

import java.util.List;

public class receAdapter extends RecyclerView.Adapter<receAdapter.ProductViewHolder> {


    private Context mCtx;
    String status, current,smail;
    private List<model> productList;

    public receAdapter(Context mCtx, List<model> productList, String status, String current, String smail) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.current = current;
        this.status = status;
        this.smail = smail;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.message, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        model product = productList.get(position);


        holder.c.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, product.getEmail(), Toast.LENGTH_SHORT).show();
                Intent MainIntent = new Intent(mCtx, com.example.tenthskate.Clients.chat.ChatActivity.class);
                MainIntent.putExtra("choice",product.getName());//product.getName()
                MainIntent.putExtra("receiver",current);//current
                MainIntent.putExtra("sender",status);
                MainIntent.putExtra("email",product.getEmail());
                MainIntent.putExtra("name",product.getName());
                MainIntent.putExtra("smail",smail);



                mCtx.startActivity(MainIntent);

            }

        });
        holder.name.setText(product.getName());
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
