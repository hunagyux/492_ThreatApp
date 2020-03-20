package com.example.android.githubsearchwithsqlite.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.githubsearchwithsqlite.utils.GitHubUtils;
import com.example.android.githubsearchwithsqlite.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;

public class MovieSearchAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = " asyncTask";
    private Callback mCallback;



    public interface Callback {
        void onSearchFinished(List<MovieSearchRepo> searchResults);
    }

    public MovieSearchAsyncTask(MovieSearchAsyncTask.Callback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String searchResults = null;
        try {
            searchResults = NetworkUtils.doHttpGet(url);
            Log.d(TAG, searchResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(String s) {
        List<MovieSearchRepo> searchResults = null;
        if (s != null) {
            searchResults = GitHubUtils.parseMovieSearchResults(s);
        }
        mCallback.onSearchFinished(searchResults);
    }
}
