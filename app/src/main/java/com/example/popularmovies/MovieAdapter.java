package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by Igor on 11/26/2016.
 */
// Class that Binds posters from TMDB to gridView
public class MovieAdapter extends ArrayAdapter<MovieRecord>
{
    @BindString(R.string.tmdb_posters_base) String POSTER_BASE;
    public MovieAdapter (Context context, List<MovieRecord> objects)
    {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the MovieRecord object from the ArrayAdapter at the appropriate position
        MovieViewHolder movieViewHolder;
        MovieRecord record;

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_movie_cell, parent, false);
            movieViewHolder = new MovieViewHolder(convertView);
            convertView.setTag(movieViewHolder);
        }
        else
        {
            movieViewHolder = (MovieViewHolder)convertView.getTag();
        }
        ButterKnife.bind(this, convertView);
        record = getItem(position);
        if (record!=null )
        {
            Picasso.with(getContext()).load(POSTER_BASE +"w185" + record.getPosterUri())
                   .into(movieViewHolder.poster_image);
        }
        return convertView;
    }
}
