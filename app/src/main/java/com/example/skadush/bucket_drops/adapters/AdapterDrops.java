package com.example.skadush.bucket_drops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.skadush.bucket_drops.R;
import com.example.skadush.bucket_drops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmResults;

import java.util.ArrayList;

/**
 * Created by skadush on 24/01/17.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    LayoutInflater mInflater;
    RealmResults<Drop> mResults;

    IAddListener mAddListener;
    public AdapterDrops(Context context, RealmResults<Drop> mResults) {
        mInflater = LayoutInflater.from(context);
        Update(mResults);

    }
    public AdapterDrops(Context context, RealmResults<Drop> mResults,IAddListener listener) {
        mInflater = LayoutInflater.from(context);
        mAddListener = listener;
        Update(mResults);

    }

    public void setAddListener(IAddListener listener){
        mAddListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new FooterHolder(view,mAddListener);
        } else {
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            return new DropHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  DropHolder){
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mResults.get(position);
            dropHolder.mTextWhat.setText(drop.getWhat());
        }

    }

    @Override
    public int getItemCount() {
        return mResults.size() + 1;
    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;

        public DropHolder(View itemView) {
            super(itemView);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
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

    @Override
    public int getItemViewType(int position) {

        if (mResults == null || position < mResults.size()) {
            return ITEM;

        } else {
            return FOOTER;
        }
    }

    public void Update(RealmResults<Drop> results) {
        mResults = results;
        notifyDataSetChanged(); // will refresh the adapter

    }
}
