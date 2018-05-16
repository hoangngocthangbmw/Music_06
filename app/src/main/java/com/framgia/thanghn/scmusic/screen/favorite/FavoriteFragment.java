package com.framgia.thanghn.scmusic.screen.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.FavoriteRepository;
import com.framgia.thanghn.scmusic.screen.BaseFragment;

import java.util.List;

/**
 * Created by thang on 5/2/2018.
 */

public class FavoriteFragment extends BaseFragment implements FavoriteContract.View,
        FavoriteAdapter.OnClickItemReyclerView {
    private FavoriteContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mFavoriteAdapter;
    private List<Song> mSongs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new FavoritePresenter(FavoriteRepository.getInstance(getActivity()));
        mPresenter.setView(this);
        mPresenter.loadFavoriteSongs();
    }

    private void initView() {
        mRecyclerView = getView().findViewById(R.id.recyler_favorite);
        initRecylerView();
    }

    private void initRecylerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void getSongsSuccess(List<Song> songList) {
        if (songList.size() > 0) {
            mSongs = songList;
            mFavoriteAdapter = new FavoriteAdapter(songList);
            mRecyclerView.setAdapter(mFavoriteAdapter);
            mFavoriteAdapter.setOnClickItemRecyclerView(this);
        }
    }

    @Override
    public void getSongsFailure(String error) {
    }

    @Override
    public void getMessageDeleted(boolean deleted) {
        Toast.makeText(getActivity(), getResources().getString(R.string.action_delete), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int postion, Song song) {
        Toast.makeText(getActivity(), song.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSongClicked(int position, Song song) {
        Toast.makeText(getActivity(), getResources().getString(R.string.action_delete), Toast.LENGTH_SHORT).show();
        mPresenter.deleteSong(song);
        mSongs.remove(position);
        mFavoriteAdapter.notifyItemRemoved(position);
    }
}
