package com.example.android.githubsearchwithsqlite.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.githubsearchwithsqlite.utils.GitHubUtils;

import java.util.List;

public class MovieSearchRepository implements MovieSearchAsyncTask.Callback{
    private static final String TAG = MovieSearchRepository.class.getSimpleName();
    private MutableLiveData<List<MovieSearchRepo>> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;


    public MovieSearchRepository() {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<List<MovieSearchRepo>> getSearchResults() {
        return mSearchResults;
    }
    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    @Override
    public void onSearchFinished(List<MovieSearchRepo> searchResults) {
        mSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }


    public void loadSearchResults(String query, String year, boolean adult_type, String language) {
            String url = GitHubUtils.buildMovieSearchURL(query, year, adult_type, language);
            mSearchResults.setValue(null);
            Log.d(TAG, "executing movie search with url: " + url);
            mLoadingStatus.setValue(Status.LOADING);
            new MovieSearchAsyncTask(this).execute(url);
    }
}
