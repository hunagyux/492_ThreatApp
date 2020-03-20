package com.example.android.githubsearchwithsqlite.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.githubsearchwithsqlite.utils.GitHubUtils;

import java.util.List;

public class NowplayingRepository implements NowPlayingAsyncTask.Callback{
    private static final String TAG = NowplayingRepository.class.getSimpleName();
    private MutableLiveData<List<NowPlayingRepo>> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;


    public NowplayingRepository() {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<List<NowPlayingRepo>> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    @Override
    public void onSearchFinished(List<NowPlayingRepo> searchResults) {
        mSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }


    public void loadSearchResults() {
            String url = GitHubUtils.buildNowPlayingURL();
            mSearchResults.setValue(null);
            Log.d(TAG, "executing search with url: " + url);
            mLoadingStatus.setValue(Status.LOADING);
            new NowPlayingAsyncTask(this).execute(url);
    }
}
