package com.TuT597.movies.movies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleIgnoringCaseContaining(String title);
}
