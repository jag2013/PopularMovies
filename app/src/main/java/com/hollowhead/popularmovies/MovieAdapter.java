package com.hollowhead.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hollowhead.popularmovies.R;
import com.hollowhead.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jasonaholloway on 2/26/18..
 */

 public class MovieAdapter extends BaseAdapter {

        private final Context mContext;

        private final List<Movie> movies;

        public MovieAdapter(Context context, List<Movie> movies) {
            this.mContext = context;
            this.movies = movies;
        }

        public int getCount() {
            return movies.size();
        }

        public Movie getItem(int position) {
            return movies.get(position);
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {

            Movie movie = getItem(position);
            ImageView imageView;

            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                imageView = (ImageView) inflater.inflate(R.layout.movie_poster_image, parent, false);

            } else {

                    imageView = (ImageView) convertView;

            }

            String imagePath = movie.getPosterPath();

            // make sure poster path is working
            // Log.d("Response: ", "return getMoviePosterPath " + movie.getPosterPath());

            Picasso.with(mContext).load(imagePath).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(imageView);

            return imageView;



        }

        public void clear() {
        if (movies.size() > 0) {
            movies.clear();
        }
    }



    }