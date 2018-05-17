package com.framgia.thanghn.scmusic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.framgia.thanghn.scmusic.BuildConfig;
import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.screen.player.PlayMusicActivity;
import com.framgia.thanghn.scmusic.ultils.Constants;
import com.framgia.thanghn.scmusic.ultils.TrackEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.framgia.thanghn.scmusic.screen.player.PlayMusicActivity.KEY_LIST;
import static com.framgia.thanghn.scmusic.screen.player.PlayMusicActivity.KEY_POSTION;

/**
 * Created by thang on 5/11/2018.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {
    private MediaPlayer mMediaPlayer;
    public static final String ACTION_PLAY = "ACTION_PLAY";
    private static final int DEFAULT_POSITION_START = 0;
    private ArrayList<Song> mSongs;
    private int mPosition;
    private Intent mIntentBroadcast;

    private RemoteViews mRemoteViews;
    private static final String
            ACTION_CHANGE_MEDIA_NEXT = "ACTION_CHANGE_MEDIA_NEXT";
    private static final String
            ACTION_CHANGE_MEDIA_PREVIOUS = "ACTION_CHANGE_MEDIA_PREVIOUS";
    private static final String
            ACTION_CHANGE_MEDIA_STATE = "ACTION_CHANGE_MEDIA_STATE";
    private static final String
            ACTION_MEDIA_CLEAR = "ACTION_MEDIA_CLEAR";
    private Notification mNotification;
    public static final int ID_NOTIFICATION = 183;
    public static int REQUEST_CODE_NEXT = 0;
    public static int REQUEST_CODE_PAUSE = 1;
    public static int REQUEST_CODE_PREVIOUS = 2;
    public static int REQUEST_CODE_CLEAR = 3;

    public static Intent getInstance(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(ACTION_PLAY);
        intent.putParcelableArrayListExtra(KEY_LIST, (ArrayList<? extends Parcelable>) songs);
        intent.putExtra(KEY_POSTION, position);
        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(mOnPrepare);
        mIntentBroadcast = new Intent();
        mIntentBroadcast.setAction(Constants.ACTION_STATE_MEDIA);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_PLAY:
                    mSongs = intent.getExtras().getParcelableArrayList(KEY_LIST);
                    Bundle bundle = intent.getExtras();
                    mPosition = bundle.getInt(KEY_POSTION);
                    play(mPosition);
                    break;
                case ACTION_CHANGE_MEDIA_STATE:
                    if (mMediaPlayer.isPlaying()) {
                        pause();
                    } else {
                        resume();
                    }
                    break;
                case ACTION_CHANGE_MEDIA_NEXT:
                    next();
                    break;
                case ACTION_CHANGE_MEDIA_PREVIOUS:
                    previous();
                    break;
                case ACTION_MEDIA_CLEAR:
                    stop();
                    stopSelf();
                    stopForeground(true);
                    break;
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public boolean isPlay() {
        return mMediaPlayer.isPlaying();
    }

    public void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mIntentBroadcast.putExtra(Constants.EXTRA_STATE_MEDIA, false);
            sendBroadcast(mIntentBroadcast);
        }
        updateNotification();
    }

    public void start() {
        mMediaPlayer.start();
        mIntentBroadcast.putExtra(Constants.EXTRA_STATE_MEDIA, true);
        sendBroadcast(mIntentBroadcast);
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mIntentBroadcast.putExtra(Constants.EXTRA_STATE_MEDIA, false);
            sendBroadcast(mIntentBroadcast);
        }
        updateNotification();
    }

    public void next() {
        if (mPosition < mSongs.size() - 1) {
            mPosition++;
        } else {
            mPosition = DEFAULT_POSITION_START;
        }
        play(mPosition);
    }

    public void previous() {
        if (mPosition > DEFAULT_POSITION_START) {
            mPosition--;
        } else {
            mPosition = DEFAULT_POSITION_START;
        }
        play(mPosition);
    }

    public void resume() {
        if (mMediaPlayer.isPlaying()) {
            return;
        }
        start();
        updateNotification();
    }

    public void play(int position) {
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mSongs.get(position).getUri() + "/stream?client_id=" + BuildConfig.KEY);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Logger.getLogger(e.toString());
        }
    }

    public int getPosition() {
        return mPosition;
    }

    public String getTitleSongPlaying() {
        return mSongs.get(mPosition).getTitle();
    }

    public String getImageUriSongPlaying() {
        return replaceUrlImage(mSongs.get(mPosition).getAvatarUrl());
    }

    public String replaceUrlImage(String url) {
        return !TextUtils.isEmpty(url) ?
                url.replace(TrackEntry.Api.LARGE_IMAGE_SIZE, TrackEntry.Api.BETTER_IMAGE_SIZE) : "";
    }

    public long getDuration() {
        return mMediaPlayer.getDuration();
    }

    public long getCurrentDuration() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void seekTo(int index) {
        mMediaPlayer.seekTo(index);
    }

    private void notifiNext() {
        Intent intentActionNext = new Intent();
        intentActionNext.setAction(ACTION_CHANGE_MEDIA_NEXT);
        intentActionNext.setClass(getApplicationContext(), MusicService.class);
        PendingIntent peServiceNext =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEXT,
                        intentActionNext, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_next, peServiceNext);
    }

    private void notifiPrev() {
        Intent intentActionPrev = new Intent();
        intentActionPrev.setAction(ACTION_CHANGE_MEDIA_PREVIOUS);
        intentActionPrev.setClass(getApplicationContext(), MusicService.class);
        PendingIntent peServicePre =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_PREVIOUS,
                        intentActionPrev, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_pre, peServicePre);
    }

    private void notifiPause() {
        Intent intentActionPause = new Intent();
        intentActionPause.setAction(ACTION_CHANGE_MEDIA_STATE);
        intentActionPause.setClass(getApplicationContext(), MusicService.class);
        PendingIntent peServicePause =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_PAUSE,
                        intentActionPause, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_pause, peServicePause);
    }

    private void notifiCancle() {
        Intent intentActionClear = new Intent();
        intentActionClear.setAction(ACTION_MEDIA_CLEAR);
        intentActionClear.setClass(this, MusicService.class);
        PendingIntent peServiceClear =
                PendingIntent.getService(this, REQUEST_CODE_CLEAR, intentActionClear,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_cancel, peServiceClear);
    }

    private void createNotification(String title) {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        mRemoteViews.setTextViewText(R.id.text_title_song_notification, title);
        notifiNext();
        notifiPrev();
        notifiPause();
        notifiCancle();
        Intent intent = new Intent(this, PlayMusicActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivities(this, (int) System.currentTimeMillis(),
                        new Intent[]{intent}, 0);
        Notification.Builder notificationBuilder =
                new Notification.Builder(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = notificationBuilder.setSmallIcon(R.mipmap.ic_app)
                    .setContentIntent(pendingIntent)
                    .setContent(mRemoteViews)
                    .setDefaults(Notification.FLAG_NO_CLEAR)
                    .build();
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    private void updateNotification() {
        mRemoteViews.setTextViewText(R.id.text_title_song_notification, mSongs.get(mPosition).getTitle());
        if (isPlay()) {
            mRemoteViews.setImageViewResource(R.id.image_pause, R.drawable.ic_pause_circle_filled);
        } else {
            mRemoteViews.setImageViewResource(R.id.image_pause, R.drawable.ic_play_circle_filled);
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    private MediaPlayer.OnPreparedListener mOnPrepare = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            start();
            createNotification(mSongs.get(mPosition).getTitle());
        }
    };

    public Song getSong() {
        return mSongs.get(mPosition);
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
