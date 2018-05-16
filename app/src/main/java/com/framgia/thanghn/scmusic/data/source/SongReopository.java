package com.framgia.thanghn.scmusic.data.source;

import android.content.Context;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.local.SongsLocalDataSource;
import com.framgia.thanghn.scmusic.data.source.remote.SongRemoteDataSource;

/**
 * Created by thang on 5/10/2018.
 */

public class SongReopository implements SongDataSource.RemoteDataSource
        , SongDataSource.LocalDataSource {
    private static SongReopository sSongReopository;
    private SongDataSource.RemoteDataSource mRemoteDataSource;
    private SongDataSource.LocalDataSource mLocalDataSource;

    private SongReopository(SongDataSource.RemoteDataSource dataSource,
                            SongDataSource.LocalDataSource localDataSource) {
        mRemoteDataSource = dataSource;
        mLocalDataSource = localDataSource;
    }

    public static SongReopository getInstance(Context context) {
        if (null == sSongReopository) {
            sSongReopository = new SongReopository(SongRemoteDataSource.getInstance(),
                    SongsLocalDataSource.getInstance(context));
        }
        return sSongReopository;
    }

    @Override
    public void getSongRemote(SongDataSource.OnFetchDataListener<Song> listener, String url) {
        mRemoteDataSource.getSongRemote(listener, url);
    }

    @Override
    public void insertSong(Song song) {
        mLocalDataSource.insertSong(song);
    }
}
