package com.example.skadush.bucket_drops.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.skadush.bucket_drops.MyApplication;
import com.example.skadush.bucket_drops.R;
import com.example.skadush.bucket_drops.beans.Drop;
import com.example.skadush.bucket_drops.extras.Util;
import io.realm.Realm;
import io.realm.RealmResults;

import java.util.ArrayList;

/**
 * Created by skadush on 24/01/17.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ISwiperListener {

    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    public static final int NO_ITEM = 2;
    public static final int COUNT_FOOTER = 1;
    public static final int COUNT_NO_ITEMS = 1;

    LayoutInflater mInflater;
    RealmResults<Drop> mResults;

    IAddListener mAddListener;
    IMarkListener markListener;
    Realm mRealm;
    Context context;

    int mFilterOption;

    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> mResults) {
        mInflater = LayoutInflater.from(context);
        mRealm = realm;
        Update(mResults);


    }

    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> mResults, IAddListener listener,
                        IMarkListener markListener) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mAddListener = listener;

        mRealm = realm;
        this.markListener = markListener;
        Update(mResults);


    }

    public void setAddListener(IAddListener listener) {
        mAddListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new FooterHolder(view, mAddListener);
        } else if (viewType == NO_ITEM) {
            View view = mInflater.inflate(R.layout.no_item, parent, false);
            return new NoItemsHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            return new DropHolder(view, markListener);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mResults.get(position);

            dropHolder.setWhat(drop.getWhat());
            dropHolder.setWhen(drop.getWhen());
            dropHolder.setBackground(drop.isCompleted());
        }

    }

    @Override
    public int getItemCount() {
        if (!mResults.isEmpty()) {
            return mResults.size() + COUNT_FOOTER;
        } else {
            if (mFilterOption == Filter.LEAST_TIME_LEFT || mFilterOption == Filter.MOST_TIME_LEFT || mFilterOption == Filter.NONE) {
                return ITEM;
            } else {
                return COUNT_NO_ITEMS + COUNT_FOOTER;
            }
        }

    }

    @Override
    public void onSwipe(int position) {
        if (position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }
    }

    public void markComplete(int position) {
        if (position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).setCompleted(true);
            mRealm.commitTransaction();
            notifyItemChanged(position);
        }

    }

    public static class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextWhat, mTextWhen;
        IMarkListener markListener;

        Context context;
        View mItemView;

        public DropHolder(View itemView, IMarkListener markListener) {
            super(itemView);
            context = itemView.getContext();
            mItemView = itemView;
            itemView.setOnClickListener(this);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
            mTextWhen = (TextView) itemView.findViewById(R.id.tv_When);
            this.markListener = markListener;
        }

        public void setWhat(String what) {
            mTextWhat.setText(what);
        }

        @Override
        public void onClick(View v) {
            markListener.onMark(getAdapterPosition());
        }

        public void setBackground(boolean completed) {
            Drawable drawable;
            if (completed) {
                drawable = ContextCompat.getDrawable(context, R.color.bg_drop_row_dark);
            } else {
                drawable = ContextCompat.getDrawable(context, R.drawable.bg_row_drop);
            }

            Util.setBackground(mItemView, drawable);

        }

        public void setWhen(long when) {
            mTextWhen.setText(DateUtils.getRelativeTimeSpanString(when, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mBtnAdd;
        IAddListener listener;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
        }

        public FooterHolder(View itemView, IAddListener listener) {
            super(itemView);
            this.listener = listener;

            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.Add();
        }
    }

    public static class NoItemsHolder extends RecyclerView.ViewHolder {

        public NoItemsHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public long getItemId(int position) {

        if(position < mResults.size()){
            Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
            return  mResults.get(position).getAdded();

        }

        return RecyclerView.NO_ID;
    }

    @Override
    public int getItemViewType(int position) {

        Log.d("item","" + ITEM);

        if (!mResults.isEmpty()) {
            if (position < mResults.size()) {
                return ITEM;
            } else {
                return FOOTER;
            }
        }else{
            if(mFilterOption == Filter.COMPETE || mFilterOption == Filter.INCOMPLETE){
                if(position == 0){
                    return  NO_ITEM;
                }else{
                    return  FOOTER;
                }
            }else{
                return ITEM;
            }
        }
    }

    public void Update(RealmResults<Drop> results) {
        mResults = results;

        mFilterOption = MyApplication.load(context);
        notifyDataSetChanged(); // will refresh the adapter


    }
}
