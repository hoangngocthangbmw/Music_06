package com.framgia.thanghn.scmusic.screen;

/**
 * Created by thang on 5/10/2018.
 */

public interface BaseMvpPresenter<V extends BaseView> {
    void setView(V view);

    void onStart();

    void onStop();
}
