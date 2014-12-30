package com.rickstart.moviefriend.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rickstart.moviefriend.R;
import com.rickstart.moviefriend.logger.Log;
import com.rickstart.moviefriend.models.Movie;
import com.rickstart.moviefriend.util.ImageFetcher;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rick on 26/11/14.
 */
public class MovieAdapter  extends ArrayAdapter<Movie> {

    private final Context mContext;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private int mActionBarHeight = 0;
    private GridView.LayoutParams mImageViewLayoutParams;
    private ImageFetcher mImageFetcher;
    private static int imageWidth;
    private List<Movie> movies= new ArrayList<Movie>();

    public MovieAdapter(Context context,int imageWidth,List<Movie> movies,ImageFetcher mImageFetcher ) {
        super(context , R.layout.item_grid_movie ,movies);
        this.mContext = context;
        this.movies = movies;
        this.imageWidth = imageWidth;
        this.mImageFetcher = mImageFetcher;
    }

    @Override
    public int getCount() {
        // If columns have yet to be determined, return no items
        if (getNumColumns() == 0) {
            return 0;
        }

        // Size + number of columns for top empty row
        return movies.size() + mNumColumns;
    }

    @Override
    public Movie getItem(int position) {
        return position < mNumColumns ?
                null : movies.get(position - mNumColumns);
    }

    @Override
    public long getItemId(int position) {
        return position < mNumColumns ? 0 : position - mNumColumns;
    }

    @Override
    public int getViewTypeCount() {
        // Two types of views, the normal ImageView and the top row of empty views
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (position < mNumColumns) ? 1 : 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        Log.e("TEST_ADAP","1");
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        // Now handle the main ImageView thumbnails
        ImageView imageView;
        ViewHolder viewHolder = null;
        if (convertView == null) { // if it's not recycled, instantiate and initialize
            convertView = inflater.inflate(R.layout.item_grid_movie, null);
            viewHolder = new ViewHolder();
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.poster);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_titulo);
            viewHolder.rating=(RatingBar) convertView.findViewById(R.id.rb_score);
            viewHolder.position = position;
            convertView.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        double rating;
        Movie movie = movies.get(position);

        rating= movie.getRating()*0.01*5;


        viewHolder.imageURL = movie.getPoster();
        viewHolder.textView.setText(movie.getTitle());
        viewHolder.rating.setRating((float) rating);

        mImageFetcher.loadImage(movies.get(position).getPoster(), viewHolder.poster);


        return convertView;
        //END_INCLUDE(load_gridview_item)
    }

    /**
     * Sets the item height. Useful for when we know the column width so the height can be set
     * to match.
     *
     * @param height
     */
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams =
                new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        mImageFetcher.setImageSize(height);
        notifyDataSetChanged();
    }

    public void setNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    static class ViewHolder {

        int position;
        ImageView poster;
        TextView textView;
        Bitmap bitmap;
        String imageURL;
        String title;
        RatingBar rating;

    }


    /*
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_movie, null);
            viewHolder = new ViewHolder();
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.poster);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_titulo);
            viewHolder.rating=(RatingBar) convertView.findViewById(R.id.rb_score);
            viewHolder.position = position;
            convertView.setTag(viewHolder);
        }
        double rating;
        Movie movie = movies.get(position);

        rating= movie.getRating()*0.01*5;

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.imageURL = movie.getPoster();
        viewHolder.textView.setText(movie.getTitle());
        viewHolder.rating.setRating((float) rating);



        new DownloadAsyncTask(position).execute(viewHolder);
        return convertView;

    }

    static class ViewHolder {

        int position;
        ImageView poster;
        TextView textView;
        Bitmap bitmap;
        String imageURL;
        String title;
        RatingBar rating;

    }

    private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        private int position;

        public DownloadAsyncTask(int position) {
            this.position= position;

        }
        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            // TODO Auto-generated method stub
            //load image directly
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.imageURL);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                // TODO: handle exception
                Log.e("error", "Downloading Image Failed");
                viewHolder.bitmap = null;
            }

            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {

            if (result.bitmap != null && position == result.position) {
                result.poster.setImageBitmap(Bitmap.createScaledBitmap(result.bitmap, imageWidth, (imageWidth + (imageWidth / 3)), false));
            } else {

                result.poster.setImageResource(R.drawable.hobbit);

            }
        }
    }

*/




}