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
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Rick on 26/11/14.
 */
public class MovieAdapter  extends ArrayAdapter<Movie> {
    private final Context context;
    private static int imageWidth;



    List<Movie> movies= new List<Movie>();


    public MovieAdapter(Context context, int imageWidth,List movies ) {
        super(context ,R.layout.item_grid_movie ,movies);
        this.context = context;
        this.movies = movies;
        this.imageWidth = imageWidth;
        this.titles=titles;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_grid_movie, parent, false);
        float rate = (float) 2.50;
        ImageView img = (ImageView) rowView.findViewById(R.id.poster);
        Log.e("POSTER ("+position+"):", movies[position]);
        Log.e("POSTER ("+position+"):", titles[position]);

        new DownloadImageTask(img)
                .execute(movies[position]);
        //img.setImageBitmap(loadBitmap(movies[position]));
        /*RatingBar rating = (RatingBar) rowView.findViewById(R.id.ratingBar);
        rating.setNumStars(5);
        rating.setRating(rate);*/

        return rowView;
    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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



            bmImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bmImage.setLayoutParams(new GridView.LayoutParams(imageWidth,
                    imageWidth));
            bmImage.setImageBitmap(result);

            // image view click listener
            bmImage.setOnClickListener(new OnImageClickListener(0));

            //bmImage.setImageBitmap(Bitmap.createScaledBitmap(result, 180, 200, false));
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