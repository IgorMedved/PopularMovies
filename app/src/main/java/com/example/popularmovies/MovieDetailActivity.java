package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportFragmentManager().beginTransaction().add(R.id.movie_detail, new DetailFragment()).commit();
    }


    public static class DetailFragment extends Fragment{
        public DetailFragment()
        {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            ((TextView)rootView.findViewById(R.id.temp_detail_textView)).setText(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));
            return rootView;
        }
    }
}
