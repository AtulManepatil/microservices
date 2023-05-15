package io.javabrains.movieinfoservice.controller.impl;

import io.javabrains.movieinfoservice.entity.Movie;
import io.javabrains.movieinfoservice.mapper.MovieMapper;
import io.javabrains.movieinfoservice.service.MovieService;
import io.javabrains.movieinfoservice.utils.MovieBuilder;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MovieControllerImplTest {
    //TODO: create the data Test generator class MovieBuilder
    private static final String ENDPOINT_URL = "/api/movie";

    @InjectMocks
    private MovieControllerImpl movieController;
    @Mock
    private MovieService movieService;
    @Mock
    private MovieMapper movieMapper;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.movieController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(movieMapper.asDTOList(ArgumentMatchers.any())).thenReturn(MovieBuilder.getListDTO());

        Mockito.when(movieService.findAll()).thenReturn(MovieBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(movieMapper.asDTO(ArgumentMatchers.any())).thenReturn(MovieBuilder.getDTO());

        Mockito.when(movieService.findById(ArgumentMatchers.anyInt())).thenReturn(java.util.Optional.of(MovieBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieId", Is.is(1)));
        Mockito.verify(movieService, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(movieService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(movieMapper.asEntity(ArgumentMatchers.any())).thenReturn(MovieBuilder.getEntity());
        Mockito.when(movieService.save(ArgumentMatchers.any(Movie.class))).thenReturn(MovieBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(MovieBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(movieService, Mockito.times(1)).save(ArgumentMatchers.any(Movie.class));
        Mockito.verifyNoMoreInteractions(movieService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(movieMapper.asEntity(ArgumentMatchers.any())).thenReturn(MovieBuilder.getEntity());
        Mockito.when(movieService.update(ArgumentMatchers.any(), ArgumentMatchers.anyInt())).thenReturn(MovieBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(MovieBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(movieService, Mockito.times(1)).update(ArgumentMatchers.any(Movie.class), ArgumentMatchers.anyInt());
        Mockito.verifyNoMoreInteractions(movieService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(movieService).deleteById(ArgumentMatchers.anyInt());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(movieService, Mockito.times(1)).deleteById(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(movieService);
    }
}