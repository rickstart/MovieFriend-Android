package com.rickstart.moviefriend.models;

/**
 * Created by mobilestudio03 on 15/12/14.
 */
public class Casting {
    private String name;
    private String character;

    public Casting(String name,String character){
        this.name=name;
        this.character=character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
