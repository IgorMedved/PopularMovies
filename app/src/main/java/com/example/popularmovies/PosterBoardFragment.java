package com.example.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
  * This is a UI class for showing movie posters on the screen
 *  inner class @GetMoviesTask class gets movie info from @The Movie Data Base and processes the returned JSON data in the background
 */
public class PosterBoardFragment extends Fragment
{
    public static final String LOG_TAG = PosterBoardFragment.class.getSimpleName();
    // using Butter knife as suggested in review of project 0
    @BindView((R.id.error_main_text)) TextView mErrorText;
    @BindView((R.id.fragment_posters)) GridView mGrid;
    private Unbinder mUnbinder;
    private MovieAdapter mAdapter;
    private int mScreenWidth;
    private int mScreenHeight;
    private GetMoviesTask mDownloadTask;

    public PosterBoardFragment ()
    {
        // Required empty public constructor
    }

    @Override
    public void onResume ()
    {
        updateScreen();
        super.onResume();
    }

    private void updateScreen()
    {
        // get estimate for number of pages of JSON script needed to fill the screen with movie posters
        int numPages = mScreenWidth * mScreenHeight * 2 / 3 / 185 / 185 / 20 + 1;
        // start download Task
        mDownloadTask = new GetMoviesTask();
        DownloadParameters params = new DownloadParameters(numPages);
        mDownloadTask.execute(params);
    }

    @Override
    public void onStop ()
    {
        // stop Download task to prevent Activity not attached errors and freeing AsyncTask thread
        mDownloadTask.cancel(true);
        super.onStop();
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_poster_board, container, false);
        List<MovieRecord> movieMock = new ArrayList(); // List of records used by adapter
        // get values of screen width and screen height in pixels
        mScreenWidth = Utility.getWidth(getActivity());
        mScreenHeight = Utility.getHeight(getActivity());
        mUnbinder = ButterKnife.bind(this,rootView);// using ButterKnife instead of findViewById calls
        mGrid.setNumColumns((mScreenWidth - 150) / 185 > 3 ? (mScreenWidth - 150) / 185 : 3);
        mAdapter = new MovieAdapter(getActivity(), movieMock);
        mGrid.setAdapter(mAdapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                detailIntent.putExtra("movie_data", mAdapter.getItem(i));
                startActivity(detailIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView ()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    // Class for getting movie info from @The Movie Data Base and processing the returned JSON data
    public class GetMoviesTask extends AsyncTask<DownloadParameters, Void, MovieRecord[]>
    {
        private final String LOG_TAG = GetMoviesTask.class.getSimpleName();
        private boolean isAdapterReloaded; // variable for keeping track

        @Override
        // requesting download and processing results in background threaad
        protected MovieRecord[] doInBackground (DownloadParameters... params)
        {
            if (params == null)
            {
                return null;
            }
            int numPages = params[0].getNumPages();
            int firstPage = params[0].getFirstPageNum();
            isAdapterReloaded = firstPage==1;

            // connection client for getting data
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieDataJsonStr = null;
            final int RESULTS_PER_PAGE = 20; // tmdb request returns 20 results per page
            List<MovieRecord> records = new ArrayList(numPages * 20);

            try
            {
                // tmdb request returns only 20 results per page, if more poster are needed several requests need to be made
                for (int i = firstPage; i < numPages + firstPage; i++)
                {

                    final String API_BASE = getString(R.string.tmdb_api_base);
                    final String API_KEY = "api_key";
                    SharedPreferences sharedPref = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    String sortingPreference = sharedPref
                            .getString(getString(R.string.pref_order_by_key), getString(R.string.pref_order_default));
                    Uri requestUri = Uri.parse(API_BASE).buildUpon().appendPath(sortingPreference)
                                        .appendQueryParameter(API_KEY, ApiAuthorization.API_KEY)
                                        .appendQueryParameter("page", Integer.toString(i)).build();
                    URL url = new URL(requestUri.toString());

                    // Create the request to The Movie Database, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(2000);
                    // There is an error that is generated

                    if (!Utility.isAirplaneModeOn(getActivity()))
                        urlConnection.connect();
                    else
                        throw new IOException("Airplane mode is on, but does not throw exception");

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    if (inputStream == null)
                    {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0)
                    {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    movieDataJsonStr = buffer.toString();
                    try
                    {
                        records.addAll(Arrays
                                .asList(getMoiveDataFromJson(movieDataJsonStr, RESULTS_PER_PAGE)));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        return null;
                    }
                }

            }
            catch (IOException e)
            {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            }
            finally
            {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (final IOException e)
                    {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            // This will only happen if there was an error getting movies.
            if (movieDataJsonStr == null)
            {
                return null;
            }
            else
            {
                return records.toArray(new MovieRecord[numPages * RESULTS_PER_PAGE]);
            }
        }

        // processing JSON data and converting it into the array of movie records
        private MovieRecord[] getMoiveDataFromJson (String movieData, int resultsPerPage) throws JSONException
        {
            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS = "results";
            final String TMDB_POSTER = "poster_path";
            final String TMDB_PLOT_SYNOPSYS = "overview";
            final String TMDB_DATE = "release_date";
            final String TMDB_TITLE = "original_title";
            final String TMDB_RATING = "vote_average";

            JSONObject movieDataJson = new JSONObject(movieData);
            JSONArray movieArray = movieDataJson.getJSONArray(TMDB_RESULTS);
            MovieRecord[] records = new MovieRecord[resultsPerPage];

            for (int i = 0; i < resultsPerPage; i++)
            {
                JSONObject record = movieArray.getJSONObject(i);
                String poster = record.getString(TMDB_POSTER);
                String synopsys = record.getString(TMDB_PLOT_SYNOPSYS);
                String title = record.getString(TMDB_TITLE);
                double rating = record.getDouble(TMDB_RATING);
                String date = record.getString(TMDB_DATE);
                records[i] = new MovieRecord(title, poster, synopsys, rating, date);
            }
            return records;
        }

        // this function processes the results of doInBackground on UI thread
        @Override
        protected void onPostExecute (MovieRecord[] movieRecords)
        {
            if (isAdapterReloaded)
                mAdapter.clear();
            if (movieRecords != null && movieRecords[0] != null)
            {
                mErrorText.setText("");
                for (int i = 0; i < movieRecords.length; i++)
                {
                    mAdapter.add(movieRecords[i]);
                }
            }
            else
            {
                mErrorText.setText(getString(R.string.picture_error));
                Toast.makeText(getActivity(), "There is problem getting data from the server. Check internet connectivity or try again later", Toast.LENGTH_LONG)
                     .show();
            }
        }
    }
}
