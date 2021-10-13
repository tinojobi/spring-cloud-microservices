package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.ICatalogueItemService;
import io.javabrains.moviecatalogservice.services.IUserRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@Slf4j
public class CatalogResource {

    private final IUserRatingService userRatingService;
    private final ICatalogueItemService catalogueItemService;

    public CatalogResource(IUserRatingService userRatingService, ICatalogueItemService catalogueItemService) {
        this.userRatingService = userRatingService;
        this.catalogueItemService = catalogueItemService;
    }


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        log.info("Fetching movie catalogs for user {}",userId);
        UserRating userRating = userRatingService.getUserRating(userId);

        return userRating.getRatings().stream()
                .map(catalogueItemService::getCatalogueItem)
                .collect(Collectors.toList());
    }




}

