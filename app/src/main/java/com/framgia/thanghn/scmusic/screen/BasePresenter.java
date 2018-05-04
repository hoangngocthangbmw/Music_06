package com.framgia.thanghn.scmusic.screen;

/**
 * Created by thang on 5/2/2018.
 */

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
