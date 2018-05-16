package com.framgia.thanghn.scmusic.data.source;

import com.framgia.thanghn.scmusic.data.model.Song;

import java.util.List;

/**
 * Created by thang on 5/14/2018.
 */

public interface SearchDataSource {
    interface RemoteDataSource {
        void getSongRemote(OnFetchDataListener<Song> listener, String url);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
