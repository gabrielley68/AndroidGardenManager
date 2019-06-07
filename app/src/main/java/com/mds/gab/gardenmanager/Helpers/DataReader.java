package com.mds.gab.gardenmanager.Helpers;

import android.app.Activity;
import android.content.Context;

import com.mds.gab.gardenmanager.MainActivity;
import com.mds.gab.gardenmanager.Manager.UserManager;
import com.mds.gab.gardenmanager.Models.Product;
import com.mds.gab.gardenmanager.Models.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import io.realm.Realm;

public class DataReader {

    private static String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void initProducts(Context context){
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try{
            JSONObject object = new JSONObject(loadJSONFromAsset(context));
            JSONArray jsonProducts = object.getJSONArray("products");
            for(int i = 0; i < jsonProducts.length(); i++){
                JSONObject jsonProduct = jsonProducts.getJSONObject(i);
                Product product = new Product(
                    jsonProduct.getInt("id"),
                    jsonProduct.getString("product"),
                    jsonProduct.getString("description"),
                    jsonProduct.getString("image")
                );
                realm.copyToRealmOrUpdate(product);
                JSONArray jsonTasks = jsonProduct.getJSONArray("tasks");
                for(int j = 0; j < jsonTasks.length(); j++){
                    JSONObject jsonTask = jsonTasks.getJSONObject(i);
                    Task task = new Task(
                            jsonTask.getInt("id"),
                            jsonTask.getString("description"),
                            product
                    );
                    realm.copyToRealmOrUpdate(task);
                }
            }
        } catch (JSONException ex){
            ex.printStackTrace();
        }
        realm.commitTransaction();
    }
}
