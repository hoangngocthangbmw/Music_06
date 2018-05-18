package com.framgia.thanghn.scmusic.ultils;

import com.framgia.thanghn.scmusic.BuildConfig;

/**
 * Created by thang on 5/2/2018.
 */

public final class Constants {
    public static final String BASE_URL = "https://api-v2.soundcloud.com/";
    public static final String PARA_MUSIC_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String GENRE_ALL_MUSIC = "all-music";
    public static final String GENRE_ALL_AUDIO = "all-audio";
    public static final String GENRE_ALTERNATIVEROCK = "alternativerock";
    public static final String GENRE_AMBIENT = "ambient";
    public static final String GENRE_CLASSICAL = "classical";
    public static final String GENRE_COUNTRY = "country";
    public static final String PARA_OFFSET = "&offset=";
    public static final String PARA_LIMIT = "&limit=";
    public static final String CLIENT_ID = "&client_id=";
    public static final String ADDED = "Added";
    public static final String DELETE = "Delete";
    public static final String STREAM = "/stream?client_id=";
    public static final String QUERY = "&q=";
    public static final String BASE_URL_SEARCH = "http://api.soundcloud.com/";
    public static final String ACTION_STATE_MEDIA = "action_state_media";
    public static final String EXTRA_STATE_MEDIA = "extra_state_media";

    private Constants() {
        //no op
    }
}
