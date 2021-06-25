package com.example.flixster.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@Parcel
public class Movie {

    public static final String TAG = "Movie";

    int id;
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    String releaseDate;
    String videoId;
    double voteAverage;

    // empty constructor for parceler
    public Movie() { }

    public Movie(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        releaseDate = jsonObject.getString("release_date");
        videoId = "";
        getVideoPath(); // overrides empty string IF at least one video exists
    }

    private void getVideoPath() {
        String VIDEOS_URL = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + BuildConfig.MOVIE_API_KEY + "&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEOS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "success");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray trailers = jsonObject.getJSONArray("results");
                    if (trailers.length() > 0) {
                        videoId = trailers.getJSONObject(0).getString("key");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "failure");
            }
        });
    }

    public static List<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i += 1) {
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() { return voteAverage; }

    public String getReleaseDate() { return releaseDate; }

    public String getVideoId() {
        return videoId;
    }
}
