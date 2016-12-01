package com.example.popularmovies;

import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Igor on 11/26/2016.
 */

public class MovieViewHolder
{
    @BindView(R.id.poster_image_thumbnail)public ImageView poster_image;

    public MovieViewHolder (View view)
    {
       ButterKnife.bind(this, view);
    }
}
