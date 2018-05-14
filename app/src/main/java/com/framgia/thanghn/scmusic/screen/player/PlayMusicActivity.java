package com.framgia.thanghn.scmusic.screen.player;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.screen.songs.SongsFragment;

import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
    }
}
