package com.TuT597.movies;

import com.TuT597.movies.authorities.MARole;
import com.TuT597.movies.movies.Movie;
import com.TuT597.movies.movies.MovieRepository;
import com.TuT597.movies.reviews.Review;
import com.TuT597.movies.reviews.ReviewRepository;
import com.TuT597.movies.users.UserService;
import com.TuT597.movies.authorities.Authority;
import com.TuT597.movies.authorities.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final ReviewRepository reviewRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {
            var up = new Movie("Up");
            var citizenKane = new Movie("Citizen Kane");
            var theGrandBudapest = new Movie("The Grand Budapest Hotel");
            var starWars = new Movie("Star Wars");
            movieRepository.saveAll(List.of(up, citizenKane, theGrandBudapest, starWars));

            var me = userService.save("TheWub", "password123", MARole.ADMIN);
            var testUser = userService.save("nn", "abc", MARole.USER);

            var myCitizenKaneReview = new Review(citizenKane, me, 2, "famous, but disappointing");
            var myUpReview = new Review(up, me, 5, "touching, surprising, and funny");
            var testGrandBudapestReview = new Review(theGrandBudapest,testUser, 3, "sometimes funny, but mostly mwuh");
            var testUpReview = new Review(up,testUser, 1, "I don't like cartoons");
            reviewRepository.saveAll(List.of(myCitizenKaneReview, myUpReview, testGrandBudapestReview, testUpReview));
        }
    }
}