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
import com.example.skadush.bucket_drops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

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
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBntClose = (ImageButton) view.findViewById(R.id.btnClose);
        mInputPicker = (DatePicker) view.findViewById(R.id.bpv_date);
        mInputWhat = (EditText) view.findViewById(R.id.et_drop);
        mBtnAdd = (Button) view.findViewById(R.id.btn_add_it);

        mBntClose.setOnClickListener(mButtonListener);
        mBtnAdd.setOnClickListener(mButtonListener);


    }

    View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_add_it:
                    addAction();
                    break;

            }
            dismiss(); // close the dialog
        }
    };

    // TODO add a date
    private void addAction() {
        String what = mInputWhat.getText().toString();
        long currentTime = System.currentTimeMillis();

        Realm.init(getContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
        Realm realm = Realm.getDefaultInstance();
        Drop drop = new Drop(what, currentTime, 0, false);

        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();

    }


}
