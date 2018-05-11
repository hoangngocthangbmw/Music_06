package com.framgia.thanghn.scmusic.ultils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by thang on 5/9/2018.
 */

public class TrackEntry {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({Api.COLLECTION, Api.TRACK,
            Api.ARTWORK_URL, Api.DESCRIPTION,
            Api.DOWNLOADABLE, Api.DOWNLOAD_URL,
            Api.DURATION, Api.ID, Api.LIKES_COUNT,
            Api.PLAYBACK_COUNT, Api.TITLE,
            Api.URI, Api.USER,
            Api.USERNAME, Api.AVATAR_URL})
    public @interface Api {
        String COLLECTION = "collection";
        String TRACK = "track";
        String ARTWORK_URL = "artwork_url";
        String DESCRIPTION = "description";
        String DOWNLOADABLE = "downloadable";
        String DOWNLOAD_URL = "download_url";
        String DURATION = "duration";
        String ID = "id";
        String LIKES_COUNT = "likes_count";
        String PLAYBACK_COUNT = "playback_count";
        String TITLE = "title";
        String URI = "uri";
        String USER = "user";
        String USERNAME = "username";
        String AVATAR_URL = "avatar_url";
    }
}
