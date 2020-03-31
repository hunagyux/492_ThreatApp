package com.example.android.githubsearchwithsqlite.data;

import java.io.Serializable;

public class NowPlayingRepo implements Serializable {

    public String title;
    //public int popularity;
    public int id;
    //    @ColumnInfo(name = "url")
    public String poster_path;

    public Float vote_average;
    public double popularity;
    public String overview;
    public String release_date;
}
