package com.mds.gab.gardenmanager.Application;

import android.app.Application;

import com.mds.gab.gardenmanager.Helpers.DataReader;
import com.mds.gab.gardenmanager.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GardenManagerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(3)
                .name(getResources().getString(R.string.app_name) + ".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        //Init the products list in Realm DB
        DataReader.initProducts(this);
    }
}
