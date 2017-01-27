package com.example.skadush.bucket_drops.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import br.com.goncalves.pugnotification.notification.PugNotification;
import com.example.skadush.bucket_drops.ActivityMain;
import com.example.skadush.bucket_drops.R;
import com.example.skadush.bucket_drops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmResults;

public class NotificationService extends IntentService {
    public NotificationService() {
        super("NotificationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Drop> results = realm.where(Drop.class).equalTo("completed", false).findAll();


            for (Drop current : results) {
                if (isNotificationNeed(current.getAdded(), current.getWhen())) {

                    fireNotification(current);

                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private void fireNotification(Drop drop) {
        String message = getString(R.string.notif_message) + " \" " + drop.getWhat();
        PugNotification.with(this)
                .load()
                .title(R.string.notif_title)
                .message(message)
                .bigTextStyle(R.string.notif_long_message)
                .smallIcon(R.drawable.pugnotification_ic_launcher)
                .largeIcon(R.drawable.pugnotification_ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .click(ActivityMain.class)
                .simple()
                .build();
    }

    private boolean isNotificationNeed(long added, long when) {
        long now = System.currentTimeMillis();
        if (now > when) { // if the added item is passed the current time
            return false;
        } else {
            long difference90 = (long)(0.9 * (when - added));
            return now > (added + difference90);
        }


    }
}
