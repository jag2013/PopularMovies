package com.hollowhead.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hollowhead.popularmovies.constants.Constants;

/**
 * Created by jasonaholloway on 2/10/18.
 */

// Reference material:
//
// https://github.com/udacity/android-custom-arrayadapter/blob/parcelable/app/src/main/java/demo/example/com/customarrayadapter/AndroidFlavor.java
// https://guides.codepath.com/android/using-parcelable

public class Movie implements Parcelable {

    private final String title;
    private final String posterPath;
    private final String overview;
    private final String voteAverage;
    private final String releaseDate;

    public Movie (String title, String posterPath, String overview, String voteAverage, String releaseDate) {
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    private Movie (Parcel parcel) {
        title = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        voteAverage = parcel.readString();
        releaseDate = parcel.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {

        return Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_W185_SIZE + posterPath;

    }

    public String getBigPosterPath() {

        return Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_W500_SIZE + posterPath;

    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
