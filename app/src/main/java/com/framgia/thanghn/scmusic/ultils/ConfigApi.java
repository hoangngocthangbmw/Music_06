package com.framgia.thanghn.scmusic.ultils;


import android.support.annotation.StringDef;

import com.framgia.thanghn.scmusic.BuildConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by thang on 5/9/2018.
 */

public final class ConfigApi {
    public static final String CLIENT_ID = BuildConfig.KEY;
    public static final int LIMIT = 20;
    public static final int OFFSET = 20;
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;

}
