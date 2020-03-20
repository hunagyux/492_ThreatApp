package com.example.android.githubsearchwithsqlite.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedReposRepository {
    private SavedReposDao mDAO;

    public SavedReposRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDAO = db.savedReposDao();
    }

    public void insertSavedRepo(MovieSearchRepo repo) {
        new InsertAsyncTask(mDAO).execute(repo);
    }

    public void deleteSavedRepo(MovieSearchRepo repo) {
        new DeleteAsyncTask(mDAO).execute(repo);
    }

    public LiveData<List<MovieSearchRepo>> getAllRepos() {
        return mDAO.getAllRepos();
    }

    public LiveData<MovieSearchRepo> getRepoByName(String title_name) {
        return mDAO.getRepoByName(title_name);
    }

    private static class InsertAsyncTask extends AsyncTask<MovieSearchRepo, Void, Void> {
        private SavedReposDao mAsyncTaskDAO;
        InsertAsyncTask(SavedReposDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(MovieSearchRepo... movieSearchRepos) {
            mAsyncTaskDAO.insert(movieSearchRepos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<MovieSearchRepo, Void, Void> {
        private SavedReposDao mAsyncTaskDAO;
        DeleteAsyncTask(SavedReposDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(MovieSearchRepo... movieSearchRepos) {
            mAsyncTaskDAO.delete(movieSearchRepos[0]);
            return null;
        }
    }
}
