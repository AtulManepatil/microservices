package io.javabrains.movieinfoservice.dto;

import io.javabrains.movieinfoservice.encrypt.MaskData;

public class MovieDTO {
    private Integer movieId;
    private String name;
    @MaskData
    private String description;

    public MovieDTO() {
    }

    public MovieDTO(Integer movieId, String name, String description) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getMovieId() {
        return this.movieId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}