package com.example.skadush.bucket_drops;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by skadush on 24/01/17.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        Realm.init(getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);

    }
}
