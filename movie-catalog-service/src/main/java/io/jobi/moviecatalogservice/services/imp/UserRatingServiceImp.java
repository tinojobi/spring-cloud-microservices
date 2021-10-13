package io.jobi.moviecatalogservice.services.imp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.jobi.moviecatalogservice.models.Rating;
import io.jobi.moviecatalogservice.models.UserRating;
import io.jobi.moviecatalogservice.services.IUserRatingService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingServiceImp implements IUserRatingService {
    private final RestTemplate restTemplate;

    public UserRatingServiceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getUserRatingFallback")
    @Override
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
    }

    private UserRating getUserRatingFallback(String userId) {
        UserRating userRating=new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(new Rating("0",0)));
        return userRating;
    }
}
