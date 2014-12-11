package com.rickstart.moviefriend;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.rickstart.moviefriend.models.Movie;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import util.GalleryUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieGridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieGridFragment extends Fragment {

    private static final String API_KEY = "35hg37n2zaybbwf7wncj9vgw";

    // the number of movies you want to get in a single request to their web server
    private static final int MOVIE_PAGE_LIMIT = 20;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private EditText searchBox;
    private Button searchButton;
    private ListView moviesList;
    GridView gvMovies;
    private int columnWidth;

    private GalleryUtils galleryUtils;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String query = "frozen";
    private OnFragmentInteractionListener mListener;


    public static MovieGridFragment newInstance(String param1, String param2) {
        MovieGridFragment fragment = new MovieGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieGridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View row = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        gvMovies = (GridView) row.findViewById(R.id.gvMovies);
        galleryUtils = new GalleryUtils(getActivity());

        // String[] list = new String[] {"Alex Rojas","Yussel Luna","Ricardo","4","5","6","7"};
        new RequestTask().execute("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + API_KEY + "&q="+query+"&page_limit=" + MOVIE_PAGE_LIMIT);

        //MovieAdapter adapter = new MovieAdapter (getActivity(), list);

        //gvMovies.setAdapter(adapter);

        return row;

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //if (!mNavigationDrawerFragment.isDrawerOpen()) {
        menu.clear();
        inflater.inflate(R.menu.grid_movies, menu);
        //    showGlobalContextActionBar();
        //}
        //super.onCreateOptionsMenu(menu, inflater);
    }

    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.grid_movies, menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) menu.findItem(R.id.menu_search).getActionView();

            search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {

                    //loadData(query);

                    return true;

                }

            });

        }

    }
    */

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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



    private class RequestTask extends AsyncTask<String, String, String>{
        // make a request to the specified url
        @Override
        protected String doInBackground(String... uri)
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try
            {
                // make a HTTP request
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK)
                {
                    // request successful - read the response and close the connection
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    Log.d ("salida: ",out.toString());
                    out.close();
                    responseString = out.toString();
                }
                else
                {
                    // request failed - close the connection
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            }
            catch (Exception e)
            {
                Log.d("Test", "Couldn't make a successful request!");
            }
            return responseString;
        }

        // if the request above completed successfully, this method will
        // automatically run so you can do something with the response
        @Override
        protected void onPostExecute(String response)
        {
            super.onPostExecute(response);

            if (response != null)
            {
                try
                {
                    // convert the String response to a JSON object,
                    // because JSON is the response format Rotten Tomatoes uses
                    JSONObject jsonResponse = new JSONObject(response);

                    ArrayList<Movie> movieArrayList =new ArrayList<Movie>();

                    // fetch the array of movies in the response
                    JSONArray movies = jsonResponse.getJSONArray("movies");



                    // add each movie's title to an array
                  //  String[] movieTitles = new String[movies.length()];
                   // String[] moviePoster = new String[movies.length()];
                    for (int i = 0; i < movies.length(); i++)
                    {
                        movieArrayList.add(new Movie());
                        JSONObject movie = movies.getJSONObject(i);
                        Log.e("MO_V", movie.toString());
                        //movieTitles[i] = movie.getString("title");
                        JSONObject posters= movie.getJSONObject("posters");
                       // moviePoster[i] = posters.getString("original").replace("_tmb","_ori");
                        movieArrayList.get(i).setTitle(movie.getString("title"));
                        movieArrayList.get(i).setPoster(posters.getString("original").replace("_tmb","_ori"));
                    }

                    Log.d("Test", jsonResponse.toString());
                    // update the UI
                    refreshMoviesList(movieArrayList);

                }
                catch (JSONException e)
                {

                    Log.d("Test", "Failed to parse the JSON response!");
                }
            }
        }
    }

    private void refreshMoviesList(ArrayList<Movie> movies)
    {

        initilizeGridLayout();
        MovieAdapter adapter = new MovieAdapter (getActivity(),columnWidth,movies);
        gvMovies.setAdapter(adapter);

    }


    private void initilizeGridLayout() {


        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GalleryUtils.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((galleryUtils.getScreenWidth() -(2*padding) - ((GalleryUtils.NUM_OF_COLUMNS + 1) * padding)) / GalleryUtils.NUM_OF_COLUMNS);

        gvMovies.setNumColumns(GalleryUtils.NUM_OF_COLUMNS);
        gvMovies.setColumnWidth(columnWidth);
        gvMovies.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gvMovies.setPadding((int) padding, (int) padding, (int) padding,(int) padding);
        gvMovies.setHorizontalSpacing((int) padding);
        gvMovies.setVerticalSpacing((int) padding);
    }

}
