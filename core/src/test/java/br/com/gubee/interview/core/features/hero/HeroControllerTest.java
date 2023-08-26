package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.dto.HeroDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

import static br.com.gubee.interview.core.features.hero.common.Constants.HERO_DTO;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HeroController.class)
class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HeroService heroService;

    @Test
    void createPlanet_WithValidData_ReturnsCreated() throws Exception{
        when(heroService.createHero(HERO_DTO)).thenReturn(HERO_DTO);

        mockMvc.perform(post("/api/v1/heroes")
                        .content(objectMapper.writeValueAsString(HERO_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$").value(HERO_DTO));
    }

    @Test
    void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception{
        HeroDto emptyHero = new HeroDto();

        mockMvc.perform(post("/api/v1/heroes")
                        .content(objectMapper.writeValueAsString(emptyHero))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void updateHero_WithValidData_ReturnsHero() throws Exception{
        UUID uuidHero = UUID.randomUUID();

        when(heroService.findHeroById(uuidHero)).thenReturn(Optional.of(HERO_DTO));
        when(heroService.updateHero(HERO_DTO, uuidHero)).thenReturn(HERO_DTO);

        mockMvc.perform(put("/api/v1/heroes/" + uuidHero)
                .content(objectMapper.writeValueAsString(HERO_DTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(HERO_DTO));
    }

    @Test
    void updateHero_WithInvalidData_ReturnsBadRequest() throws Exception{
        UUID uuidHero = UUID.randomUUID();

        when(heroService.findHeroById(uuidHero)).thenReturn(Optional.empty());
        when(heroService.updateHero(HERO_DTO, uuidHero)).thenReturn(null);

        mockMvc.perform(put("/api/v1/heroes/" + uuidHero)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findHeroByName_WithValidName_ReturnsHero() throws Exception{
        when(heroService.findHeroByName(anyString())).thenReturn(Optional.ofNullable(HERO_DTO));

        mockMvc.perform(get("/api/v1/heroes/name/" + HERO_DTO.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(HERO_DTO));
    }

    @Test
    void findHeroByName_WithInvalidName_ReturnsNotFound() throws Exception{
        mockMvc.perform(get("/api/v1/heroes/name/name"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findHeroById_WithValidId_ReturnsHero() throws Exception{
        when(heroService.findHeroById(any())).thenReturn(Optional.ofNullable(HERO_DTO));

        mockMvc.perform(get("/api/v1/heroes/" + UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(HERO_DTO));

    }

    @Test
    void findHeroById_WithInvalidId_ReturnsNotFound() throws Exception{
        mockMvc.perform(get("/api/v1/heroes/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteHeroById_WithValidId_ReturnsNoContent() throws Exception {
        UUID heroId = UUID.randomUUID();
        when(heroService.findHeroById(heroId)).thenReturn(Optional.of(HERO_DTO));

        mockMvc.perform(delete("/api/v1/heroes/" + heroId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteHeroById_WithInvalidId_ReturnsNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();
        doThrow(new EntityNotFoundException()).when(heroService).deleteHeroById(uuid);

        mockMvc.perform(delete("/api/v1/heroes/" + uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    void compareStatsFromHeroes() {
    }
}