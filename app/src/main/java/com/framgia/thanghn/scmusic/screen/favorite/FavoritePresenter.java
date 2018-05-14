package com.framgia.thanghn.scmusic.screen.favorite;

import android.content.Context;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.FavoriteDataSource;
import com.framgia.thanghn.scmusic.data.source.FavoriteRepository;
import com.framgia.thanghn.scmusic.screen.BasePresenter;

import java.util.List;

/**
 * Created by thang on 5/2/2018.
 */

public class FavoritePresenter extends BasePresenter<FavoriteContract.View>
        implements FavoriteContract.Presenter, FavoriteDataSource.OnFetchDataListener<Song> {
    private FavoriteRepository mFavoriteRepository;

    public FavoritePresenter(FavoriteRepository repository) {
        mFavoriteRepository = repository;
    }

    @Override
    public void loadFavoriteSongs() {
        mFavoriteRepository.getSongLocal(this);
    }

    @Override
    public void deleteSong(Song song) {
        getView().getMessageDeleted( mFavoriteRepository.deleteSong(song));
    }

    @Override
    public void insertSong(Song song) {
        mFavoriteRepository.insertSong(song);
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
