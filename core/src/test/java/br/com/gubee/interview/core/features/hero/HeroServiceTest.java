package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsRepository;
import br.com.gubee.interview.model.dto.HeroDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

import static br.com.gubee.interview.core.features.hero.common.Constants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

    @InjectMocks
    private HeroService heroService;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private PowerStatsRepository powerStatsRepository;

    @Test
    void createHero_WithValidData_ReturnsHero(){
        UUID uuid = UUID.randomUUID();
        when(powerStatsRepository.createPowerStats(POWER_STATS_DTO)).thenReturn(uuid);
        when(heroRepository.createHero(HERO_DTO, uuid)).thenReturn(anyInt());

        HeroDto sut = heroService.createHero(HERO_DTO);

        assertThat(sut).isNotNull()
                .isEqualTo(HERO_DTO);
    }

    @Test
    void createHero_WithInvalidData_ThrowException(){
        when(heroRepository.findHeroByName(INVALID_HERO.getName()))
                .thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> heroService.createHero(INVALID_HERO))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void deleteHero_WithValidId_DoesNotThrowAnyException(){
        assertThatCode(() -> heroService.deleteHeroById(UUID.randomUUID()))
                                                        .doesNotThrowAnyException();
    }

    @Test
    void deleteHero_WithInvalidId_ThrowException(){
        UUID uuid = UUID.randomUUID();
        doThrow(new RuntimeException()).when(heroRepository).deleteById(uuid);

        assertThatThrownBy(() -> heroRepository.deleteById(uuid))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void findHeroById_WithValidId_ReturnsHero() {
        UUID uuid = UUID.randomUUID();
        when(heroRepository.findHeroById(uuid)).thenReturn(Optional.ofNullable(HERO_DTO));

        Optional<HeroDto> sut = heroService.findHeroById(uuid);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(HERO_DTO);
    }

    @Test
    void findHeroById_WithInvalidId_ReturnsEmpty() {
        UUID uuid = UUID.randomUUID();
        doThrow(new EntityNotFoundException()).when(heroRepository).findHeroById(uuid);

        assertThatThrownBy(() -> heroService.findHeroById(uuid))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findHeroByName_WithValidName_ReturnsHero() {
        when(heroRepository.findHeroByName(anyString())).thenReturn(Optional.ofNullable(HERO_DTO));

        Optional<HeroDto> sut = heroService.findHeroByName(HERO_DTO.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(HERO_DTO);
    }

    @Test
    void findHeroByName_WithInvalidName_ReturnsEmpty() {
        doThrow(new EntityNotFoundException()).when(heroRepository).findHeroByName(anyString());

        assertThatThrownBy(() -> heroService.findHeroByName(HERO_DTO.getName()))
                .isInstanceOf(EntityNotFoundException.class);
    }
}