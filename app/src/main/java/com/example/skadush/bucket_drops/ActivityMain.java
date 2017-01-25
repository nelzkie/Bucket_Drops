package com.example.skadush.bucket_drops;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.skadush.bucket_drops.adapters.AdapterDrops;
import com.example.skadush.bucket_drops.beans.Drop;
import com.facebook.drawee.view.SimpleDraweeView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ActivityMain extends AppCompatActivity {

    Toolbar mToolbar;
    SimpleDraweeView simpleDraweeView;
    RecyclerView mRecyclerView;
    RealmResults<Drop> mRealmResult;
    AdapterDrops mAdapter;
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();
        mRealmResult = mRealm.where(Drop.class).findAllAsync();

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_drops);
        mAdapter =new AdapterDrops(this,mRealmResult);
        mRecyclerView.setAdapter(mAdapter);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(manager);
        setSupportActionBar(mToolbar);

        initBackgroundImageUsingFresco();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mRealmResult.addChangeListener(realmChangeListener);
    }

    private void initBackgroundImageUsingFresco() {
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.frescoImage);
        String path = "res:/" + R.drawable.bg; // Only one slash after res:

        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_XY);
        simpleDraweeView.setImageURI(Uri.parse(path));
    }

    public void ShowDialog(View view) {
        showDialogAdd();
    }

    private void showDialogAdd() {
        DialogAdd dialogAdd = new DialogAdd();
        dialogAdd.show(getSupportFragmentManager(), "Add");
    }

    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            mAdapter.Update(mRealmResult);
        }
    };
}
