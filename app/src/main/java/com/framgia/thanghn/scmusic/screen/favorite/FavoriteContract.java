package com.framgia.thanghn.scmusic.screen.favorite;

import android.content.Context;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.screen.BaseMvpPresenter;
import com.framgia.thanghn.scmusic.screen.BaseView;

import java.util.List;

/**
 * Created by thang on 5/12/2018.
 */

public interface FavoriteContract {

    interface View extends BaseView {
        void getSongsSuccess(List<Song> songList);

        void getSongsFailure(String error);

        void getMessageDeleted(boolean deleted);
    }

    interface Presenter extends BaseMvpPresenter<View> {
        void loadFavoriteSongs();

        void deleteSong(Song song);

        void insertSong(Song song);
    }
}
