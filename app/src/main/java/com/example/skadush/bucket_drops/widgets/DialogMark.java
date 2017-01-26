package com.example.skadush.bucket_drops.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.skadush.bucket_drops.ActivityMain;
import com.example.skadush.bucket_drops.R;
import com.example.skadush.bucket_drops.adapters.ICompleteListener;

/**
 * Created by skadush on 25/01/17.
 */
public class DialogMark extends DialogFragment {

    ImageButton mBtnClose;
    Button mButtonCompleted;
    ICompleteListener iCompleteListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_mark_close);
        mButtonCompleted = (Button) view.findViewById(R.id.btn_mark_completed);

        mButtonCompleted.setOnClickListener(mBtnClickListener);
        mBtnClose.setOnClickListener(mBtnClickListener);



    }

    View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_mark_completed:
                    markAsCompelte();
                    break;
            }
            dismiss();
        }
    };

    private void markAsCompelte() {
        Bundle arguments = getArguments();

        if(iCompleteListener != null && arguments != null){
            int position = arguments.getInt("POSITION");
            iCompleteListener.onComplete(position);
        }
    }

    public void setCompleteListener(ICompleteListener iCompleteListener) {
        this.iCompleteListener = iCompleteListener;
    }
}
