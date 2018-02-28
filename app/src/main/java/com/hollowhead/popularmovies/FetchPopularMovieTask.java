package com.hollowhead.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.hollowhead.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jasonaholloway on 2/26/18.
 */

// Reference material:
// https://github.com/google-developer-training/android-fundamentals/blob/master/WhoWroteIt/app/src/main/java/com/example/android/whowroteit/FetchBook.java
// http://www.informit.com/articles/article.aspx?p=1998555&seqNum=3
// https://stackoverflow.com/questions/3398363/how-to-define-callbacks-in-android
// https://stackoverflow.com/questions/34147217/load-image-from-url-to-imageview-in-a-gridview-using-picasso

public class FetchPopularMovieTask extends AsyncTask<String, Void, Movie[]> {

    private final MyMovieCallback movieTaskCallback;

    FetchPopularMovieTask(MyMovieCallback movieTaskCallback) {
        this.movieTaskCallback = movieTaskCallback;
    }

    @Override
    protected Movie[] doInBackground(String... params) {

        // Log.d("Response: ", "fetch movie");


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJSONString;

        try {

            // Note: I had put my api key in a file called api_key.xml under the values directory like so
            // String APIString = getString(R.string.API_KEY);
            //
            // But I got this error:
            // non static method getString(Int) cannot be referenced from a static context
            //
            // Until I figure out a good way to hide the API from others when uploading to gitup
            // I am just going to put it manually down below:

            String APIString = "insert your api key here";

            final String MOVIE_BASE_URL = ("http://api.themoviedb.org/3/movie/popular");

            Uri builtURI = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter("api_key", APIString)
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {

                builder.append(line + "\n");

                //log below to make sure everything is connecting up to the api
                //Log.d("Response: ", "> " + line);

            }

            if (builder.length() == 0) {
                return null;

            }

            movieJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        try {

            return getMoviesFromJson(movieJSONString);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }

  @Override
    protected void onPostExecute(Movie[] movies) {
        movieTaskCallback.updateAdapter(movies);
    }


    // Reference material:
    // https://stackoverflow.com/questions/9373398/how-do-i-pull-the-string-array-from-this-json-object

    private Movie[] getMoviesFromJson(String movieJSONString) throws JSONException {

        final String TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVG = "vote_average";
        final String RELEASE_DATE = "release_date";


        if (movieJSONString == null || "".equals(movieJSONString )) {
            return null;
        }

        JSONObject jsonObjectMovie = new JSONObject(movieJSONString );
        JSONArray jsonArrayMovies = jsonObjectMovie.getJSONArray("results");

        Movie[] movies = new Movie[jsonArrayMovies.length()];

        for (int i = 0; i < jsonArrayMovies.length(); i++) {
            JSONObject object = jsonArrayMovies.getJSONObject(i);
            movies[i] = new Movie(object.getString(TITLE),
                    object.getString(POSTER_PATH),
                    object.getString(OVERVIEW),
                    object.getString(VOTE_AVG),
                    object.getString(RELEASE_DATE));
        }

        // print out results in log to make sure everything is working.

        // Log.i("JSON OBJECT MOVIE RESULT", jsonObjectMovie.toString());


        return movies;


    }
}