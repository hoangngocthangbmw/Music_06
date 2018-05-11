package com.framgia.thanghn.scmusic.data.source.remote;

import android.os.AsyncTask;

import com.framgia.thanghn.scmusic.data.model.Song;
import com.framgia.thanghn.scmusic.data.source.SongDataSource;
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
 * Created by thang on 5/6/2018.
 */

public class GetDataApiSoundCloundAsynTask extends AsyncTask<String, Void, String> {

    private SongDataSource.OnFetchDataListener<Song> mOnFetchDataListener;

    public GetDataApiSoundCloundAsynTask(SongDataSource.OnFetchDataListener listener) {
        mOnFetchDataListener = listener;
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (mOnFetchDataListener != null) {
            mOnFetchDataListener.onFetchDataSuccess(parseSongObject(data));
        }
        mOnFetchDataListener.onFetchDataFailure(Constants.ERROR_MESSAGE);
    }

    public List<Song> parseSongObject(String data) {
        List<Song> mlistSongs = new ArrayList<Song>();
        try {
            JSONObject object = new JSONObject(data);
            String collection = object.getString(TrackEntry.Api.COLLECTION);
            JSONArray arrayCollection = new JSONArray(collection);
            for (int i = 0; i < arrayCollection.length(); i++) {
                JSONObject jsonObject = arrayCollection.getJSONObject(i);
                JSONObject jsonObjectTrack =
                        new JSONObject(jsonObject.getString(TrackEntry.Api.TRACK));
                mlistSongs.add(parseSong(jsonObjectTrack));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (ArrayList<Song>) mlistSongs;
    }

    private Song parseSong(JSONObject jsonObjectTrack) throws JSONException {
        if (jsonObjectTrack != null) {
            Song song = new Song();
            song.setArtworkUrl(jsonObjectTrack.getString(TrackEntry.Api.ARTWORK_URL));
            song.setDescription(jsonObjectTrack.getString(TrackEntry.Api.DESCRIPTION));
            song.setDownloadable(jsonObjectTrack.getBoolean(TrackEntry.Api.DOWNLOADABLE));
            song.setDownloadUrl(jsonObjectTrack.getString(TrackEntry.Api.DOWNLOAD_URL));
            song.setDuration(jsonObjectTrack.getLong(TrackEntry.Api.DURATION));
            song.setId(jsonObjectTrack.getInt(TrackEntry.Api.ID));
            song.setLikesCount(jsonObjectTrack.getInt(TrackEntry.Api.LIKES_COUNT));
            song.setPlaybackCount(jsonObjectTrack.getInt(TrackEntry.Api.PLAYBACK_COUNT));
            song.setTitle(jsonObjectTrack.getString(TrackEntry.Api.TITLE));
            song.setUri(jsonObjectTrack.getString(TrackEntry.Api.URI));
            JSONObject jsonObjectUser = new JSONObject(jsonObjectTrack.getString(TrackEntry.Api.USER));
            song.setUsername(jsonObjectUser.getString(TrackEntry.Api.USERNAME));
            song.setAvatarUrl(jsonObjectUser.getString(TrackEntry.Api.AVATAR_URL));
            return song;
        }
        return null;
    }

}
