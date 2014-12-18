package com.rickstart.moviefriend.models;

/**
 * Created by mobilestudio03 on 08/12/14.
 */
public class Movie {
    private String poster;
    private String title;
    private double rating;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
