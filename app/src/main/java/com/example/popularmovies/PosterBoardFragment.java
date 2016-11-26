package com.example.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**

 */
public class PosterBoardFragment extends Fragment {

    private ArrayAdapter<String> mAdapter;

    public PosterBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_poster_board, container, false);
        List<String> moviesMock = new ArrayList<>();
        moviesMock.add("Shawshank Redemption");
        moviesMock.add("Green Mile");
        moviesMock.add("Star Wars: New Hope");
        moviesMock.add("Once Upon a Time in America");
        moviesMock.add("Pulp Fiction");
        //TODO: butter knife binding
        GridView grid = (GridView)rootView.findViewById(R.id.fragment_posters);
        grid.setNumColumns (grid.getWidth()/185 > 3? grid.getWidth()/185: 3);
        mAdapter = new ArrayAdapter<>(getActivity(),R.layout.grid_cell, R.id.cell_textview,moviesMock);
        grid.setAdapter(mAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                detailIntent.putExtra(Intent.EXTRA_TEXT,mAdapter.getItem(i));
                startActivity(detailIntent);
            }
        });
        return rootView;
    }

    public class GetMoviesTask extends AsyncTask<Void, Void, MovieRecord[]>
    {

        @Override
        protected MovieRecord[] doInBackground(Void... voids) {
            return new MovieRecord[0];
        }
    }
}
