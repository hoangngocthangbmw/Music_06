package com.framgia.thanghn.scmusic.screen.search;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by thang on 5/14/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Song> mListSong;
    private SearchAdapter.OnClickItemReyclerView mOnClickItemReyclerView;

    public SearchAdapter(List<Song> listSong) {
        this.mListSong = listSong;
    }

    public void setOnClickItemRecyclerView(SearchAdapter.OnClickItemReyclerView onClickItemReyclerView) {
        mOnClickItemReyclerView = onClickItemReyclerView;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_list_search, parent, false);
        return new SearchAdapter.ViewHolder(itemview, mOnClickItemReyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        holder.bindData(mListSong.get(position));
    }

    @Override
    public int getItemCount() {
        return mListSong != null ? mListSong.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView mAvatarSong;
        private TextView mTextSongName;
        private TextView mTextArtist;
        private ImageView mItemAction;
        private ConstraintLayout mConstraintLayout;
        private SearchAdapter.OnClickItemReyclerView mOnClickItemReyclerView;
        private Song mSong;

        public ViewHolder(View itemView, SearchAdapter.OnClickItemReyclerView onClickItemReyclerView) {
            super(itemView);
            mAvatarSong = itemView.findViewById(R.id.image_avatar_song);
            mTextSongName = itemView.findViewById(R.id.text_song_name);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mItemAction = itemView.findViewById(R.id.image_item_action);
            mConstraintLayout = itemView.findViewById(R.id.constrain_item);
            mOnClickItemReyclerView = onClickItemReyclerView;
            mItemAction.setOnClickListener(this);
            mConstraintLayout.setOnClickListener(this);
        }

        public void bindData(Song song) {
            if (song != null) {
                mSong = song;
                Picasso.get().load(song.getAvatarUrl()).into(mAvatarSong);
                mTextSongName.setText(song.getTitle());
                mTextArtist.setText(song.getUserName());
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_item_action:
                    showMenu(view);
                    break;
                case R.id.constrain_item:
                    if (mOnClickItemReyclerView != null) {
                        mOnClickItemReyclerView.onItemClicked(getAdapterPosition(), mSong);
                    }
                    break;
            }
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mItemAction);
            popup.inflate(R.menu.popup_menu);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            mOnClickItemReyclerView.onAddToFavoriteClicked(getAdapterPosition(), mSong);
                            break;
                        case R.id.menu_download:
                            mOnClickItemReyclerView.onDowloadSongClicked(getAdapterPosition(), mSong);
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }

    interface OnClickItemReyclerView {
        void onItemClicked(int position, Song song);

        void onAddToFavoriteClicked(int position, Song song);

        void onDowloadSongClicked(int position, Song song);
    }
}

