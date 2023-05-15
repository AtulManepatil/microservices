package io.javabrains.movieinfoservice.service.impl;

import io.javabrains.movieinfoservice.dao.MovieRepository;
import io.javabrains.movieinfoservice.entity.Movie;
import io.javabrains.movieinfoservice.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movie save(Movie entity) {
        return repository.save(entity);
    }

    @Override
    public List<Movie> save(List<Movie> entities) {
        return (List<Movie>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Movie> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return (List<Movie>) repository.findAll();
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        Page<Movie> entityPage = repository.findAll(pageable);
        List<Movie> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Movie update(Movie entity, Integer id) {
        Optional<Movie> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}