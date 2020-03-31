package com.example.android.githubsearchwithsqlite.utils;

import android.net.Uri;

import com.example.android.githubsearchwithsqlite.data.MovieSearchRepo;
import com.example.android.githubsearchwithsqlite.data.NowPlayingRepo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GitHubUtils {


    //================================================================================================
    //DashBoard
    private final static String MOVIE_NOW_PLAYING_BASE_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private final static String MOVIE_API_PARAM = "api_key";
    private final static String MOVIE_API_KEY = "d6ee9422ac191601069dbfb440536f6e";
    //=================================================================================================
    //Search
    private final static String MOVIE_SEARCH_BASE_URL = " https://api.themoviedb.org/3/search/movie";
    private final static String MOVIE_SEARCH_LANGUAGE_PARA = "language";
    private final static String MOVIE_SEARCH_LANGUAGE_VALUE = "en-US";
    private final static String MOVIE_SEARCH_QUERY_PARA = "query";

    private final static String MOVIE_SEARCH_YEAR_PARA = "year";
    private final static String MOVIE_SEARCH_ADULT_PARA = "include_adult";
    //=================================================================================================


    static class NowPlayingResults{
        ArrayList<NowPlayingRepo> results;

    }

    static class MovieSearchResults{
        ArrayList<MovieSearchRepo> results;
    }
    public static ArrayList<NowPlayingRepo> parseNowPlayingResults(String json){
        Gson gson = new Gson();
        NowPlayingResults results = gson.fromJson(json, NowPlayingResults.class);
        if (results != null && results.results != null) {
            return results.results;
        } else {
            return null;
        }
    }

    public static ArrayList<MovieSearchRepo> parseMovieSearchResults(String json){
        Gson gson = new Gson();
        MovieSearchResults results = gson.fromJson(json, MovieSearchResults.class);
        if (results != null && results.results != null) {
            return results.results;
        } else {
            return null;
        }
    }




    public static String buildNowPlayingURL() {

        Uri.Builder builder = Uri.parse(MOVIE_NOW_PLAYING_BASE_URL).buildUpon();
        builder.appendQueryParameter(MOVIE_API_PARAM, MOVIE_API_KEY);
        return builder.build().toString();
    }

    public static String buildMovieSearchURL(String searchquery, String year, boolean adult_type, String language){
        Uri.Builder builder = Uri.parse(MOVIE_SEARCH_BASE_URL).buildUpon();
        builder.appendQueryParameter(MOVIE_API_PARAM, MOVIE_API_KEY);
        builder.appendQueryParameter(MOVIE_SEARCH_LANGUAGE_PARA, language);
        builder.appendQueryParameter(MOVIE_SEARCH_QUERY_PARA, searchquery);
        String adult = Boolean.toString(adult_type);
        builder.appendQueryParameter(MOVIE_SEARCH_ADULT_PARA, adult);

        if (!year.equals("")) {
            builder.appendQueryParameter(MOVIE_SEARCH_YEAR_PARA, year);
        }

        return builder.build().toString();
    }



}
