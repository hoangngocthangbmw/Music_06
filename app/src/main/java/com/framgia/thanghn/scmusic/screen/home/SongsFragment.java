package com.framgia.thanghn.scmusic.screen.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.screen.BaseFragment;

/**
 * Created by thang on 5/2/2018.
 */

public class SongsFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ImageView mImageViewSearch;

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

    private void initData() {
    }

    private void initView() {
        mRecycler = getView().findViewById(R.id.recyler_home);
        mImageViewSearch = getView().findViewById(R.id.button_search_home);
    }
}
