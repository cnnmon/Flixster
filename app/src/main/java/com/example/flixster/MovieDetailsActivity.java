package com.example.flixster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Context context = this;
    Movie movie;

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackdrop;
    TextView tvReleaseDate;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // unwrap movie
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        // get data
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        ivBackdrop = findViewById(R.id.ivBackdrop);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);

        // set data
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(movie.getReleaseDate());
        rbVoteAverage.setRating((int) (movie.getVoteAverage() / 2.0f));
        Glide.with(this).load(movie.getBackdropPath()).placeholder(R.drawable.backdrop_placeholder).into(ivBackdrop);

        // backdrop click listener
        ivBackdrop.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieTrailerActivity.class);
            intent.putExtra("videoKey", movie.getVideoId());
            context.startActivity(intent);
        });
    }
}
