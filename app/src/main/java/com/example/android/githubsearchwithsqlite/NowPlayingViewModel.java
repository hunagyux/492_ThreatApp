package com.example.android.githubsearchwithsqlite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.githubsearchwithsqlite.data.NowPlayingRepo;
import com.example.android.githubsearchwithsqlite.data.NowplayingRepository;
import com.example.android.githubsearchwithsqlite.data.Status;

import java.util.List;

public class NowPlayingViewModel extends ViewModel {
    private NowplayingRepository mRepository;
    private LiveData<List<NowPlayingRepo>> mSearchResults;
    private LiveData<Status> mLoadingStatus;

    public NowPlayingViewModel() {
        mRepository = new NowplayingRepository();
        mSearchResults = mRepository.getSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();


    }

    public void loadSearchResults() {
        mRepository.loadSearchResults();
    }

    public LiveData<List<NowPlayingRepo>> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }


}
