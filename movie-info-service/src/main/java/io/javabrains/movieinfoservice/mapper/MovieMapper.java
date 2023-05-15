package io.javabrains.movieinfoservice.mapper;

import io.javabrains.movieinfoservice.dto.MovieDTO;
import io.javabrains.movieinfoservice.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper extends GenericMapper<Movie, MovieDTO> {
    @Override
    @Mapping(target = "movieId", ignore = true)
    Movie asEntity(MovieDTO dto);
}