package com.example.tenthskate.Supplier.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenthskate.R;
import com.example.tenthskate.Supplier.Class.reqUpload;
import com.example.tenthskate.Supplier.Prevalent.Prevalent;
import com.example.tenthskate.Supplier.Activity.Supplier_confirmSupply;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class Finance_supplyPaymentsAdapter extends RecyclerView.Adapter<Finance_supplyPaymentsAdapter.ProductsViewHolder> {
    private Context mContext;
    private List<reqUpload> mUploads;

    DatabaseReference allProducts = FirebaseDatabase.getInstance().getReference().child("all_confirmed_supplies").child(Prevalent.currentOnlineSupplier.getName());
    //DatabaseReference categoryProducts = FirebaseDatabase.getInstance().getReference().child("SupplierCategory");


    public Finance_supplyPaymentsAdapter(Context context, List<reqUpload> uploads)
    {
        mContext=context;
        mUploads=uploads;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.confirmpaidsupplynow, viewGroup,false);
        return new ProductsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, final int i) {
        final reqUpload uploadCur=mUploads.get(productsViewHolder.getAdapterPosition());
        productsViewHolder.product_name.setText(uploadCur.getProductName());
        productsViewHolder.product_pack.setText(uploadCur.getQuantity());
        productsViewHolder.code.setText(uploadCur.getMpesa());
        productsViewHolder.quant.setText(uploadCur.getStatus());
        //productsViewHolder.described.setText(uploadCur.getProductDescription());
        productsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent supplyIntent = new Intent(mContext, Supplier_confirmSupply.class);
                supplyIntent.putExtra("retProductID", uploadCur.getProductID());
                supplyIntent.putExtra("ID",uploadCur.getID());
                mContext.startActivity(supplyIntent);
            }
        });
        productsViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final int which_item = i;

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext,androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);

                dialog.setTitle("Not available right now?")
                        .setMessage("Do you want to delete this product?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int k) {
                                mUploads.remove(which_item);
                                allProducts.child(uploadCur.getProductID()).removeValue();
                                //categoryProducts.child(uploadCur.getProductCategory()).child(uploadCur.getProductID()).removeValue();
                            }
                        })
                        .setNegativeButton("NO",null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    @Override
    public long getItemId(int item_pos)
    {
        return super.getItemId(item_pos);
    }


    public static class ProductsViewHolder extends RecyclerView.ViewHolder{
        public TextView product_name, product_pack,quant,code,described;
        //public ImageView product_image;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name=itemView.findViewById(R.id.product_card_name1);
            product_pack=itemView.findViewById(R.id.product_card_price1);
            quant=itemView.findViewById(R.id.status1);
            code=itemView.findViewById(R.id.mpesa);
            //described = itemView.findViewById(R.id.product_card_description);
        }

    }
}

