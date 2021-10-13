package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.UserRating;

public interface IUserRatingService {
    UserRating getUserRating(String userId);
}
