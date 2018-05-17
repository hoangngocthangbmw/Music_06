package com.framgia.thanghn.scmusic.screen.player;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.service.MusicService;
import com.framgia.thanghn.scmusic.ultils.Constants;
import com.framgia.thanghn.scmusic.ultils.StringUtil;
import com.framgia.thanghn.scmusic.ultils.TrackEntry;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {

    private static int DEFAULT_DELAY = 1000;
    public static final String KEY_POSTION = "position";
    public static final String KEY_LIST = "list";
    private static final String TIME_FORMAT = "mm:ss";
    private static final int PERCENT=100;
    private ImageView mImageViewBack, mImageViewDownload,
            mImageViewSong, mImageViewShuffle,
            mImageViewPrevious, mImageViewState,
            mImageViewNext, mImageViewRepeat;
    private TextView mTextViewTitle, mTextViewCurrenTime,
            mTextViewDuration;
    private SeekBar mSeekBar;
    private MusicService mMusicService;
    private boolean isBound;
    private Handler mHandler;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mBroadcastReceiver;

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initView();
        receiveStateMedia();
    }

    private String replaceUrlImage(String url) {
        return !TextUtils.isEmpty(url) ?
                url.replace(TrackEntry.Api.LARGE_IMAGE_SIZE, TrackEntry.Api.BETTER_IMAGE_SIZE) : "";
    }

    private void initView() {
        mImageViewBack = (ImageView) findViewById(R.id.image_back);
        mImageViewDownload = (ImageView) findViewById(R.id.image_download);
        mImageViewSong = (ImageView) findViewById(R.id.image_song);
        mImageViewShuffle = (ImageView) findViewById(R.id.image_shuffle);
        mImageViewPrevious = (ImageView) findViewById(R.id.image_previous);
        mImageViewState = (ImageView) findViewById(R.id.image_state);
        mImageViewNext = (ImageView) findViewById(R.id.image_next);
        mImageViewRepeat = (ImageView) findViewById(R.id.image_repeat);
        mTextViewTitle = (TextView) findViewById(R.id.text_title);
        mTextViewCurrenTime = (TextView) findViewById(R.id.text_current_time);
        mTextViewDuration = (TextView) findViewById(R.id.text_duration);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        mHandler = new Handler();
        updateUI();
        mImageViewBack.setOnClickListener(this);
        mImageViewPrevious.setOnClickListener(this);
        mImageViewState.setOnClickListener(this);
        mImageViewNext.setOnClickListener(this);
        mImageViewRepeat.setOnClickListener(this);
        mImageViewShuffle.setOnClickListener(this);
        mImageViewDownload.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekChange);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_download:
                break;
            case R.id.image_shuffle:
                break;
            case R.id.image_previous:
                mMusicService.previous();
                break;
            case R.id.image_state:
                if (mMusicService.isPlay()) {
                    mMusicService.pause();
                } else {
                    mMusicService.resume();
                }
                break;
            case R.id.image_next:
                mMusicService.next();
                break;
            case R.id.image_repeat:
                break;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isBound = true;
            MusicService.MyBinder myBinder = (MusicService.MyBinder) iBinder;
            mMusicService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBroadcastReceiver, mIntentFilter);
        Intent intent = new Intent(PlayMusicActivity.this, MusicService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (isBound) {
            unbindService(mServiceConnection);
            isBound = false;
        }
        super.onStop();
        removeCallbacks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMusicService != null) {
            Song song = mMusicService.getSong();
            mTextViewTitle.setText(song.getTitle());
            mTextViewDuration.setText(StringUtil
                    .parseMilliSecondsToTimer(song.getDuration()));
            mTextViewCurrenTime.setText(getResources().getString(R.string.value_curren_time));
            Glide.with(PlayMusicActivity.this)
                    .load(replaceUrlImage(song.getAvatarUrl()))
                    .into(mImageViewSong);
        }
        updateUI();
    }

    private void updateUI() {
        mHandler.postDelayed(mTimeCounter, DEFAULT_DELAY);
    }

    private Runnable mTimeCounter = new Runnable() {
        @Override
        public void run() {
            if (!mTextViewTitle.getText().toString().equals(mMusicService.getTitleSongPlaying())) {
                mTextViewDuration.setText(convertToTime(mMusicService.getDuration()));
                mTextViewCurrenTime.setText(getResources().getString(R.string.value_curren_time));
                Glide.with(PlayMusicActivity.this).load(mMusicService.getImageUriSongPlaying()).into(mImageViewSong);
                mTextViewTitle.setText(mMusicService.getTitleSongPlaying());
            }
            if (mMusicService.isPlay()) {
                long currentPercent = 100 * mMusicService.getCurrentDuration() / mMusicService.getDuration();
                mSeekBar.setProgress((int) currentPercent);
                mTextViewCurrenTime.setText(convertToTime(mMusicService.getCurrentDuration()));
                mTextViewDuration.setText(convertToTime(mMusicService.getDuration()));
            }
            updateUI();
        }
    };

    private String convertToTime(long duration) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        String time = sdf.format(duration);
        return time;
    }

    private void removeCallbacks() {
        mHandler.removeCallbacks(mTimeCounter);
    }

    private SeekBar.OnSeekBarChangeListener mOnSeekChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int percent = seekBar.getProgress();
            int currentSeekBarTo = percent * (int) mMusicService.getDuration() / PERCENT;
            mMusicService.seekTo(currentSeekBarTo);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
    };

    private void receiveStateMedia() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Constants.ACTION_STATE_MEDIA);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) {
                    return;
                }
                if (intent.getAction().equals(Constants.ACTION_STATE_MEDIA)) {
                    if (intent.getBooleanExtra(Constants.EXTRA_STATE_MEDIA,
                            false)) {
                        mImageViewState.setImageResource(R.drawable.ic_pause_circle_filled);
                    } else {
                        mImageViewState.setImageResource(R.drawable.ic_play_circle_filled);
                    }
                }
            }
        };
    }
}
