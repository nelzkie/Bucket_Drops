package com.example.skadush.bucket_drops;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.skadush.bucket_drops.adapters.*;
import com.example.skadush.bucket_drops.beans.Drop;
import com.example.skadush.bucket_drops.widgets.BucketRecyclerView;
import com.example.skadush.bucket_drops.widgets.DialogMark;
import com.facebook.drawee.view.SimpleDraweeView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class ActivityMain extends AppCompatActivity implements IAddListener, IMarkListener, ICompleteListener {

    Toolbar mToolbar;
    SimpleDraweeView simpleDraweeView;
    BucketRecyclerView mRecyclerView;
    RealmResults<Drop> mRealmResult;
    AdapterDrops mAdapter;
    Realm mRealm;

    View emptyView;


    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            mAdapter.Update(mRealmResult);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();
        int filterOption = MyApplication.load(this);
        loadResults(filterOption);

        



        mToolbar = (Toolbar) findViewById(R.id.toolBar);

        mRecyclerView = (BucketRecyclerView) findViewById(R.id.rv_drops);
        mRecyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
       mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new AdapterDrops(this, mRealm, mRealmResult, this, this);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);

        emptyView = findViewById(R.id.empty_drops);

        mRecyclerView.hideIfEmpty(mToolbar);
        mRecyclerView.showIfEmpty(emptyView);

//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(manager);
        setSupportActionBar(mToolbar);

        initSwipe();


        initBackgroundImageUsingFresco();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int filterOption = Filter.NONE;
        boolean handle = true;
        switch (item.getItemId()) {
            case R.id.action_add:

                showDialogAdd();
                break;
            case R.id.action_sort_none:
                filterOption = Filter.NONE;
                break;

            case R.id.action_sort_descending_date:

                filterOption = Filter.MOST_TIME_LEFT;


                break;
            case R.id.action_sort_ascending_date:

                filterOption = Filter.LEAST_TIME_LEFT;


                break;
            case R.id.action_show_complete:
                filterOption = Filter.COMPETE;



                break;
            case R.id.action_show_incomplete:
                filterOption = Filter.INCOMPLETE;




                break;
            default:

                handle = false;
                break;
        }
        MyApplication.save(this,filterOption);

        loadResults(filterOption);
        return handle;
    }

    private void initSwipe() {
        // setting up the swipe in activity main
        SimpleTouchCallBack callBack = new SimpleTouchCallBack(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        helper.attachToRecyclerView(mRecyclerView);
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

    private void showDialogMark(int position) {
        DialogMark dialogMark = new DialogMark();

        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        dialogMark.setArguments(bundle);
        dialogMark.setCompleteListener(this);
        dialogMark.show(getSupportFragmentManager(), "Mark");
    }


    @Override
    public void Add() {
        showDialogAdd();
    }

    @Override
    public void onMark(int position) {
        showDialogMark(position);
    }

    @Override
    public void onComplete(int position) {
        mAdapter.markComplete(position);
    }


    void loadResults(int filter) {
        switch (filter) {
            case Filter.NONE:
                mRealmResult = mRealm.where(Drop.class).findAllAsync();
                break;
            case Filter.LEAST_TIME_LEFT:
                mRealmResult = mRealm.where(Drop.class).findAllSortedAsync("when");
                break;
            case Filter.MOST_TIME_LEFT:

                mRealmResult = mRealm.where(Drop.class).findAllSortedAsync("when", Sort.DESCENDING);
                break;
            case Filter.COMPETE:
                mRealmResult = mRealm.where(Drop.class).equalTo("completed", true).findAllAsync();
                break;
            case Filter.INCOMPLETE:
                mRealmResult = mRealm.where(Drop.class).equalTo("completed", false).findAllAsync();
                break;
        }
        mRealmResult.addChangeListener(realmChangeListener);
    }


}
