package com.framgia.thanghn.scmusic.screen.download;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.framgia.thanghn.scmusic.R;

/**
 * Created by thang on 5/17/2018.
 */

public class DownloadReceiver extends BroadcastReceiver {
    private static final int ID_NOTIFI = 111;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_app)

                        .setContentText(context
                                .getString(R.string.string_download_completed));
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFI, mBuilder.build());
        Toast.makeText(context, R.string.string_download_succes, Toast.LENGTH_SHORT).show();
    }
}
