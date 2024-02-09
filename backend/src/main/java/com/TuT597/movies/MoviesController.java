package com.TuT597.movies;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class MoviesController {
    private final MovieRepository movieRepository;

    public MoviesController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("findall")
    public Iterable<Movie> getAll() {
        return movieRepository.findAll();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Movie> getById(@PathVariable long id) {
        Optional<Movie> possibleMovie = movieRepository.findById(id);
        return possibleMovie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("search/titles/{title}")
    public ResponseEntity<Iterable<Movie>> findByTitle(@PathVariable String title) {
        List<Movie> movies = movieRepository.findByTitleIgnoringCaseContaining(title);
        if (!movies.isEmpty()) return ResponseEntity.ok(movies);
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("search/rating/{rating}")
    public ResponseEntity<Iterable<Movie>> findByRating(@PathVariable int rating){
        List<Movie> movies = movieRepository.findByRating(rating);
        if(!movies.isEmpty()) return ResponseEntity.ok(movies);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("add")
    public void add(@RequestBody Movie movie){
        movieRepository.save(movie);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id) {
        movieRepository.deleteById(id);
    }
}
