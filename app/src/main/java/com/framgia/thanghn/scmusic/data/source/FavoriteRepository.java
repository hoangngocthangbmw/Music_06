package com.framgia.thanghn.scmusic.data.source;

import android.content.Context;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.local.SongsLocalDataSource;

/**
 * Created by thang on 5/12/2018.
 */

public class FavoriteRepository implements FavoriteDataSource.LocalDataSource {
    private static FavoriteRepository sFavoriteRepository;
    private FavoriteDataSource.LocalDataSource mLocalDataSource;

    private FavoriteRepository(FavoriteDataSource.LocalDataSource dataSource) {
        mLocalDataSource = dataSource;
    }

    public static FavoriteRepository getInstance(Context context) {
        if (null == sFavoriteRepository) {
            sFavoriteRepository = new FavoriteRepository(SongsLocalDataSource.getInstance(context));
        }
        return sFavoriteRepository;
    }

    @Override
    public void getSongLocal(FavoriteDataSource.OnFetchDataListener<Song> listener) {
        mLocalDataSource.getSongLocal(listener);
    }

    @Override
    public void insertSong(Song song) {
        mLocalDataSource.insertSong(song);
    }

    @Override
    public boolean deleteSong(Song song) {
        return mLocalDataSource.deleteSong(song);
    }
}
