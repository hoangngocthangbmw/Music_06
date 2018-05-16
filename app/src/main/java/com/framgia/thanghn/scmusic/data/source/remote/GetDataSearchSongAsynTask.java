package com.framgia.thanghn.scmusic.data.source.remote;

import android.os.AsyncTask;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.SearchDataSource;
import com.framgia.thanghn.scmusic.ultils.ConfigApi;
import com.framgia.thanghn.scmusic.ultils.Constants;
import com.framgia.thanghn.scmusic.ultils.TrackEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thang on 5/13/2018.
 */

public class GetDataSearchSongAsynTask extends AsyncTask<String, Void, String> {
    private SearchDataSource.OnFetchDataListener<Song> mSongOnFetchDataListener;

    public GetDataSearchSongAsynTask(SearchDataSource.OnFetchDataListener listener) {
        mSongOnFetchDataListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(ConfigApi.REQUEST_METHOD);
            connection.setReadTimeout(ConfigApi.READ_TIMEOUT);
            connection.setConnectTimeout(ConfigApi.CONNECT_TIMEOUT);
            connection.setDoOutput(true);
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            String result = sb.toString();
            connection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            mSongOnFetchDataListener.onFetchDataFailure(Constants.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            mSongOnFetchDataListener.onFetchDataFailure(Constants.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (mSongOnFetchDataListener != null && data != null) {
            mSongOnFetchDataListener.onFetchDataSuccess(parseSongObject(data));
        } else {
            mSongOnFetchDataListener.onFetchDataFailure(Constants.ERROR_MESSAGE);
        }
    }

    public List<Song> parseSongObject(String data) {
        List<Song> songList = new ArrayList<Song>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectSong = (JSONObject) jsonArray.get(i);
                Song song = new Song();
                song.setArtworkUrl(objectSong.getString(TrackEntry.Api.ARTWORK_URL));
                song.setDescription(objectSong.getString(TrackEntry.Api.DESCRIPTION));
                song.setDownloadable(objectSong.getBoolean(TrackEntry.Api.DOWNLOADABLE));
                song.setDownloadUrl(objectSong.getString(TrackEntry.Api.DOWNLOAD_URL));
                song.setDuration(objectSong.getLong(TrackEntry.Api.DURATION));
                song.setId(objectSong.getInt(TrackEntry.Api.ID));
                song.setLikesCount(objectSong.getInt(TrackEntry.Api.LIKES_COUNT));
                song.setPlaybackCount(objectSong.getInt(TrackEntry.Api.PLAYBACK_COUNT));
                song.setTitle(objectSong.getString(TrackEntry.Api.TITLE));
                song.setUri(objectSong.getString(TrackEntry.Api.URI));
                JSONObject jsonObjectUser = new JSONObject(objectSong.getString(TrackEntry.Api.USER));
                song.setUserName(jsonObjectUser.getString(TrackEntry.Api.USERNAME));
                song.setAvatarUrl(jsonObjectUser.getString(TrackEntry.Api.AVATAR_URL));
                songList.add(song);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songList;
    }
}
