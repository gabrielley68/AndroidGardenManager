package com.mds.gab.gardenmanager.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mds.gab.gardenmanager.Manager.UserManager;
import com.mds.gab.gardenmanager.Models.Product;
import com.mds.gab.gardenmanager.Models.User;
import com.mds.gab.gardenmanager.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private ArrayList<Product> products;

    public ShopAdapter(ArrayList<Product> products){
        this.products = products;
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView description;
        private final ImageView image;
        private final Button button;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.shop_row_title);
            description = itemView.findViewById(R.id.shop_row_description);
            image = itemView.findViewById(R.id.shop_row_image);
            button = itemView.findViewById(R.id.buy_me_button);
        }
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_shop_row, viewGroup, false);
        return new ShopAdapter.ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopViewHolder shopViewHolder, int i) {
        final Product product = products.get(i);
        shopViewHolder.title.setText(product.getName());
        shopViewHolder.description.setText(product.getDescription());
        try {
            InputStream is = shopViewHolder.button.getContext().getAssets().open("images/" + product.getImageUrl());
            shopViewHolder.image.setImageDrawable(Drawable.createFromStream(is, null));
        } catch (Exception e){
            shopViewHolder.image.setImageResource(R.drawable.placeholder);
        }
        shopViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(shopViewHolder.button.getContext())
                        .setTitle("Transaction")
                        .setMessage("Do you really want to buy this ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                User user = UserManager.getInstance().getUser();
                                user.addProduct(product);
                                realm.copyToRealmOrUpdate(user);
                                realm.commitTransaction();
                                products.remove(shopViewHolder.getAdapterPosition());
                                notifyItemRemoved(shopViewHolder.getAdapterPosition());
                                notifyItemRangeChanged(shopViewHolder.getAdapterPosition(), products.size());
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setItems(ArrayList<Product> products){
        this.products = products;
    }
}
