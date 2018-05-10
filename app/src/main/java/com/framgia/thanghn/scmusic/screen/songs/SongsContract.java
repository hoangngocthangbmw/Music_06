package com.framgia.thanghn.scmusic.screen.songs;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.screen.BaseMvpPresenter;
import com.framgia.thanghn.scmusic.screen.BasePresenter;
import com.framgia.thanghn.scmusic.screen.BaseView;

import java.util.List;

/**
 * Created by thang on 5/10/2018.
 */

public interface SongsContract {
    /**
     * interface action Song screen
     */
    interface View extends BaseView {
        void getSongsSuccess(List<Song> songList);

        void getSongsFailure(String error);
    }

    /**
     * handle logic load data
     */
    interface Presenter extends BaseMvpPresenter<View> {
        void loadSongs(String genres);
    }
}
