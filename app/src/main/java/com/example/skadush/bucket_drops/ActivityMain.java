package com.example.skadush.bucket_drops;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.facebook.drawee.view.SimpleDraweeView;

public class ActivityMain extends AppCompatActivity {

    Toolbar mToolbar;
    SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(mToolbar);

        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.frescoImage);
        String path = "res:/" + R.drawable.bg; // Only one slash after res:

        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_XY);
        simpleDraweeView.setImageURI(Uri.parse(path));



    }
}
