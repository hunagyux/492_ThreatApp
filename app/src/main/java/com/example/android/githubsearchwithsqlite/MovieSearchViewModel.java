package com.example.android.githubsearchwithsqlite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.githubsearchwithsqlite.data.MovieSearchRepo;
import com.example.android.githubsearchwithsqlite.data.MovieSearchRepository;
import com.example.android.githubsearchwithsqlite.data.Status;

import java.util.List;

public class MovieSearchViewModel extends ViewModel {

    private MovieSearchRepository mMovieRepository;
    private LiveData<List<MovieSearchRepo>> mMovieSearchResults;
    private LiveData<Status> mMovieLoadingStatus;
    public MovieSearchViewModel() {
        mMovieRepository = new MovieSearchRepository();
        mMovieSearchResults = mMovieRepository.getSearchResults();
        mMovieLoadingStatus = mMovieRepository.getLoadingStatus();
    }

    public void loadSearchResults(String query, String year, boolean adult_type, String language){
        mMovieRepository.loadSearchResults(query, year, adult_type, language);
    }
    public LiveData<List<MovieSearchRepo>> getSearchResults() {
        return mMovieSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mMovieLoadingStatus;
    }
}
