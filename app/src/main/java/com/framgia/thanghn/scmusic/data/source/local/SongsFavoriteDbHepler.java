package com.framgia.thanghn.scmusic.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.FavoriteDataSource;
import com.framgia.thanghn.scmusic.ultils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thang on 2/22/2018.
 */

public class SongsFavoriteDbHepler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FavoriteSongs.db";
    public static final String TABLE_NAME = "Favorite";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_GENRES = "genres";
    public static final String COLUMN_NAME_IMAGE = "image";
    public static final String COLUMN_NAME_AUTHOR = "author";
    private static final String CREATE_NEW_TABLE = "CREATE TABLE " +
            TABLE_NAME + "("
            + COLUMN_LINK + " TEXT PRIMARY KEY,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_GENRES + " NTEXT,"
            + COLUMN_NAME_IMAGE + " TEXT,"
            + COLUMN_NAME_AUTHOR + " TEXT" + ")";
    private Context mContext;

    public SongsFavoriteDbHepler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void getAllSongs(FavoriteDataSource.OnFetchDataListener<Song> mOnFetchDataListener) {
        List<Song> songList = new ArrayList<Song>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String link = cursor.getString(cursor.getColumnIndex(COLUMN_LINK));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String image = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_IMAGE));
            String author = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_AUTHOR));
            songList.add(new Song(link, title, image, author));
            cursor.moveToNext();
        }
        cursor.close();
        if (songList.size() > 0) {
            mOnFetchDataListener.onFetchDataSuccess(songList);
        }
        db.close();
    }

    public boolean insertdb(Song song) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_LINK, song.getUri());
        values.put(COLUMN_NAME_IMAGE, song.getAvatarUrl());
        values.put(COLUMN_NAME_AUTHOR, song.getUserName());
        boolean result = db.insert(TABLE_NAME, null, values) == -1;
        return result;
    }

    public boolean delete(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_LINK + " = ?",
                new String[]{String.valueOf(song.getUri())}) > 0;
    }

}
