package io.jobi.moviecatalogservice.services;

import io.jobi.moviecatalogservice.models.UserRating;

public interface IUserRatingService {
    UserRating getUserRating(String userId);
}
