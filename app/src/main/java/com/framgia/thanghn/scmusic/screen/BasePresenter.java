package com.framgia.thanghn.scmusic.screen;

/**
 * Created by thang on 5/2/2018.
 */

public class BasePresenter<V extends BaseView> implements BaseMvpPresenter<V> {
    protected V mView;

    public V getView() {
        return mView;
    }

    @Override
    public void setView(V view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
