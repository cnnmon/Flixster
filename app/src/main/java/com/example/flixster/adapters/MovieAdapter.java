package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public static final String TAG = "MovieAdapter";
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // inflates layout and returns holder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.layout_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // gets movie and binds movie data to holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder " + position);
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            boolean isLand = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
            String imageURL = isLand ? movie.getBackdropPath() : movie.getPosterPath();
            int placeholder = isLand ? R.drawable.backdrop_placeholder : R.drawable.movie_placeholder;
            Glide.with(context).load(imageURL)
                    .fitCenter()
                    .centerCrop() // scale image to fill the entire ImageView
                    .transform(new RoundedCornersTransformation(30, 0))
                    .placeholder(placeholder)
                    .into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // get movie & initialize intent
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                // serialize movie
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }
}
