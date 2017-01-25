package com.example.skadush.bucket_drops.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import com.example.skadush.bucket_drops.extras.Util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by skadush on 25/01/17.
 */
public class BucketRecyclerView extends RecyclerView {

    List<View> mNonEmptyViews = Collections.emptyList();
    List<View>mEmptyViews = Collections.emptyList();
    AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggletViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggletViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggletViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggletViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggletViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggletViews();
        }
    };

    private void toggletViews() {
        if(getAdapter() != null && !mEmptyViews.isEmpty() && !mNonEmptyViews.isEmpty()){
            if(getAdapter().getItemCount() == 0){

                // show all the empty views
                Util.showViews(mEmptyViews);

                // hide the recycler view
                setVisibility(View.GONE);

                // hide all the views which are  meant to be hidden
                Util.hideViews(mNonEmptyViews);

            }else{

                //hide all the empty views
                Util.showViews(mNonEmptyViews);
                // show the recycler
                setVisibility(View.VISIBLE);

                // hide all the views which are  meant to be hidden
                Util.hideViews(mEmptyViews);
            }
        }
    }

    public BucketRecyclerView(Context context) {
        super(context);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if(adapter != null){
            adapter.registerAdapterDataObserver(mObserver);
        }

        mObserver.onChanged();
    }

    public void hideIfEmpty(View ...views) {
        mNonEmptyViews = Arrays.asList(views);

    }

    public void showIfEmpty(View ...emptyViews) {
        mEmptyViews = Arrays.asList(emptyViews) ;

    }
}
