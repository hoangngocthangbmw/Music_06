package com.framgia.thanghn.scmusic.screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by thang on 5/2/2018.
 */

public abstract class BaseRecyclerViewAdapter<V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    private final Context mContext;

    protected BaseRecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }
}
