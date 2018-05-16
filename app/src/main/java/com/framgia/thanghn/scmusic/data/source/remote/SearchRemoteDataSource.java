package com.framgia.thanghn.scmusic.data.source.remote;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.SearchDataSource;

/**
 * Created by thang on 5/14/2018.
 */

public class SearchRemoteDataSource implements SearchDataSource.RemoteDataSource {
    private static SearchRemoteDataSource sSearchRemoteDataSource;

    private SearchRemoteDataSource() {

    }

    public static SearchRemoteDataSource getInstance() {
        if (null == sSearchRemoteDataSource) {
            sSearchRemoteDataSource = new SearchRemoteDataSource();
        }
        return sSearchRemoteDataSource;
    }

    @Override
    public void getSongRemote(SearchDataSource.OnFetchDataListener<Song> listener, String url) {
        new GetDataSearchSongAsynTask(listener).execute(url);
    }
}
