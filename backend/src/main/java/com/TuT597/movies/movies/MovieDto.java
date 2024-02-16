package com.TuT597.movies.movies;

import com.TuT597.movies.reviews.ReviewDto;

import java.util.List;
import java.util.OptionalDouble;

public record MovieDto(String title, List<ReviewDto> reviews) {
    public MovieDto(Movie movie) {
        this(movie.getTitle(), movie.getReviews().stream().map(ReviewDto::new).toList());
    }

    public OptionalDouble getAverageRating() {
        return reviews.stream().mapToDouble(ReviewDto::rating).average();
    }

    public static MovieDto convertToDto(Movie movie) {
        return new MovieDto(movie.getTitle(), movie.getReviews().stream().map(ReviewDto::new).toList());
    }
}
