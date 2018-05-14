package com.framgia.thanghn.scmusic.data.source.remote;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.SongDataSource;

/**
 * Created by thang on 5/10/2018.
 */

public class SongRemoteDataSource implements SongDataSource.RemoteDataSource {
    private static SongRemoteDataSource sSongRemoteDataSource;

    private SongRemoteDataSource() {

    }

    public static SongRemoteDataSource getInstance() {
        if (null == sSongRemoteDataSource) {
            sSongRemoteDataSource = new SongRemoteDataSource();
        }
        return sSongRemoteDataSource;
    }

    @Override
    public void getSongRemote(SongDataSource.OnFetchDataListener<Song> listener, String url) {
        new GetDataApiSoundCloundAsynTask(listener).execute(url);
    }
}
