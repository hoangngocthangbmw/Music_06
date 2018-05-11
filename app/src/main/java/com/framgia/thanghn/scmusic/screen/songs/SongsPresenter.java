package com.framgia.thanghn.scmusic.screen.songs;

import com.framgia.thanghn.scmusic.BuildConfig;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.SongDataSource;
import com.framgia.thanghn.scmusic.data.source.SongReopository;
import com.framgia.thanghn.scmusic.screen.BasePresenter;
import com.framgia.thanghn.scmusic.ultils.ConfigApi;
import com.framgia.thanghn.scmusic.ultils.Constants;

import java.util.List;

/**
 * Created by thang on 5/2/2018.
 */

public class SongsPresenter extends BasePresenter<SongsContract.View>
        implements SongsContract.Presenter, SongDataSource.OnFetchDataListener<Song> {
    private SongReopository mSongReopository;

    public SongsPresenter() {
        mSongReopository = SongReopository.getInstance();
    }


    @Override
    public void loadSongs(String genres) {
        String url = Constants.BASE_URL
                + Constants.PARA_MUSIC_GENRE
                + genres
                + Constants.CLIENT_ID
                + BuildConfig.KEY
                + Constants.PARA_LIMIT
                + ConfigApi.LIMIT
                + Constants.PARA_OFFSET
                + ConfigApi.OFFSET;
        mSongReopository.getSongRemote(this,url );
    }

    @Override
    public void onFetchDataSuccess(List<Song> data) {
        if (data != null) {
            getView().getSongsSuccess(data);
        }
    }

    @Override
    public void onFetchDataFailure(String error) {
        getView().getSongsFailure(error);
    }
}
