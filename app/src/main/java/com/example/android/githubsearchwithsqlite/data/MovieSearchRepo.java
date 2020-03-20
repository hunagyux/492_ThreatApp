package com.example.android.githubsearchwithsqlite.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie_repos")
public class MovieSearchRepo implements Serializable {
    @PrimaryKey
    @NonNull
    public String title;
    //public int popularity;
    public int id;
    public String poster_path;

    public double vote_average;
    public double popularity;
    public String overview;

}
