package com.TuT597.movies.movie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int rating;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    Movie(){}

    Movie(String title, int rating){
        this.title = title;
        this.rating = rating;
    }
}
