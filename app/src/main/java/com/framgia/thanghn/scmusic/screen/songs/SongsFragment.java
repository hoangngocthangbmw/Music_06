package com.framgia.thanghn.scmusic.screen.songs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.screen.BaseFragment;
import com.framgia.thanghn.scmusic.screen.player.PlayMusicActivity;
import com.framgia.thanghn.scmusic.ultils.ConfigApi;
import com.framgia.thanghn.scmusic.ultils.Constants;
import com.framgia.thanghn.scmusic.ultils.TrackEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.thanghn.scmusic.ultils.Constants.GENRE_ALL_AUDIO;
import static com.framgia.thanghn.scmusic.ultils.Constants.GENRE_ALL_MUSIC;
import static com.framgia.thanghn.scmusic.ultils.Constants.GENRE_ALTERNATIVEROCK;
import static com.framgia.thanghn.scmusic.ultils.Constants.GENRE_AMBIENT;
import static com.framgia.thanghn.scmusic.ultils.Constants.GENRE_CLASSICAL;
import static com.framgia.thanghn.scmusic.ultils.Constants.GENRE_COUNTRY;

/**
 * Created by thang on 5/2/2018.
 */

public class SongsFragment extends BaseFragment implements AdapterView.OnItemSelectedListener,
        SongsAdapter.OnClickItemReyclerView, SongsContract.View, View.OnClickListener {
    private RecyclerView mRecycler;
    private ImageView mImageViewSearch;
    private String[] mListSpinner = {GENRE_ALL_MUSIC, GENRE_ALL_AUDIO, GENRE_ALTERNATIVEROCK,
            GENRE_AMBIENT, GENRE_CLASSICAL, GENRE_COUNTRY};
    private Spinner mSpinner;
    private SongsPresenter mSongPresenter;
    private SongsAdapter mSongsAdapter;
    private TextView mTextViewSongName;
    private ImageView mImageViewSkipPrevious, mImageViewPlay, mImageViewSkipNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        mSongPresenter = new SongsPresenter();
        mSongPresenter.setView(this);
        mSongPresenter.loadSongs(Constants.GENRE_ALL_MUSIC);
        mRecycler = getView().findViewById(R.id.recyler_home);
        mImageViewSearch = getView().findViewById(R.id.button_search_home);
        mSpinner = getView().findViewById(R.id.spinner);
        mTextViewSongName = getActivity().findViewById(R.id.text_song_name);
        mImageViewSkipPrevious = getActivity().findViewById(R.id.image_skip_previous);
        mImageViewPlay = getActivity().findViewById(R.id.image_play);
        mImageViewSkipNext = getActivity().findViewById(R.id.image_skip_next);
        mImageViewSkipNext.setOnClickListener(this);
        mImageViewPlay.setOnClickListener(this);
        mImageViewSkipPrevious.setOnClickListener(this);
        initSpinner();
        initRecyler();
    }

    private void initData() {
    }

    private void initRecyler() {
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        mRecycler.addItemDecoration(dividerItemDecoration);
    }

    private void initSpinner() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, mListSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        mSongPresenter.loadSongs(adapterView.getAdapter().getItem(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClicked(int postion) {
        Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAddToFavoriteClicked(Song song) {

    }

    @Override
    public void onDowloadSongClicked(Song song) {

    }

    @Override
    public void getSongsSuccess(List<Song> songList) {
        if (songList.size() > 0) {
            mSongsAdapter = new SongsAdapter(songList);
            mRecycler.setAdapter(mSongsAdapter);
            mSongsAdapter.setOnClickItemRecyclerView(this);
        }

    }

    @Override
    public void getSongsFailure(String error) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_skip_previous:
                break;
            case R.id.image_play:
                break;
            case R.id.image_skip_next:
                break;
        }
    }
}
