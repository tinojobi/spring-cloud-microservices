package io.jobi.moviecatalogservice.services.imp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.jobi.moviecatalogservice.models.CatalogItem;
import io.jobi.moviecatalogservice.models.Movie;
import io.jobi.moviecatalogservice.models.Rating;
import io.jobi.moviecatalogservice.services.ICatalogueItemService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogueItemServiceImp implements ICatalogueItemService {
    private final RestTemplate restTemplate;

    public CatalogueItemServiceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(
            fallbackMethod = "getCatalogItemFallback",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "5"), //check last 5 requests before making a decision
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),//if 50% of the requestVolume threshold have failed break circuit
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),//sleep for 5000 milliseconds before picking up again


            },
            threadPoolKey = "catalogueItemPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "20"),
                    @HystrixProperty(name = "maxQueueSize",value = "10"),
            }

    )
    @Override
    public CatalogItem getCatalogueItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    private CatalogItem getCatalogItemFallback(Rating rating) {
        return new CatalogItem("Movie not found","",rating.getRating());
    }
}
