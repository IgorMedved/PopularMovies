package com.example.popularmovies.favoritedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.popularmovies.favoritedatabase.MovieDbContract.MovieEntry;
import com.example.popularmovies.favoritedatabase.MovieDbContract.TrailerEntry;
import com.example.popularmovies.favoritedatabase.MovieDbContract.ReviewEntry;

/**
 * Created by Igor on 12/19/2016.
 */

public class MovieDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favorite_movies";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase)
    {
        // creating movie_db statement
        final StringBuilder SQL_CREATE_MOVIE_DB_TABLE_BUILDER =
                new StringBuilder("CREATE TABLE "+ MovieEntry.TABLE_NAME + " (" )
                        .append(MovieEntry._ID+ " INTEGER PRIMARY KEY, ") // no autoincrement same ids are used as in movie db
                        .append(MovieEntry.COLUMN_TITLE +  " TEXT NOT NULL, ")
                        .append(MovieEntry.COLUMN_RATING + " REAL NOT NULL, ")
                        .append(MovieEntry.COLUMN_PLOT + " TEXT NOT NULL, ")
                        .append(MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, ")
                        .append(MovieEntry.COLUMN_POSTER_URL + " TEXT NOT NULL, ")
                        .append(MovieEntry.COLUMN_POSTER_IMAGE + " BLOB ");
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_DB_TABLE_BUILDER.toString());

        final StringBuilder SQL_CREATE_REVIEW_TABLE_BUILDER =
                new StringBuilder("CREATE TABLE "+ ReviewEntry.TABLE_NAME + " (" )
                        .append(ReviewEntry._ID + " INTEGER PRIMARY KEY, " ) // same ids as are used in movie db
                        .append(ReviewEntry.COLUMN_MOVIE_KEY + " INTEGER NOT NULL, ")
                        .append(ReviewEntry.COLUMNT_REVIEW_AUTHOR + " TEXT NOT NULL, ")
                        .append(ReviewEntry.COLUMN_REVIEW + " TEXT NOT NULL, ")
                        .append(" FOREIGN KEY (" + ReviewEntry.COLUMN_MOVIE_KEY + ") REFERENCES " + MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + "), ");
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE_BUILDER.toString());

        final StringBuilder SQL_CREATE_TRAILER_TABLE_BUILDER =
                new StringBuilder("CREATE TABLE "+ TrailerEntry.TABLE_NAME + " (" )
                        .append(TrailerEntry._ID + " INTEGER PRIMARY KEY, " )
                        .append(TrailerEntry.COLUMN_MOVIE_KEY + " INTEGER NOT NULL, " )
                        .append(TrailerEntry.COLUMN_TRAILER_NAME + " TEXT NOT NULL, " )
                        .append(TrailerEntry.COLUMN_TRAILER_URL + " TEXT NOT NULL, " )
                        .append(" FOREIGN KEY (" + TrailerEntry.COLUMN_MOVIE_KEY + ") REFERENCES " + MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + "), ");
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE_BUILDER.toString());







    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        /*
         * This is the first version of the database nothing has to be done here
         *
         * For future versions the incremental upgrades strategy might be useful(source Android-Database-Upgrade-Tutorial)
         *
            switch(oldVersion +1) {

            case 2:
                upgradeFrom1to2()
            case 3:
                upgradeFrom2to3()
               .......
            case newVersion:
                upgradeFrom_newVersion-1_to_newVersion()
                break;
}


         */

    }
}
