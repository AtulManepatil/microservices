package io.javabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@RefreshScope
public class CatalogResource {

    Logger logger = LoggerFactory.getLogger(CatalogResource.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Value("${microservice.endpoints.ratings-data}")
    private String ratingsDataServiceUrl;

    @Value("${microservice.endpoints.movie-info}")
    private String movieInfoServiceUrl;

    @Value("${config-changes-test}")
    private String configChangesTest;

    @GetMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "callGetCatalog_Fallback")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        logger.info("in getCatalog ratingsDataServiceUrl :{} configChangesTest :{}",ratingsDataServiceUrl,configChangesTest);
        UserRating userRating = restTemplate.getForObject(ratingsDataServiceUrl+"ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject(movieInfoServiceUrl+"api/movie/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());

    }

    public List<CatalogItem> callGetCatalog_Fallback(@PathVariable("userId") String userId) {
        logger.info("in callGetCatalog_Fallback userId :", userId);
      return null;
    }
}

/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/