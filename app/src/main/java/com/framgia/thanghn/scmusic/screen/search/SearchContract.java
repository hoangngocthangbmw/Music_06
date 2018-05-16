package com.framgia.thanghn.scmusic.screen.search;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.screen.BaseMvpPresenter;
import com.framgia.thanghn.scmusic.screen.BaseView;

import java.util.List;

/**
 * Created by sonng266 on 13/03/2018.
 */

public interface SearchContract {
    /**
     * interface action Song screen
     */
    interface View extends BaseView {
        void getSongsSearchSuccess(List<Song> songList);

        void getSongsSearchFailure(String error);
    }

    /**
     * handle logic load data
     */
    interface Presenter extends BaseMvpPresenter<View> {
        void loadSongsSearch(String query);
    }

}
