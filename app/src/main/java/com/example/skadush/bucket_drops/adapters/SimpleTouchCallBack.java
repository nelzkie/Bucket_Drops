package com.example.skadush.bucket_drops.adapters;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.example.skadush.bucket_drops.beans.Drop;

/**
 * Created by skadush on 25/01/17.
 */
public class SimpleTouchCallBack extends Callback {
    ISwiperListener listener;

    public SimpleTouchCallBack(ISwiperListener listener) {
        this.listener = listener;
    }



    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        // In english environment. reading starts from left-right. So ItemTouchHelper.END means RIGHT
        return makeMovementFlags(0,ItemTouchHelper.END);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(viewHolder instanceof AdapterDrops.DropHolder){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(viewHolder instanceof AdapterDrops.DropHolder){
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }


    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(viewHolder instanceof AdapterDrops.DropHolder){
            listener.onSwipe(viewHolder.getAdapterPosition());
        }

    }
}
