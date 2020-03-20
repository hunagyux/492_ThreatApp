package com.example.android.githubsearchwithsqlite.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedReposDao {
    @Insert
    void insert(MovieSearchRepo repo);

    @Delete
    void delete(MovieSearchRepo repo);

    @Query("SELECT * FROM movie_repos")
    LiveData<List<MovieSearchRepo>> getAllRepos();

    @Query("SELECT * FROM movie_repos WHERE title = :title_name LIMIT 1")
    LiveData<MovieSearchRepo> getRepoByName(String title_name);
}
