package com.rickstart.moviefriend.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rickstart.moviefriend.R;
import com.rickstart.moviefriend.models.Movie;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String id_movie = "movie";


    // TODO: Rename and change types of parameters
    public static Movie movie;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(id_movie, getMovie(movie));

        fragment.setArguments(args);
        return fragment;
    }

    public static Serializable getMovie(Movie movie){
        return (Serializable) movie;
    }

    public static Movie getMovie(Serializable movie){
        return (Movie) movie;
    }
    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        Serializable serializable = bundle.getSerializable(id_movie);
        movie = getMovie(serializable);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ImageView poster = (ImageView) container.findViewById(R.id.imageView);
        TextView titulo = (TextView) container.findViewById(R.id.tv_title_movie);
        TextView year = (TextView) container.findViewById(R.id.tv_year);
        TextView runtime = (TextView) container.findViewById(R.id.tv_runtime);
        TextView release = (TextView) container.findViewById(R.id.tv_date);
        TextView synopsis = (TextView) container.findViewById(R.id.tv_synopsis);
        RatingBar stars=(RatingBar) container.findViewById(R.id.ratingBarMovie);

        //poster.setImageResource(R.drawable.hobbit);

        titulo.setText(movie.getTitle());
        year.setText(movie.getYear());
        runtime.setText(movie.getRuntime());
        release.setText(movie.getRuntime());
        synopsis.setText(movie.getSynopsis());

        double rating;
        rating= movie.getRating()*0.01*5;
        stars.setRating((float) rating);


        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           // mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
