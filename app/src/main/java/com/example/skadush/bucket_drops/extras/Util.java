package com.example.skadush.bucket_drops.extras;

import android.view.View;

import java.util.List;

/**
 * Created by skadush on 25/01/17.
 */
public class Util {
    public static void showViews(List<View> views) {
        for (View view :
                views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void  hideViews(List<View>views){
        for (View view :
                views) {
            view.setVisibility(View.GONE);
        }
    }
}
