package com.hollowhead.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hollowhead.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by jasonaholloway on 2/27/18.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private final static String KEY_MOVIE = "MOVIE";
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();

        Movie movie = intent.getParcelableExtra(KEY_MOVIE);

        TextView detailsMovieTitle = findViewById(R.id.textMovieTitle);

        ImageView detailsImageMoviePoster = findViewById(R.id.imageMoviePoster);

        TextView detailsTextReleaseDate = findViewById(R.id.textReleaseDate);

        TextView detailsRatings = findViewById(R.id.textVoteAverage);

        TextView detailsMovieOverview = findViewById(R.id.textPlotSynopsis);

        if (movie != null) {

            detailsMovieTitle.setText(movie.getTitle());


            String imagePath = movie.getBigPosterPath();

            Picasso.with(mContext).load(imagePath).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(detailsImageMoviePoster);


            detailsTextReleaseDate.setText("Release Date: " + String.format("%.4s", movie.getReleaseDate()));
            detailsRatings.setText(String.format("Vote Average: " + "%s/10", movie.getVoteAverage()));
            detailsMovieOverview.setText("Overview: "+ movie.getOverview());


        }



    }







}
