package com.framgia.thanghn.scmusic.screen.download;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.ultils.Constants;

import java.io.File;

/**
 * Created by thang on 5/17/2018.
 */

public class DownLoadManager {

    public static final String DIRECTORYNAME = "MyMusic";
    private static DownLoadManager sDownloadManager;
    private static DownloadReceiver mDownloadReceiver;

    private DownLoadManager() {
    }

    @SuppressLint("WrongConstant")
    public static DownLoadManager getInstance() {
        if (null == sDownloadManager) {
            sDownloadManager = new DownLoadManager();
            createFolder();
        }
        return sDownloadManager;
    }

    public static void registerReceiver(Context context) {
        mDownloadReceiver = new DownloadReceiver();
        context.registerReceiver(mDownloadReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static void unregisterReceiver(Context context) {
        if (mDownloadReceiver != null&&mDownloadReceiver.isOrderedBroadcast()) {
            context.unregisterReceiver(mDownloadReceiver);
        }
    }

    public static void requestDownload(Context context, Song song) {
        String url = Constants.BASE_URL;
        DownloadManager downloadManager = (DownloadManager)
                context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(song.getUserName());
        request.setDescription(song.getTitle());
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(getRoot(), song.getTitle() + "." + "mp3");
        downloadManager.enqueue(request);
    }

    private static File createFolder() {
        File filePath = null;
        try {
            filePath = new File(getRoot());
            if (!filePath.exists()) {
                if (!filePath.mkdirs())
                    Log.e("ImageSaverUtilsUtils", "Directory not created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static String getRoot() {
        return new StringBuilder(Environment.DIRECTORY_DOWNLOADS)
                .append("/").append(DIRECTORYNAME).append("/").toString();
    }
}


