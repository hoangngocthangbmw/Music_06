package com.framgia.thanghn.scmusic.screen.favorite;

import android.support.annotation.NonNull;
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
 * Created by thang on 5/2/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<Song> mSongs;
    private FavoriteAdapter.OnClickItemReyclerView mOnClickItemReyclerView;

    public FavoriteAdapter(List<Song> songs) {
        this.mSongs = songs;
    }

    public void setOnClickItemRecyclerView(FavoriteAdapter.OnClickItemReyclerView onClickItemReyclerView) {
        mOnClickItemReyclerView = onClickItemReyclerView;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_list_favorite, parent, false);
        return new FavoriteAdapter.ViewHolder(itemview, mOnClickItemReyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.bindData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mAvatarSong;
        private TextView mTextSongName;
        private TextView mTextArtist;
        private FavoriteAdapter.OnClickItemReyclerView mOnClickItemReyclerView;
        private Song mSong;
        private ImageView mImageAction;

        public ViewHolder(View itemView, FavoriteAdapter.OnClickItemReyclerView onClickItemReyclerView) {
            super(itemView);
            mAvatarSong = itemView.findViewById(R.id.image_avatar_song);
            mTextSongName = itemView.findViewById(R.id.text_song_name);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mImageAction = itemView.findViewById(R.id.image_item_action);
            mOnClickItemReyclerView = onClickItemReyclerView;
            mImageAction.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnClickItemReyclerView != null) {
                        mOnClickItemReyclerView.onItemClicked(getAdapterPosition(), mSong);
                    }
                }
            });
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
            }
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mImageAction);
            popup.inflate(R.menu.popup_menu_favorite);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            mOnClickItemReyclerView.onDeleteSongClicked(getAdapterPosition(), mSong);
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }

    interface OnClickItemReyclerView {
        void onItemClicked(int postion, Song song);

        void onDeleteSongClicked(int position, Song song);
    }

}

