package com.example.skadush.bucket_drops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by skadush on 24/01/17.
 */
public class DialogAdd extends DialogFragment {

    ImageButton mBntClose;
    EditText mInputWhat;
    DatePicker mInputPicker;
    Button mBtnAdd;

    public DialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBntClose = (ImageButton) view.findViewById(R.id.btnClose);
        mInputPicker = (DatePicker) view.findViewById(R.id.bpv_date);
        mInputWhat = (EditText) view.findViewById(R.id.et_drop);
        mBtnAdd = (Button) view.findViewById(R.id.btn_add_it);

        mBntClose.setOnClickListener(mBtnCloseDialogListener);


    }

    View.OnClickListener mBtnCloseDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}
