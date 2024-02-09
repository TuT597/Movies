package com.TuT597.movies.review;

import com.TuT597.movies.movie.Movie;
import com.TuT597.movies.user.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private User user;
    private Movie movie;
    private int rating;
    private String text;

    Review(){}

    public Review(User user, Movie movie, int rating, String text) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }
}
