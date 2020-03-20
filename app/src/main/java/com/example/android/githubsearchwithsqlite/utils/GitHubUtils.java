package com.example.android.githubsearchwithsqlite.utils;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.android.githubsearchwithsqlite.data.MovieSearchRepo;
import com.example.android.githubsearchwithsqlite.data.NowPlayingRepo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GitHubUtils {
    private final static String GITHUB_SEARCH_BASE_URL = "https://api.github.com/search/repositories";
    private final static String GITHUB_SEARCH_QUERY_PARAM = "q";
    private final static String GITHUB_SEARCH_SORT_PARAM = "sort";
    private final static String GITHUB_SEARCH_SORT_VALUE = "stars";
    private final static String GITHUB_SEARCH_LANGUAGE_FORMAT_STR = "language:%s";
    private final static String GITHUB_SEARCH_USER_FORMAT_STR = "user:%s";
    private final static String GITHUB_SEARCH_SEARCH_IN_FORMAT_STR = "in:%s";
    private final static String GITHUB_SEARCH_IN_NAME = "name";
    private final static String GITHUB_SEARCH_IN_DESCRIPTION = "description";
    private final static String GITHUB_SEARCH_IN_README = "readme";

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




    public static String buildGitHubSearchURL(String query, String sort, String language,
                                              String user, boolean searchInName,
                                              boolean searchInDescription, boolean searchInReadme) {

        Uri.Builder builder = Uri.parse(GITHUB_SEARCH_BASE_URL).buildUpon();

        /*
         * Language, username, and search-in terms are incorporated directly into the query
         * parameter, e.g. "q=android language:java user:square".  Below, we simply fold those
         * terms into the query parameter if they're specified.
         */
        if (!language.equals("")) {
            query += " " + String.format(GITHUB_SEARCH_LANGUAGE_FORMAT_STR, language);
        }

        if (!user.equals("")) {
            query += " " + String.format(GITHUB_SEARCH_USER_FORMAT_STR, user);
        }

        String searchIn = buildSearchInURLString(searchInName, searchInDescription, searchInReadme);
        if (searchIn != null) {
            query += " " + String.format(GITHUB_SEARCH_SEARCH_IN_FORMAT_STR, searchIn);
        }

        /*
         * Finally append the query parameters into the URL, including sort only if specified.
         */
        builder.appendQueryParameter(GITHUB_SEARCH_QUERY_PARAM, query);
        if (!sort.equals("")) {
            builder.appendQueryParameter(GITHUB_SEARCH_SORT_PARAM, sort);
        }

        return builder.build().toString();
    }


    public static String buildGitHubSearchURL(String q) {
        return Uri.parse(GITHUB_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(GITHUB_SEARCH_QUERY_PARAM, q)
                .appendQueryParameter(GITHUB_SEARCH_SORT_PARAM, GITHUB_SEARCH_SORT_VALUE)
                .build()
                .toString();
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

    @Nullable
    private static String buildSearchInURLString(boolean searchInName, boolean searchInDescription,
                                                 boolean searchInReadme) {
        ArrayList<String> searchInTerms = new ArrayList<>();
        if (searchInName) {
            searchInTerms.add(GITHUB_SEARCH_IN_NAME);
        }
        if (searchInDescription) {
            searchInTerms.add(GITHUB_SEARCH_IN_DESCRIPTION);
        }
        if (searchInReadme) {
            searchInTerms.add(GITHUB_SEARCH_IN_README);
        }

        if (!searchInTerms.isEmpty()) {
            return TextUtils.join(",", searchInTerms);
        } else {
            return null;
        }
    }
}
