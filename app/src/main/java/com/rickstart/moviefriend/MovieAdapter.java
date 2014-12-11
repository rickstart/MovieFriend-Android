package com.rickstart.moviefriend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.net.URLConnection;
import com.rickstart.moviefriend.models.Movie;
import java.util.*;

import java.util.ArrayList;


/**
 * Created by Rick on 26/11/14.
 */
public class MovieAdapter  extends ArrayAdapter<Movie> {
    private final Context context;
    private static int imageWidth;



    List<Movie> movies= new ArrayList<Movie>();

    //String[] movies;
    //ArrayList<Bitmap> posters = new ArrayList<Bitmap>();


    public MovieAdapter(Context context, int imageWidth,List<Movie> movies ) {
        super(context ,R.layout.item_grid_movie ,movies);
        this.context = context;
        this.movies = movies;
        this.imageWidth = imageWidth;


    }

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
            convertView.setTag(viewHolder);
        }
        Movie movie = movies.get(position);

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.imageURL = movie.getPoster();
        viewHolder.textView.setText(movie.getTitle());
        new DownloadAsyncTask().execute(viewHolder);
        return convertView;

    }
/*
        @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.item_grid_movie, parent, false);


        ImageView img = (ImageView) rowView.findViewById(R.id.poster);
<<<<<<< HEAD
        TextView tituloTV = (TextView) rowView.findViewById(R.id.tv_titulo);
        Log.e("POSTER ("+position+"):", movies.get(position).getTitle());
        tituloTV.setText(movies.get(position).getTitle());

        new DownloadImageTask(img)
                .execute(movies.get(position).getPoster());
=======
        Log.e("POSTER", movies[position]);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setOnClickListener(new OnImageClickListener(position));
        try{

            img.setImageBitmap(Bitmap.createScaledBitmap(posters.get(position), imageWidth, (imageWidth + (imageWidth / 3)), false));

        }
        catch (Exception e){
            new DownloadImageTask(img, position)
                    .execute(movies[position]);

        }

            return rowView;

>>>>>>> 32268a008ca2b3759a40662178a4cca249065e27
        //img.setImageBitmap(loadBitmap(movies[position]));
        /*RatingBar rating = (RatingBar) rowView.findViewById(R.id.ratingBar);
        rating.setNumStars(5);
        rating.setRating(rate);

        //
    }
*/

    static class ViewHolder {

        ImageView poster;
        TextView textView;
        Bitmap bitmap;
        String imageURL;
        String title;

    }

    private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

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
            // TODO Auto-generated method stub
            if (result.bitmap == null) {
                result.poster.setImageResource(R.drawable.hobbit);
            } else {
                result.poster.setImageBitmap(Bitmap.createScaledBitmap(result.bitmap, imageWidth, (imageWidth + (imageWidth / 3)), false));
            }
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        int position;

        public DownloadImageTask(ImageView bmImage, int position) {


            this.bmImage = bmImage;
            this.position = position;

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {


            //posters.add(position,result);
            bmImage.setImageBitmap(Bitmap.createScaledBitmap(result, imageWidth, (imageWidth + (imageWidth/3)), false));
            //bmImage.setImageBitmap(result);
        }
    }
    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            /*
            FullScreenImageFragment fullScreen = FullScreenImageFragment.newInstance(_postion, picturePaths);

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fullScreen, FullScreenImageFragment.TAG);
            fragmentTransaction.commit();*/

        }
    }



}