package com.framgia.thanghn.scmusic.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.screen.BaseAcitivity;
import com.framgia.thanghn.scmusic.screen.download.DownloadFragment;
import com.framgia.thanghn.scmusic.screen.favorite.FavoriteFragment;
import com.framgia.thanghn.scmusic.screen.songs.SongsFragment;
import com.framgia.thanghn.scmusic.ultils.SystemNavigationBarUtils;

/**
 * Created by thang on 5/2/2018.
 */

public class MainActivity extends BaseAcitivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private SongsFragment mSongsFragment;
    private DownloadFragment mDownloadFragment;
    private FavoriteFragment mFavoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHomeFragment();
        initBottomBar();
    }

    private void initHomeFragment() {
        if (mSongsFragment == null) {
            mSongsFragment = new SongsFragment();
        }
        replaceFragment(mSongsFragment);
    }

    private void initBottomBar() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_view);
        navigation.setOnNavigationItemSelectedListener(this);
        SystemNavigationBarUtils.disableShiftMode(navigation);
    }

    public void replaceFragment(Fragment fragment) {
        if (null == fragment)
            return;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                replaceFragment(new SongsFragment());
                return true;
            case R.id.navigation_download:
                replaceFragment(new DownloadFragment());
                return true;
            case R.id.navigation_favorite:
                replaceFragment(new FavoriteFragment());
                return true;
            case R.id.navigation_menu:
                return true;
        }
        return false;
    }
}
