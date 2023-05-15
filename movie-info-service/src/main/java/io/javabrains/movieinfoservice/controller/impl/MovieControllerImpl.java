package io.javabrains.movieinfoservice.controller.impl;

import io.javabrains.movieinfoservice.config.Credential;
import io.javabrains.movieinfoservice.controller.MovieController;
import io.javabrains.movieinfoservice.dto.MovieDTO;
import io.javabrains.movieinfoservice.entity.Movie;
import io.javabrains.movieinfoservice.mapper.MovieMapper;
import io.javabrains.movieinfoservice.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/movie")
@RestController
public class MovieControllerImpl implements MovieController {
    Logger logger = LoggerFactory.getLogger(MovieControllerImpl.class);
    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final Credential credential;

    @Value("${config-changes-test}")
    private String configChangesTest;

    public MovieControllerImpl(MovieService movieService, MovieMapper movieMapper,Credential credential) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.credential = credential;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO save(@RequestBody MovieDTO movieDTO) {
        logger.info("Username : {} , Password : {}",credential.getUsername(),credential.getPassword());
        Movie movie = movieMapper.asEntity(movieDTO);
        return movieMapper.asDTO(movieService.save(movie));
    }

    //this method can be access by user whose role is admin
    @Override
    @GetMapping("/{id}")
    @RolesAllowed("admin")
    public MovieDTO findById(@PathVariable("id") Integer id) {
        logger.info("in findById :{} configChangesTest :{}",id,configChangesTest);
        Movie movie = movieService.findById(id).orElse(null);
        return movieMapper.asDTO(movie);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        movieService.deleteById(id);
    }

    //this method can be access by user whose role is admin
    @Override
    @GetMapping
    public List<MovieDTO> list() {
        return movieMapper.asDTOList(movieService.findAll());
    }

    @Override
    @GetMapping("/page-query")
    public Page<MovieDTO> pageQuery(Pageable pageable) {
        Page<Movie> moviePage = movieService.findAll(pageable);
        List<MovieDTO> dtoList = moviePage
                .stream()
                .map(movieMapper::asDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, moviePage.getTotalElements());
    }

    @Override
    @PutMapping("/{id}")
    public MovieDTO update(@RequestBody MovieDTO movieDTO, @PathVariable("id") Integer id) {
        Movie movie = movieMapper.asEntity(movieDTO);
        movie.setMovieId(id);
        return movieMapper.asDTO(movieService.update(movie, id));
    }
}