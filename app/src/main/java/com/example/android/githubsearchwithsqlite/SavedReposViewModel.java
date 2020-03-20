package com.example.android.githubsearchwithsqlite;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.githubsearchwithsqlite.data.MovieSearchRepo;
import com.example.android.githubsearchwithsqlite.data.SavedReposRepository;

import java.util.List;

public class SavedReposViewModel extends AndroidViewModel {
    private SavedReposRepository mRepository;

    public SavedReposViewModel(Application application) {
        super(application);
        mRepository = new SavedReposRepository(application);
    }

    public void insertSavedRepo(MovieSearchRepo repo) {
        mRepository.insertSavedRepo(repo);
    }

    public void deleteSavedRepo(MovieSearchRepo repo) {
        mRepository.deleteSavedRepo(repo);
    }

    public LiveData<List<MovieSearchRepo>> getAllRepos() {
        return mRepository.getAllRepos();
    }

    public LiveData<MovieSearchRepo> getRepoByName(String fullName) {
        return mRepository.getRepoByName(fullName);
    }
}
