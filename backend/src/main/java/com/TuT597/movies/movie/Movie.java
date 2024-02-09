package com.TuT597.movies.movie;

import com.TuT597.movies.review.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    Movie() {}

    public Movie(String title) {
        this.title = title;
    }

    private Set<Review> reviews = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }
}
