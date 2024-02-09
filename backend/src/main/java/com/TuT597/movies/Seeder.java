package com.TuT597.movies;

import com.TuT597.movies.movie.Movie;
import com.TuT597.movies.movie.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    private final MovieRepository movieRepository;

    public Seeder(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String...args) throws Exception {
        if (movieRepository.count() == 0){
            var seedMovies = List.of(
                    new Movie("Up"),
                    new Movie("Citizen Kane"),
                    new Movie("The Grand Budapest Hotel")
                    );
            for (Movie movie: seedMovies) movieRepository.save(movie);
        }
    }
}
