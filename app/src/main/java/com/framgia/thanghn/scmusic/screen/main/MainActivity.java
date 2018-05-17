package com.framgia.thanghn.scmusic.screen.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.screen.BaseAcitivity;
import com.framgia.thanghn.scmusic.screen.download.DownloadFragment;
import com.framgia.thanghn.scmusic.screen.favorite.FavoriteFragment;
import com.framgia.thanghn.scmusic.screen.menu.MenuFragment;
import com.framgia.thanghn.scmusic.screen.songs.SongsFragment;
import com.framgia.thanghn.scmusic.ultils.SystemNavigationBarUtils;

/**
 * Created by thang on 5/2/2018.
 */

public class MainActivity extends BaseAcitivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private SongsFragment mSongsFragment;
    private static final int PERMISSION_REQUEST_CODE = 1996;
    private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionsResult();
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
            case R.id.navigation_favorite:
                replaceFragment(new FavoriteFragment());
                return true;
            case R.id.navigation_menu:
                replaceFragment(new MenuFragment());
                return true;
        }
        return false;
    }

    private void checkPermissionsResult() {
        if (ActivityCompat.checkSelfPermission(this, PERMISSION_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                }, PERMISSION_REQUEST_CODE);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
            }
            break;
        }
    }
}
