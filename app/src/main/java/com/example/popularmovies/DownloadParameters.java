package com.example.popularmovies;

/**
 * Created by Igor on 11/27/2016.
 */
// This class is needed for passing paramenters into doInBackground of GetMoviesTask
public class DownloadParameters
{
    private int numPages;
    private int firstPageNum;


    public DownloadParameters()
    {
        this(1);
    }

    public DownloadParameters (int numPages)
    {
        this (numPages, 1);
    }

    public DownloadParameters (int numPages, int firstPageNum)
    {
        this.numPages = numPages;
        this.firstPageNum = firstPageNum;
    }


    public int getNumPages ()
    {
        return numPages;
    }

    public void setNumPages (int numPages)
    {
        this.numPages = numPages;
    }

    public int getFirstPageNum ()
    {
        return firstPageNum;
    }

    public void setFirstPageNum (int firstPageNum)
    {
        this.firstPageNum = firstPageNum;
    }


}
