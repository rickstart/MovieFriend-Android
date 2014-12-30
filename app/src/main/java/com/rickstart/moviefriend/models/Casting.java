package com.rickstart.moviefriend.models;

import java.io.Serializable;

/**
 * Created by mobilestudio03 on 15/12/14.
 */
public class Casting implements Serializable {
    private String name;
    private String character[];


    public Casting() {
    }

    public Casting(String name,String character[]){
        this.name=name;
        this.character=character;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String [] getCharacter() {
        return character;
    }

    public void setCharacter(String character[]) {
        this.character = character;
    }
}
