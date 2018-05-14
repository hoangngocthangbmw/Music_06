package com.framgia.thanghn.scmusic.data.source;

import com.framgia.thanghn.scmusic.data.model.Song;

import java.util.List;

/**
 * Created by thang on 5/12/2018.
 */

public interface FavoriteDataSource {
    interface LocalDataSource {
        void getSongLocal(FavoriteDataSource.OnFetchDataListener<Song> listener);

        void insertSong(Song song);

        boolean deleteSong(Song song);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
