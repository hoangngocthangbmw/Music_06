package com.framgia.thanghn.scmusic.data.source;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.remote.SongRemoteDataSource;

/**
 * Created by thang on 5/10/2018.
 */

public class SongReopository implements SongDataSource.RemoteDataSource {
    private static SongReopository sSongReopository;
    private SongDataSource.RemoteDataSource mRemoteDataSource;

    private SongReopository(SongDataSource.RemoteDataSource dataSource) {
        mRemoteDataSource = dataSource;
    }

    public static SongReopository getInstance() {
        if (null == sSongReopository) {
            sSongReopository = new SongReopository(SongRemoteDataSource.getInstance());
        }
        return sSongReopository;
    }

    @Override
    public void getSongRemote(SongDataSource.OnFetchDataListener<Song> listener, String url) {
        mRemoteDataSource.getSongRemote(listener, url);
    }
}
