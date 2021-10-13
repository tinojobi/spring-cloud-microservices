package io.javabrains.ratingsdataservice.resources;

import io.javabrains.ratingsdataservice.model.Rating;
import io.javabrains.ratingsdataservice.model.UserRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
@Slf4j
public class RatingsResource {

    private final Environment environment;

    public RatingsResource(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/movies/{movieId}")
    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
        log.info("Fetching rating for movie with id {}. Server port : {}",movieId,environment.getProperty("local.server.port"));
        return new Rating(movieId, 4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        log.info("Fetching info for user with id {}. Server port : {}",userId,environment.getProperty("local.server.port"));

        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;

    }

}
