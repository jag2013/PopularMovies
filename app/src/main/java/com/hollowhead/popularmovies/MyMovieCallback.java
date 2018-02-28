package com.hollowhead.popularmovies;

import com.hollowhead.popularmovies.model.Movie;

/**
 * Created by jasonaholloway on 2/26/18.
 */

public interface MyMovieCallback {

    void updateAdapter(Movie[] movies);
}
