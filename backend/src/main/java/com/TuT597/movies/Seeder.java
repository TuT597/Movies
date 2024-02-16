package com.TuT597.movies;

import com.TuT597.movies.movies.Movie;
import com.TuT597.movies.movies.MovieRepository;
import com.TuT597.movies.reviews.Review;
import com.TuT597.movies.reviews.ReviewRepository;
import com.TuT597.movies.users.User;
import com.TuT597.movies.users.UserRepository;
import com.TuT597.movies.users.authorities.Authority;
import com.TuT597.movies.users.authorities.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public Seeder(MovieRepository movieRepository, UserRepository userRepository, ReviewRepository reviewRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {
            var up = new Movie("Up");
            var citizenKane = new Movie("Citizen Kane");
            var theGrandBudapest = new Movie("The Grand Budapest Hotel");
            var starWars = new Movie("Star Wars");
            movieRepository.saveAll(List.of(up, citizenKane, theGrandBudapest, starWars));

            var me = new User("TheWub", passwordEncoder.encode("password123"));
            var testUser = new User("nn", passwordEncoder.encode("abc"));
            userRepository.saveAll(List.of(me, testUser));

            var myRole = new Authority("TheWub", "ROLE_ADMIN");
            var testRole = new Authority("nn", "ROLE_USER");
            authorityRepository.saveAll(List.of(myRole, testRole));

            var myCitizenKaneReview = new Review(citizenKane, me, 2, "famous, but disappointing");
            var myUpReview = new Review(up, me, 5, "touching, surprising, and funny");
            var testGrandBudapestReview = new Review(theGrandBudapest,testUser, 3, "sometimes funny, but mostly mwuh");
            var testUpReview = new Review(up,testUser, 1, "I don't like cartoons");
            reviewRepository.saveAll(List.of(myCitizenKaneReview, myUpReview, testGrandBudapestReview, testUpReview));
        }
    }


}