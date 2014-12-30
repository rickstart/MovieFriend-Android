package com.rickstart.moviefriend.models;

import java.io.Serializable;

/**
 * Created by mobilestudio03 on 08/12/14.
 */
public class Movie implements Serializable{
    private String poster;
    private String title;
    private double rating;
    private int year;
    private String releaseDate;
    private String synopsis;
    private Casting casting;
    private String runtime;


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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Casting getCasting() {
        return casting;
    }

    public void setCasting(Casting casting) {
        this.casting = casting;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
}
