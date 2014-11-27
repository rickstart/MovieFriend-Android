package com.rickstart.moviefriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;

/**
 * Created by Rick on 26/11/14.
 */
public class MovieAdapter  extends ArrayAdapter<String> {
    private final Context context;



    String[] movies;

    public MovieAdapter(Context context, String[] movies) {
        super(context, R.layout.item_grid_movie, movies);
        this.context = context;
        this.movies = movies;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_grid_movie, parent, false);
        float rate = (float) 2.50;
        RatingBar rating = (RatingBar) rowView.findViewById(R.id.ratingBar);
        rating.setNumStars(5);
        rating.setRating(rate);

        return rowView;
    }


}