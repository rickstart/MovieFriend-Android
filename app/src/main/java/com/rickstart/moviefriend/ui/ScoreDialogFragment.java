package com.rickstart.moviefriend.ui;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.parse.ParseObject;
import com.rickstart.moviefriend.R;
import com.rickstart.moviefriend.models.Movie;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScoreDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoreDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreDialogFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MOVIE = "movie";


    // TODO: Rename and change types of parameters
    private Movie movie;

    private Button btnSend;
    private RatingBar scoreRatingBar;
    private EditText etComment;
    private OnFragmentInteractionListener mListener;


    public static ScoreDialogFragment newInstance(Movie movie) {
        ScoreDialogFragment fragment = new ScoreDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);

        fragment.setArguments(args);
        return fragment;
    }

    public ScoreDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie =(Movie) getArguments().getSerializable(ARG_MOVIE);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_dialog, container, false);

        btnSend = (Button) view.findViewById(R.id.btnSend);
        scoreRatingBar = (RatingBar) view.findViewById(R.id.ratingBarDialog);
        etComment = (EditText) view.findViewById(R.id.etComment);
        btnSend.setOnClickListener(this);


        return view;
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

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSend:
                sendScore();
                break;
        }

    }

    public void sendScore(){

        ParseObject movieScoreObject = new ParseObject("MovieScore");
        movieScoreObject.put("movieId", movie.getTitle());
        movieScoreObject.put("userId", "1");
        movieScoreObject.put("score", scoreRatingBar.getRating());
        movieScoreObject.put("comment", etComment.getText().toString());
        movieScoreObject.saveInBackground();


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
