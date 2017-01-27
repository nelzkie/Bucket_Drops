package com.example.skadush.bucket_drops;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.skadush.bucket_drops.adapters.Filter;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.w3c.dom.Text;

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

    public static void save(Context context, int filterOption) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("filter", filterOption);
        editor.apply(); // apply() is async while commit() is sync
    }

    public static int load(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int filterOption = pref.getInt("filter", Filter.NONE);
        return filterOption;
    }

   public static void setCustomFont(Context context,String path, TextView... textViews){
       Typeface typeface = Typeface.createFromAsset(context.getAssets(),path);

       for(TextView t : textViews){
           t.setTypeface(typeface);
       }

   }
}
