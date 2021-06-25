package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.flixster.databinding.ActivityMovieTrailerBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    String videoKey;
    public static final String TAG = "MovieTrailerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // unwrap videoKey
        videoKey = getIntent().getExtras().getString("videoKey");

        ActivityMovieTrailerBinding binding = ActivityMovieTrailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        youTubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}