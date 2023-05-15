package io.javabrains.movieinfoservice.utils;

import io.javabrains.movieinfoservice.dto.MovieDTO;
import io.javabrains.movieinfoservice.entity.Movie;

import java.util.Arrays;
import java.util.List;

public class MovieBuilder {
    public static Movie getEntity() {
        return new Movie(1,"title","Desc");
    }

    public static MovieDTO getDTO() {
        return new MovieDTO(1,"title","Desc");
    }

    public static List<MovieDTO> getListDTO() {
        return Arrays.asList(new MovieDTO(1,"title","Desc"),new MovieDTO(2,"title2","Desc2"));
    }

    public static List<Movie> getListEntities() {
        return Arrays.asList(new Movie(1,"title","Desc"),new Movie(2,"title2","Desc2"));

    }
}
