package io.javabrains.movieinfoservice.controller;

import io.javabrains.movieinfoservice.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MovieController {

    public MovieDTO save(@RequestBody MovieDTO movie);

    public MovieDTO findById(@PathVariable("id") Integer id);

    public void delete(@PathVariable("id") Integer id);

    public List<MovieDTO> list();

    public Page<MovieDTO> pageQuery(Pageable pageable);

    public MovieDTO update(@RequestBody MovieDTO dto, @PathVariable("id") Integer id);
}