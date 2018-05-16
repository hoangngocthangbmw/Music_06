package com.framgia.thanghn.scmusic.data.source;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.remote.SearchRemoteDataSource;

/**
 * Created by thang on 5/14/2018.
 */

public class SearchRepository implements SearchDataSource.RemoteDataSource {
    private static SearchRepository sSearchRepository;
    private SearchDataSource.RemoteDataSource mRemoteDataSource;

    private SearchRepository(SearchDataSource.RemoteDataSource dataSource) {
        mRemoteDataSource = dataSource;
    }

    public static SearchRepository getInstance() {
        if (null == sSearchRepository) {
            sSearchRepository = new SearchRepository(SearchRemoteDataSource.getInstance());
        }
        return sSearchRepository;
    }

    @Override
    public void getSongRemote(SearchDataSource.OnFetchDataListener<Song> listener, String url) {
        mRemoteDataSource.getSongRemote(listener, url);
    }
}
