package com.framgia.thanghn.scmusic.data.source.local;

import android.content.Context;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.FavoriteDataSource;
import com.framgia.thanghn.scmusic.data.source.SongDataSource;

/**
 * Created by thang on 5/12/2018.
 */

public class SongsLocalDataSource implements FavoriteDataSource.LocalDataSource,
        SongDataSource.LocalDataSource {
    private static SongsLocalDataSource sSongsLocalDataSource;
    private SongsFavoriteDbHepler mSongsFavoriteDbHepler;

    private SongsLocalDataSource(Context context) {
        mSongsFavoriteDbHepler = new SongsFavoriteDbHepler(context);
    }

    public static SongsLocalDataSource getInstance(Context context) {
        if (null == sSongsLocalDataSource) {
            sSongsLocalDataSource = new SongsLocalDataSource(context);
        }
        return sSongsLocalDataSource;
    }

    @Override
    public void getSongLocal(FavoriteDataSource.OnFetchDataListener<Song> listener) {
        mSongsFavoriteDbHepler.getAllSongs(listener);
    }

    @Override
    public void insertSong(Song song) {
        mSongsFavoriteDbHepler.insertdb(song);
    }

    @Override
    public boolean deleteSong(Song song) {
        return mSongsFavoriteDbHepler.delete(song);
    }

}
