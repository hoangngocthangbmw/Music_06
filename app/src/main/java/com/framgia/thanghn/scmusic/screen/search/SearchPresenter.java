package com.framgia.thanghn.scmusic.screen.search;

import com.framgia.thanghn.scmusic.BuildConfig;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.SearchDataSource;
import com.framgia.thanghn.scmusic.data.source.SearchRepository;
import com.framgia.thanghn.scmusic.screen.BasePresenter;
import com.framgia.thanghn.scmusic.ultils.Constants;

import java.util.List;

/**
 * Created by sonng266 on 13/03/2018.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View>
        implements SearchContract.Presenter, SearchDataSource.OnFetchDataListener<Song> {
    private SearchRepository mSearchRepository;

    public SearchPresenter() {
        mSearchRepository = SearchRepository.getInstance();
    }

    @Override
    public void loadSongsSearch(String query) {

        mSearchRepository = SearchRepository.getInstance();
        if (!query.equals("")) {
            String url = Constants.BASE_URL_SEARCH +
                    "tracks?filter=public&limit=20" +
                    Constants.CLIENT_ID +
                    BuildConfig.KEY +
                    Constants.QUERY +
                    query;

            mSearchRepository.getSongRemote(this, url);
        }
    }

    @Override
    public void onFetchDataSuccess(List<Song> data) {
        if (data != null) {
            getView().getSongsSearchSuccess(data);
        }
    }

    @Override
    public void onFetchDataFailure(String error) {
        getView().getSongsSearchFailure(error);
    }

}
