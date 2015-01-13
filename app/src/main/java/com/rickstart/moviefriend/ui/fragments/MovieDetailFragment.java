package com.rickstart.moviefriend.ui.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rickstart.moviefriend.R;
import com.rickstart.moviefriend.models.Movie;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import com.rickstart.moviefriend.models.*;


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
    LinearLayout detalle_fragment;
    Context context;

    // TODO: Rename and change types of parameters
    public static Movie movie;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(id_movie,movie);

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
        context=getActivity();
        Bundle bundle = getArguments();
        if(bundle!=null)
            movie = (Movie) bundle.getSerializable(id_movie);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        //  ImageView poster = (ImageView) row.findViewById(R.id.imageView);
        TextView titulo = (TextView) row.findViewById(R.id.tv_title_movie);
        TextView year = (TextView) row.findViewById(R.id.tv_year);
        TextView runtime = (TextView) row.findViewById(R.id.tv_runtime);
        TextView release = (TextView) row.findViewById(R.id.tv_date);
        TextView synopsis = (TextView) row.findViewById(R.id.tv_synopsis);
        RatingBar stars=(RatingBar) row.findViewById(R.id.ratingBarMovie);
        TextView audienceScore = (TextView) row.findViewById(R.id.tv_audience_score);
        TextView criticsScore = (TextView) row.findViewById(R.id.tv_critics_score);
        TextView reparto = (TextView) row.findViewById(R.id.tv_reparto);

        detalle_fragment=(LinearLayout) row.findViewById(R.id.detalle_layout);
        new DownloadAsyncTask().execute(movie.getPoster());

        //poster.setImageResource(R.drawable.hobbit);

        titulo.setText(movie.getTitle());
        year.setText("Año: "+movie.getYear());
        runtime.setText("Duracion: "+movie.getRuntime()+" min");
        release.setText("Fecha de estreno: "+movie.getReleaseDate());
        synopsis.setText(movie.getSynopsis());
        audienceScore.setText(String.valueOf("Calificacion audiencia: "+movie.getRating()));
        criticsScore.setText(String.valueOf("Calificacion criticos "+movie.getCriticsRating()));


        reparto.setText("Reparto: "+movie.getCasting().getName());


        double rating;
        rating= movie.getRating()*0.01*5;
        stars.setRating((float) rating);



        return row;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackGroundMovie(Bitmap bitmap){

        BitmapDrawable ob = new BitmapDrawable(context.getResources(), bitmap);
        detalle_fragment.setBackground(ob);


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

    private class DownloadAsyncTask extends AsyncTask<String, String, Bitmap> {

        private int position;


        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            //load image directly

            try {
                URL imageURL = new URL(params[0]);
                return BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                // TODO: handle exception
                Log.e("error", "Downloading Image Failed");
                return null;
            }


        }

        @Override
        protected void onPostExecute(Bitmap result) {

            if (result != null) {
                setBackGroundMovie(result);
            }
        }
    }
}
