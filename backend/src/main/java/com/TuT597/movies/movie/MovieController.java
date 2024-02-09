package com.TuT597.movies.movie;

import com.TuT597.movies.exceptions.InconsistentIdException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
public class MovieController {
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("findall")
    public Iterable<Movie> getAll(Pageable pageable) {
        return movieRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), 5),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "rating"))
        ));
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Movie> getById(@PathVariable long id) {
        Optional<Movie> possibleMovie = movieRepository.findById(id);
        return possibleMovie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("search/titles/{title}")
    public ResponseEntity<Iterable<Movie>> findByTitle(@PathVariable String title) {
        List<Movie> movies = movieRepository.findByTitleIgnoringCaseContaining(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("search/rating/{rating}")
    public ResponseEntity<Iterable<Movie>> findByRating(@PathVariable int rating){
        List<Movie> movies = movieRepository.findByRating(rating);
        return ResponseEntity.ok(movies);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody Movie movie, UriComponentsBuilder ucb) {
        if (movie.getId() != null) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    "the body of this POST request can not include an ID, as it is assigned by the database");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        movieRepository.save(movie);
        URI locationOfNewMovie = ucb
                .path("{id}")
                .buildAndExpand(movie.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewMovie).body(movie);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> replace(@RequestBody Movie movie, @PathVariable long id) {
        var idFromBody = movie.getId();
        if (idFromBody != null && idFromBody != id) {
            var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    "ids given by path and body are inconsistent, and the id of an item should not be changed");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        var possibleOriginalMovie = movieRepository.findById(id);
        if (possibleOriginalMovie.isEmpty()) return ResponseEntity.notFound().build();
        movie.setId(id);
        movieRepository.save(movie);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("patch/{id}")
    public ResponseEntity<?> patch(@RequestBody Movie changedMovie, @PathVariable long id) {
        var idFromBody = changedMovie.getId();
        if (idFromBody != null && idFromBody != id) throw new InconsistentIdException();
        var possibleOriginalMovie = movieRepository.findById(id);
        if (possibleOriginalMovie.isEmpty()) return ResponseEntity.notFound().build();

        var movie = possibleOriginalMovie.get();
        var newTitle = changedMovie.getTitle();
        if (newTitle!= null) movie.setTitle(newTitle);

        movieRepository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if(movieRepository.findById(id).isPresent()) {
            movieRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
