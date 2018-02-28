package com.hollowhead.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.hollowhead.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.Collections;

// References:
// trying to figure out gridview
// https://www.mkyong.com/android/android-gridview-example/

// now, trying images:
// http://www.java2s.com/Code/Android/UI/UsingGridViewtodisplayimages.htm


public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieList;
    private static final String KEY_MOVIE = "MOVIE";
    private static final String KEY_MOVIE_LIST = "MOVIE_LIST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_MOVIE_LIST)) {
            movieList = new ArrayList<>();
        } else {
            movieList = savedInstanceState.getParcelableArrayList(KEY_MOVIE_LIST);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        movieAdapter = new MovieAdapter(this, movieList);

        GridView gridView = findViewById(R.id.gridView1);

        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                intent.putExtra(KEY_MOVIE, movie);
                startActivity(intent);
            }
        });






        downloadPopularMovies();


    }

    private void downloadPopularMovies() {

        FetchPopularMovieTask moviesTask = new FetchPopularMovieTask(new MyMovieCallback() {
            public void updateAdapter(Movie[] movies) {

                if (movies != null) {
                    movieAdapter.clear();
                    Collections.addAll(movieList, movies);
                    movieAdapter.notifyDataSetChanged();

                }

            }
        });

          moviesTask.execute();


    }

        private void downloadTopRatedMovies() {

        FetchTopRatedMovieTask moviesTask = new FetchTopRatedMovieTask(new MyMovieCallback() {
            public void updateAdapter(Movie[] movies) {

                if (movies != null) {
                    movieAdapter.clear();
                    Collections.addAll(movieList, movies);
                    movieAdapter.notifyDataSetChanged();

                }

            }
        });

          moviesTask.execute();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE_LIST, movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        downloadPopularMovies();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_most_popular_refresh) {
            downloadPopularMovies();
            return true;
        }

        if (id == R.id.action_rating_refresh) {
            downloadTopRatedMovies();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }






}
