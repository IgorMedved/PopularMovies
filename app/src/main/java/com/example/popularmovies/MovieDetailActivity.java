package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


// activity for showing
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail, new DetailFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.settings)
        {
            Intent menuIntent = new Intent (this, SettingsActivity.class);
            startActivity(menuIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public static class DetailFragment extends Fragment{
        @BindView(R.id.title_text) TextView mTitleText;
        @BindView(R.id.synopsys_text) TextView mSynopsisText;
        @BindView(R.id.release_date_text) TextView mReleaseDateText;
        @BindView(R.id.rating_text) TextView mRatingText;
        @BindView(R.id.poster_image_detail) ImageView mPosterDetailImage;
        private Unbinder mUnbinder;

        public DetailFragment()
        {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            if (getActivity().getIntent()!=null)
            {
                MovieRecord details = getActivity().getIntent().getParcelableExtra("movie_data");
                if (details!= null)
                {
                    mUnbinder = ButterKnife.bind(this, rootView);
                    mSynopsisText.setText("Plot Synopsis:\n" + details.getPlotSynopsys());
                    mTitleText.setText(details.getTitle());
                    mReleaseDateText.setText(details.getDate());
                    mRatingText.setText("User Rating: " + details.getRating());
                    Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/" + getImageSize()+details.getPosterUri()).
                            into(mPosterDetailImage);
                }
            }

            return rootView;
        }

        @Override
        public void onDestroyView ()
        {
            super.onDestroyView();
            mUnbinder.unbind();
        }

        // get poster image size, based on device screen size
        private String getImageSize()
        {
            int width = Utility.getWidth(getActivity());
            int height = Utility.getHeight(getActivity());

            // make sure image doesn't take more than 3/5 of the screen in portrait mode
            if (height > width) // portrait orientation
            {
                if (width < 5*300/3)
                    return "/w185";
                else if (width < 5*500/3)
                    return "/w342";
                else if (width < 5*780/3)
                    return "/w500";
                else if (width < 5*1000/3)
                    return "/w780";
            }
            else
            {
                if (height < 5*300/3)
                    return "/w185";
                else if (height < 5*500/3)
                    return "/w342";
                else if (height < 5*780/3)
                    return "/w500";
                else if (height < 5*1000/3)
                    return "/w780";
            }
            return "/original";
        }
    }


}
