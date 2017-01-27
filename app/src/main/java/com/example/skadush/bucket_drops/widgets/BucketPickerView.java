package com.example.skadush.bucket_drops.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.skadush.bucket_drops.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by skadush on 27/01/17.
 */
public class BucketPickerView extends LinearLayout {

    TextView mTextDate, mTextMonth, mTextYear;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    public BucketPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public BucketPickerView(Context context) {

        super(context);
        init(context);
    }

    void init(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.bucket_picker_view, this);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MMM");

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextDate = (TextView) this.findViewById(R.id.tv_date);
        mTextMonth = (TextView) this.findViewById(R.id.tv_month);
        mTextYear = (TextView) this.findViewById(R.id.tv_year);

        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        update(date, month, year, 0, 0, 0);

    }

    void update(int date, int month, int year, int hour, int minute, int seconds) {
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, seconds);

        mTextYear.setText(year + "");
        mTextDate.setText(date + "");
        mTextMonth.setText(simpleDateFormat.format(calendar.getTime()));

    }

    public long getTime() {
        return calendar.getTimeInMillis();
    }
}
