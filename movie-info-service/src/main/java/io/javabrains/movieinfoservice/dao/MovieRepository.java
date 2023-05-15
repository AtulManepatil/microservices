package io.javabrains.movieinfoservice.dao;

import io.javabrains.movieinfoservice.entity.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer> {
}