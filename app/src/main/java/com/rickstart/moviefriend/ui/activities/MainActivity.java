package com.rickstart.moviefriend.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.rickstart.moviefriend.MovieGridFragment;
import com.rickstart.moviefriend.R;
import com.rickstart.moviefriend.ui.fragments.MovieDetailFragment;
import com.rickstart.moviefriend.ui.fragments.NavigationDrawerFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private MovieGridFragment mMovieGridFragment;
    private MovieDetailFragment mMovieDetailFragment;
    private String global_variable;


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 1:
                mTitle = getString(R.string.mobile);
                mMovieDetailFragment = new MovieDetailFragment();
                mTitle = getString(R.string.title_section2);

                mMovieDetailFragment = new MovieDetailFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mMovieDetailFragment)
                        .commit();
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                mMovieGridFragment = new MovieGridFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mMovieGridFragment)
                        .commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }

        // update the main content by replacing fragments
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/
    }

    public void onSectionAttached(int number) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (number) {
            case 1:
                mTitle = getString(R.string.mobile);
                break;
            case 2:
                mMovieDetailFragment = new MovieDetailFragment();
                mTitle = getString(R.string.title_section2);

                mMovieDetailFragment = new MovieDetailFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mMovieDetailFragment)
                        .commit();

                break;
            case 3:
                mTitle = getString(R.string.title_section3);

                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
/*
            getMenuInflater().inflate(R.menu.grid_movies, menu);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

                SearchView search = (SearchView) menu.findItem(R.id.menu_search).getActionView();

                search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

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


                restoreActionBar();
            }*/

            restoreActionBar();
        }
        return false;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private static int section;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            TextView section_label = (TextView) rootView.findViewById(R.id.section_label);
            section_label.setText(ARG_SECTION_NUMBER+" "+section);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
            section = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

}
