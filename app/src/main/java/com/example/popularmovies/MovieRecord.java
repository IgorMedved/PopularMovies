package com.example.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Igor on 11/25/2016.
 */
// Data about the movie
public class MovieRecord implements Parcelable
{
    private String mTitle;
    private String mPosterUri;
    private String mPlotSynopsys;
    private double mRating;
    private String mDate;

    public MovieRecord (String title, String posterUri, String plotSynopsys, double rating, String date)
    {
        mTitle = title;
        mPosterUri = posterUri;
        mPlotSynopsys = plotSynopsys;
        mRating = rating;
        mDate = date;
    }

    protected MovieRecord (Parcel in)
    {
        mTitle = in.readString();
        mPosterUri = in.readString();
        mPlotSynopsys = in.readString();
        mRating = in.readDouble();
        mDate = in.readString();
    }

    public static final Creator<MovieRecord> CREATOR = new Creator<MovieRecord>()
    {
        @Override
        public MovieRecord createFromParcel (Parcel in)
        {
            return new MovieRecord(in);
        }

        @Override
        public MovieRecord[] newArray (int size)
        {
            return new MovieRecord[size];
        }
    };

    public String getTitle ()
    {
        return mTitle;
    }

    public void setTitle (String title)
    {
        mTitle = title;
    }

    public String getPosterUri ()
    {
        return mPosterUri;
    }

    public void setPosterUri (String posterUri)
    {
        mPosterUri = posterUri;
    }

    public String getPlotSynopsys ()
    {
        return mPlotSynopsys;
    }

    public void setPlotSynopsys (String plotSynopsys)
    {
        mPlotSynopsys = plotSynopsys;
    }

    public double getRating ()
    {
        return mRating;
    }

    public void setRating (double rating)
    {
        mRating = rating;
    }

    public String getDate ()
    {
        return mDate;
    }

    public void setDate (String date)
    {
        mDate = date;
    }

    @Override
    public int describeContents ()
    {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel parcel, int i)
    {
        parcel.writeString(mTitle);
        parcel.writeString(mPosterUri);
        parcel.writeString(mPlotSynopsys);
        parcel.writeDouble(mRating);
        parcel.writeString(mDate);
    }

    @Override
    public String toString ()
    {
        return "Title " + getTitle() + " rating "+ getRating() + " date " +  getDate();
    }
}
