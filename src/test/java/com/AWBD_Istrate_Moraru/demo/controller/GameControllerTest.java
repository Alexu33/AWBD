package com.AWBD_Istrate_Moraru.demo.controller;

import com.AWBD_Istrate_Moraru.demo.dto.*;
import com.AWBD_Istrate_Moraru.demo.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    GameService gameService;

    @MockitoBean
    GenreService genreService;

    @MockitoBean
    PublisherService publisherService;

    @MockitoBean
    DeveloperService developerService;

    @MockitoBean
    ReviewService reviewService;

    @Test
    @WithMockUser(username = "Bideo James", roles = {"USER"})
    void getGamePage() throws Exception {
        List<GameDto> gameDtos = List.of(new GameDto());
        PageImpl<GameDto> gamePage = new PageImpl<>(gameDtos);

        when(gameService.findPaginated(any(PageRequest.class)))
            .thenReturn(gamePage);
        when(genreService.findAll())
            .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/games"))
               .andExpect(status().isOk())
               .andExpect(view().name("gameList"))
               .andExpect(model().attributeExists("gamePage"))
               .andExpect(model().attributeExists("genreDtos"));
    }

    @Test
    @WithMockUser(username = "Bideo James", roles = {"USER"})
    void gameShow() throws Exception {
        long publisherId = 1L;
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setId(publisherId);

        long developerId = 1L;
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(developerId);

        long gameId = 1L;
        GameDto gameDto = new GameDto();
        gameDto.setId(gameId);
        gameDto.setPublisher(publisherDto);
        gameDto.setDeveloper(developerDto);

        when(gameService.findById(gameId)).thenReturn(gameDto);
        when(genreService.findAll()).thenReturn(Collections.emptyList());
        when(reviewService.findAllByGameId(gameId)).thenReturn(Collections.emptyList());
        when(publisherService.findById(publisherId)).thenReturn(publisherDto);
        when(developerService.findById(developerId)).thenReturn(developerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/games/{id}", gameId))
               .andExpect(status().isOk())
               .andExpect(view().name("gameShow"))
               .andExpect(model().attributeExists("gameDto"))
               .andExpect(model().attributeExists("genreDtos"))
               .andExpect(model().attributeExists("reviewDtos"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void gameFormGet() throws Exception {
        when(genreService.findAll()).thenReturn(Collections.emptyList());
        when(publisherService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/games/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("gameForm"))
               .andExpect(model().attributeExists("gameDto"))
               .andExpect(model().attributeExists("genreDtos"))
               .andExpect(model().attributeExists("publisherDtos"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void gameFormPost() throws Exception {
        GameDto gameDto = new GameDto();
        gameDto.setGenreIds(List.of(1L));

        when(genreService.findAllByIds(anyList())).thenReturn(List.of());
        when(publisherService.findById(anyLong())).thenReturn(new PublisherDto());

        mockMvc.perform(MockMvcRequestBuilders.post("/games/new")
                                              .param("genreIds", "1")
                                              .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/games"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void editGet() throws Exception {
        GameDto gameDto = new GameDto();
        gameDto.setId(1L);
        gameDto.setGenres(List.of(new GenreDto(1L, "Action")));

        when(gameService.findById(1L)).thenReturn(gameDto);
        when(genreService.findAll()).thenReturn(Collections.emptyList());
        when(publisherService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/games/edit/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(view().name("gameForm"))
               .andExpect(model().attributeExists("gameDto"))
               .andExpect(model().attributeExists("genreDtos"))
               .andExpect(model().attributeExists("publisherDtos"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void editPost() throws Exception {
        when(genreService.findAllByIds(anyList())).thenReturn(Collections.emptyList());
        when(publisherService.findById(anyLong())).thenReturn(new PublisherDto());

        mockMvc.perform(MockMvcRequestBuilders.post("/games/edit/{id}", 1L)
                                              .param("genreIds", "1")
                                              .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/games"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void deleteGame() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/games/delete/{id}", 1L))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/games"));

        verify(gameService).deleteById(1L);
    }

    @Test
    @WithMockUser
    void gameFormPost_emptyGenre() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/games/new")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }

    @Test
    @WithMockUser
    void editPost_invalidId() throws Exception {
        when(genreService.findAllByIds(anyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.post("/games/edit/{id}", -1L)
                        .param("genreIds", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }
}
