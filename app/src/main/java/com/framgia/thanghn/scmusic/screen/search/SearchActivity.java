package com.framgia.thanghn.scmusic.screen.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.screen.BaseAcitivity;
import com.framgia.thanghn.scmusic.screen.main.MainActivity;
import com.framgia.thanghn.scmusic.screen.player.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseAcitivity implements View.OnClickListener,
        SearchContract.View, SearchAdapter.OnClickItemReyclerView {
    private EditText mEditTextSearch;
    private Button mButtonSearch;
    private RecyclerView mRecyclerViewSearch;
    private SearchContract.Presenter mPresenter;
    private SearchAdapter mSearchAdapter;
    private ArrayList<Song> mSongList;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new SearchPresenter();
        mPresenter.setView(this);
    }

    private void initView() {
        mEditTextSearch = (EditText) findViewById(R.id.edit_text_search);
        mButtonSearch = (Button) findViewById(R.id.button_search);
        mRecyclerViewSearch = (RecyclerView) findViewById(R.id.recyler_search);
        mImageViewBack = (ImageView) findViewById(R.id.image_back);
        mImageViewBack.setOnClickListener(this);
        initReyclerView();
        mButtonSearch.setOnClickListener(this);
    }

    private void initReyclerView() {
        mRecyclerViewSearch.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewSearch.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(SearchActivity.this, layoutManager.getOrientation());
        mRecyclerViewSearch.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                mPresenter.loadSongsSearch(mEditTextSearch.getText().toString());
                break;
            case R.id.image_back:
                finish();
                break;
        }
    }

    @Override
    public void getSongsSearchSuccess(List<Song> songList) {
        if (songList.size() > 0) {
            mSongList = (ArrayList<Song>) songList;
            mSearchAdapter = new SearchAdapter(songList);
            mRecyclerViewSearch.setAdapter(mSearchAdapter);
            mSearchAdapter.setOnClickItemRecyclerView(this);
        }
    }

    @Override
    public void getSongsSearchFailure(String error) {
        Toast.makeText(SearchActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position, Song song) {
        Intent intent = new Intent(SearchActivity.this, PlayMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Song.class.getName(), mSongList);
        bundle.putInt(getResources().getString(R.string.key_position_item), position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onAddToFavoriteClicked(int position, Song song) {

    }

    @Override
    public void onDowloadSongClicked(int position, Song song) {

    }
}
