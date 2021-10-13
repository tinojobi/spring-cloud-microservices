package io.jobi.moviecatalogservice.services;

import io.jobi.moviecatalogservice.models.CatalogItem;
import io.jobi.moviecatalogservice.models.Rating;

public interface ICatalogueItemService {
    CatalogItem getCatalogueItem(Rating rating);
}
