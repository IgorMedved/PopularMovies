package com.example.popularmovies.favoritedatabase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Igor on 12/18/2016.
 */

// Defines Tables containing movie information
public class MovieDbContract
{
    public static final String CONTENT_AUTHORITY = "com.example.popularmovies.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.popularmovies.app/movie/ is a valid path for
    // looking at movie data. content://com.example.popularmovies.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_MOVIES = "movies";
    public static final String PATH_REVIEWS = "reviews";
    public static final String PATH_TRAILERS = "trailers";

    public static final class MovieEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_RATING = "rating";

        public static final String COLUMN_PLOT = "plot";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_POSTER_URL = "poster_url";

        public static final String COLUMN_POSTER_IMAGE = "poster_image";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES)
                                                              .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;


        public static Uri buildMovieUri (long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieWithReviews (long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id).buildUpon().appendPath(PATH_REVIEWS).build();
        }

        public static Uri buildMovieWithTrailers (long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id).buildUpon().appendPath(PATH_TRAILERS).build();
        }

        public static long getMovieIdFromUri(Uri uri)
        {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

    public static final class ReviewEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "reviews";

        public static final String COLUMN_MOVIE_KEY = "movie_id";

        public static final String COLUMN_REVIEW = "movie_review";

        public static final String COLUMNT_REVIEW_AUTHOR = "author";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS)
                                                              .build();

        public static Uri buildReviewUri (long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;
    }

    public static final class TrailerEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "trailers";

        public static final String COLUMN_MOVIE_KEY = "movie_id";

        public static final String COLUMN_TRAILER_NAME = "trailer_name";

        public static final String COLUMN_TRAILER_URL = "trailer_url"; // youtube key is stored here


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS)
                                                              .build();

        public static Uri buildTrailerUri (long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

    }
}
