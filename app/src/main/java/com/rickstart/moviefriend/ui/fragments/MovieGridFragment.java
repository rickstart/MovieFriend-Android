package com.rickstart.moviefriend.ui.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.rickstart.moviefriend.R;
import com.rickstart.moviefriend.models.Movie;
import com.rickstart.moviefriend.models.Casting;
import com.rickstart.moviefriend.ui.adapters.MovieAdapter;
import com.rickstart.moviefriend.util.GalleryUtils;
import com.rickstart.moviefriend.util.ImageCache;
import com.rickstart.moviefriend.util.ImageFetcher;
import com.rickstart.moviefriend.util.Utils;

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
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private static final int MOVIE_PAGE_LIMIT = 20;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private EditText searchBox;
    private Button searchButton;
    private ListView moviesList;
    public GridView gvMovies;
    private int columnWidth;
    private ImageFetcher mImageFetcher;
    private GalleryUtils galleryUtils;
    private MovieAdapter movieAdapter;
    public ArrayList<Movie> movieArrayList;
    private Menu optionsMenu;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String query = "frozen";
    private OnFragmentInteractionListener mListener;
    private ImageCache.ImageCacheParams cacheParams;

    int mImageThumbSize;

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
        setHasOptionsMenu(true);
        galleryUtils = new GalleryUtils(getActivity());
        cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GalleryUtils.GRID_PADDING, r.getDisplayMetrics());
        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        columnWidth = (int) ((galleryUtils.getScreenWidth() -(2*padding) - ((GalleryUtils.NUM_OF_COLUMNS + 1) * padding)) / GalleryUtils.NUM_OF_COLUMNS);
        mImageFetcher = new ImageFetcher(getActivity(),mImageThumbSize,columnWidth);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);


        //new RequestTask().execute("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + API_KEY + "&q="+query+"&page_limit=" + MOVIE_PAGE_LIMIT);


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

        initilizeGridLayout();

        // String[] list = new String[] {"Alex Rojas","Yussel Luna","Ricardo","4","5","6","7"};


        gvMovies.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                // Pause fetcher to ensure smoother scrolling when flinging
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Before Honeycomb pause image loading on scroll to help with performance
                    if (!Utils.hasHoneycomb()) {
                        mImageFetcher.setPauseWork(true);
                    }
                } else {
                    mImageFetcher.setPauseWork(false);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });

        gvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movieArrayList.get(position));


                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.container, movieDetailFragment).addToBackStack(null)
                        .commit();


                Toast.makeText(getActivity(),movieArrayList.get(position).getTitle(),Toast.LENGTH_SHORT).show();


            }
        });
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

        this.optionsMenu = menu;
        menu.clear();
        inflater.inflate(R.menu.grid_movies, menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) menu.findItem(R.id.menu_search).getActionView();

            search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    //Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();

                    String query = s.trim().replaceAll(" +", "%20");
                    Toast.makeText(getActivity(),query,Toast.LENGTH_SHORT).show();

                    new RequestTask().execute("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + API_KEY + "&q="+query+"&page_limit=" + MOVIE_PAGE_LIMIT);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {


                    //loadData(query);

                    return true;

                }

            });


            //restoreActionBar();
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.airport_menuRefresh:
                Toast.makeText(getActivity(),"Refre",Toast.LENGTH_SHORT).show();
                setRefreshActionButtonState(true);
                // Complete with your code
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu
                    .findItem(R.id.airport_menuRefresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("GRIS");
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshMoviesList(movieArrayList);
    }

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


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setRefreshActionButtonState(true);
        }

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

            setRefreshActionButtonState(false);
            if (response != null)
            {
                try
                {
                    JSONObject jsonResponse = new JSONObject(response);
                    movieArrayList =new ArrayList<Movie>();
                    JSONArray movies = jsonResponse.getJSONArray("movies");


                    for (int i = 0; i < movies.length(); i++)
                    {
                        movieArrayList.add(new Movie());

                        JSONObject movie = movies.getJSONObject(i);

                        JSONObject release = movie.getJSONObject("release_dates");
                        JSONArray cast = movie.getJSONArray("abridged_cast");


                        // moviePoster[i] = posters.getString("original").replace("_tmb","_ori");


                        JSONObject posters= movie.getJSONObject("posters");
                        JSONObject rating= movie.getJSONObject("ratings");

                        movieArrayList.get(i).setTitle(movie.getString("title"));
                        movieArrayList.get(i).setRating(Float.parseFloat(rating.getString("audience_score")));
                        if(posters.has("original"))
                        movieArrayList.get(i).setPoster(posters.getString("original").replace("_tmb","_ori"));
                        movieArrayList.get(i).setYear(movie.optInt("year",0));
                        movieArrayList.get(i).setRuntime(movie.getString("runtime"));
                        if(release.has("theater"))
                        movieArrayList.get(i).setReleaseDate(release.getString("theater"));

                        movieArrayList.get(i).setSynopsis(movie.getString("synopsis"));
                        String characters[]=new String[1000];
                        for(int j=0;j<cast.length();j++){
                            JSONObject charactersJson = cast.getJSONObject(j);
                            if(charactersJson.has("characters")) {
                                Log.d("charactersJson ", charactersJson.toString());

                                movieArrayList.get(i).setCasting(new Casting());
                                movieArrayList.get(i).getCasting().setName(cast.getJSONObject(j).getString("name"));
                                Log.d("characters", j + ":" + charactersJson.getJSONArray("characters").getString(0));
                            }


                            //movieArrayList.get(i).getCasting(new Casting(cast.getJSONObject(j).getString("name"),characters));
                        }
                    }

                    // update the UI
                    Log.d("Test", "Adp1");
                    refreshMoviesList(movieArrayList);

                }
                catch (JSONException e)
                {

                    e.printStackTrace();
                    Log.d("Test", "Failed to parse the JSON response!");
                }
            }
        }
    }

    private void refreshMoviesList(ArrayList<Movie> movies)
    {

        if(movies!=null) {
            if(movies.size()==0)
                Toast.makeText(getActivity(),getResources().getString(R.string.empty_search),Toast.LENGTH_SHORT).show();
            else {
                movieAdapter = new MovieAdapter(getActivity(), columnWidth, movies, mImageFetcher);
                gvMovies.setAdapter(movieAdapter);
            }
        }
    }


    private void initilizeGridLayout() {


        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GalleryUtils.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((galleryUtils.getScreenWidth() -(2*padding) - ((GalleryUtils.NUM_OF_COLUMNS + 1) * padding)) / GalleryUtils.NUM_OF_COLUMNS);
        Log.e("WIDTH", ""+columnWidth);
        gvMovies.setNumColumns(GalleryUtils.NUM_OF_COLUMNS);
        gvMovies.setColumnWidth(columnWidth);
        gvMovies.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gvMovies.setPadding((int) padding, (int) padding, (int) padding,(int) padding);
        gvMovies.setHorizontalSpacing((int) padding);
        gvMovies.setVerticalSpacing((int) padding);
    }

}
