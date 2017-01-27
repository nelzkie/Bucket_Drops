package com.example.skadush.bucket_drops.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.skadush.bucket_drops.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by skadush on 27/01/17.
 */
public class BucketPickerView extends LinearLayout implements View.OnTouchListener {

    TextView mTextDate, mTextMonth, mTextYear;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    private int MSG_KEY =1;
    private long DELAY = 250;
    boolean mIncrement, mDecrement;
    int mActiveTextViewID;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(mIncrement){
                increment(mActiveTextViewID);
            }

            if(mDecrement){
                decrement(mActiveTextViewID);
            }

            if(mIncrement || mDecrement){
                mHandler.sendEmptyMessageDelayed(MSG_KEY,DELAY);
            }

            return true;
        }
    });



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


        mTextDate.setOnTouchListener(this);
        mTextMonth.setOnTouchListener(this);
        mTextYear.setOnTouchListener(this);

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()) {
            case R.id.tv_date:
                processEcventsFor(mTextDate, event);
                break;
            case R.id.tv_month:
                processEcventsFor(mTextMonth, event);
                break;
            case R.id.tv_year:
                processEcventsFor(mTextYear, event);
                break;
        }


        return true;
    }

    void processEcventsFor(TextView textView, MotionEvent event) {
        Drawable[] drawables = textView.getCompoundDrawables();
        if (hasDrawableTop(drawables) && hasDrawableBottom(drawables)) {
            Rect topBounds = drawables[TOP].getBounds();
            Rect bottomBounds = drawables[BOTTOM].getBounds();
            float x = event.getX();
            float y = event.getY();
            mActiveTextViewID = textView.getId();
            if (topDrawableHit(textView,topBounds.height(),x,y)) {

                if(isActionDown(event)){

                    mIncrement = true;
                    increment(textView.getId());
                    mHandler.removeMessages(MSG_KEY);
                    mHandler.sendEmptyMessageDelayed(MSG_KEY,DELAY);
                }
                if(isActionUpOrCancel(event)){
                    mIncrement = false;
                }
            } else if (bottomDrawableHit(textView,bottomBounds.height(),x,y)) {
                if(isActionDown(event)){
                    mDecrement = true;
                    decrement(textView.getId());
                    mHandler.removeMessages(MSG_KEY);
                    mHandler.sendEmptyMessageDelayed(MSG_KEY,DELAY);
                }
                if(isActionUpOrCancel(event)){
                    mDecrement = false;
                }
            } else {
                mDecrement = false;
                mIncrement = false;
            }


        }
    }

    boolean isActionDown(MotionEvent event){
        return  event.getAction() == MotionEvent.ACTION_DOWN;
    }
    boolean isActionUpOrCancel(MotionEvent event){
        return  event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ;
    }

    void increment(int id){
        switch (id) {
            case R.id.tv_date:
                calendar.add(Calendar.DATE,1);
                break;
            case R.id.tv_month:
                calendar.add(Calendar.MONTH,1);
                break;
            case R.id.tv_year:
                calendar.add(Calendar.YEAR,1);
                break;
        }

       // set(calendar);
        UpdateTextViews(calendar);
    }

    private void UpdateTextViews(Calendar calendar) {
        int date = calendar.get(Calendar.DATE);


        int year = calendar.get(Calendar.YEAR);
        mTextDate.setText(date + "");
        mTextYear.setText(year + "");
       mTextMonth.setText(simpleDateFormat.format(calendar.getTime()));
    }

    void decrement(int id){
        switch (id) {
            case R.id.tv_date:
                calendar.add(Calendar.DATE,-1);
                break;
            case R.id.tv_month:
                calendar.add(Calendar.MONTH,-1);
                break;
            case R.id.tv_year:
                calendar.add(Calendar.YEAR,-1);
                break;
        }
        UpdateTextViews(calendar);

    }

    private boolean topDrawableHit(TextView textView,int drawableHeight ,float x, float y) {
        int xmin = textView.getPaddingLeft(); // padding left of the textview
        int xmax = textView.getWidth() - textView.getPaddingRight();  // width of textview minus padding right of the textview
        int ymin = textView.getPaddingTop(); // the padding top of the textview
        int ymax = textView.getPaddingTop() + drawableHeight; // padding top + the drawable height

        return x > xmin && x < xmax && y > ymin && y < ymax;
    }

    private boolean bottomDrawableHit( TextView textView,int drawableHeight, float x, float y) {
        int xmin = textView.getPaddingLeft(); // padding left of the textview
        int xmax = textView.getWidth() - textView.getPaddingRight();  // width of textview minus padding right of the textview
        int ymax = textView.getHeight() - textView.getPaddingBottom();
        int ymin = ymax - drawableHeight;

        return x > xmin && x < xmax && y > ymin && y < ymax;
    }

    boolean hasDrawableTop(Drawable[] drawables) {
        return drawables[TOP] != null;
    }

    boolean hasDrawableBottom(Drawable[] drawables) {
        return drawables[BOTTOM] != null;
    }

}
