package com.framgia.thanghn.scmusic.data.source;

import com.framgia.thanghn.scmusic.data.model.Song;

import java.util.List;

/**
 * Created by thang on 5/10/2018.
 */

public interface SongDataSource {

    interface RemoteDataSource {
        void getSongRemote(OnFetchDataListener<Song> listener,String url);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
