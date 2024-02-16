package com.TuT597.movies.movies;

import com.TuT597.movies.reviews.Review;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "movie")
    private Set<Review> reviews = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
}
