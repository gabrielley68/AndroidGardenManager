package com.mds.gab.gardenmanager.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mds.gab.gardenmanager.Adapters.ShopAdapter;
import com.mds.gab.gardenmanager.Manager.UserManager;
import com.mds.gab.gardenmanager.Models.Product;
import com.mds.gab.gardenmanager.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ShopFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShopAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        recyclerView = view.findViewById(R.id.shop_recycler_view);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ShopAdapter(getProductsUser());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    private ArrayList<Product> getProductsUser(){
        final Realm realm = Realm.getDefaultInstance();
        RealmList<Product> userProducts = UserManager.getInstance().getUser().getProducts();
        Integer[] userProductsId = new Integer[userProducts.size()];
        for(int i = 0; i < userProducts.size(); i++){
            userProductsId[i] = userProducts.get(i).getId();
        }
        RealmResults<Product> productRealmResults = realm.where(Product.class).not().in("id", userProductsId).findAll();
        return new ArrayList<>(productRealmResults);
    }
}
