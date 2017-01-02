package com.example.popularmovies.favoritedatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.popularmovies.favoritedatabase.MovieDbContract.MovieEntry;

/**
 * Created by Igor on 12/19/2016.
 */

public class MovieProvider extends ContentProvider
{
    private static final UriMatcher sUriMatcher = buildUriMatcher(); // todo add uris to uriMatcher
    private MovieDbHelper mOpenHelper;

    static final int MOVIES = 100;
    static final int MOVIE = 110;
    static final int MOVIE_WITH_REVIEWS = 111;
    static final int MOVIE_WITH_TRAILERS = 112;
    static final int REVIEWS = 200;
    static final int TRAILERS = 300;


    @Override
    public boolean onCreate ()
    {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Cursor retCursor;
        switch (sUriMatcher.match(uri))
        {
        case MOVIES:
            retCursor = mOpenHelper.getReadableDatabase().query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case REVIEWS:
            retCursor = mOpenHelper.getReadableDatabase().query(MovieDbContract.ReviewEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case TRAILERS:
            retCursor = mOpenHelper.getReadableDatabase().query(MovieDbContract.TrailerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        case MOVIE_WITH_REVIEWS:
            retCursor = getMovieWithReviews(uri, projection, sortOrder);

        }

        return null;
    }

    private static String sMovieReviewsSelection = MovieEntry.TABLE_NAME +  ".";


    private Cursor getMovieWithReviews(Uri uri, String [] projection, String sortOrder)
    {
      /*  long movieId = MovieEntry.getMovieIdFromUri(uri);
        String[] selectionArgs;
        String selection;

        if (movieId == 0) {
            selection = sMovieReviewsSelection;
            selectionArgs = new String[]{locationSetting};
        } else {
            selectionArgs = new String[]{locationSetting, Long.toString(startDate)};
            selection = sLocationSettingWithStartDateSelection;
        }

        return sWeatherByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );*/

        return new SQLiteQueryBuilder().query(mOpenHelper.getReadableDatabase(),projection, new String(), new String[]{},null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType (Uri uri)
    {
        final int match = sUriMatcher.match(uri);

        switch(match)
        {
        case MOVIES:
            return MovieEntry.CONTENT_TYPE;
        case MOVIE:
            return MovieEntry.CONTENT_ITEM_TYPE;
        case REVIEWS:
            return MovieDbContract.ReviewEntry.CONTENT_TYPE;
        case TRAILERS:
            return MovieDbContract.TrailerEntry.CONTENT_TYPE;
        case MOVIE_WITH_REVIEWS:
            return MovieEntry.CONTENT_TYPE;
        case MOVIE_WITH_TRAILERS:
            return MovieEntry.CONTENT_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert (Uri uri, ContentValues contentValues)
    {
        return null;
    }

    @Override
    public int delete (Uri uri, String s, String[] strings)
    {
        return 0;
    }

    @Override
    public int update (Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;
    }

    static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieDbContract.CONTENT_AUTHORITY, MovieDbContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MovieDbContract.CONTENT_AUTHORITY, MovieDbContract.PATH_REVIEWS, REVIEWS);
        uriMatcher.addURI(MovieDbContract.CONTENT_AUTHORITY, MovieDbContract.PATH_TRAILERS, TRAILERS);
        uriMatcher.addURI(MovieDbContract.CONTENT_AUTHORITY, MovieDbContract.PATH_MOVIES + "/#", MOVIE);
        uriMatcher.addURI(MovieDbContract.CONTENT_AUTHORITY, MovieDbContract.PATH_MOVIES + "/#/"+ MovieDbContract.PATH_REVIEWS, MOVIE_WITH_REVIEWS);
        uriMatcher.addURI(MovieDbContract.CONTENT_AUTHORITY, MovieDbContract.PATH_MOVIES + "/#/"+ MovieDbContract.PATH_TRAILERS, MOVIE_WITH_TRAILERS);
        return uriMatcher;
    }
}
