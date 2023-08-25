package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsRepository;
import br.com.gubee.interview.model.dto.CompareHeroes;
import br.com.gubee.interview.model.dto.HeroDto;
import br.com.gubee.interview.model.dto.PowerStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private PowerStatsRepository powerStatsRepository;

    @Transactional
    public HeroDto createHero(HeroDto heroDto) {

        PowerStatsDto powerStatsDto = new PowerStatsDto(
                heroDto.getStrength(),
                heroDto.getAgility(),
                heroDto.getDexterity(),
                heroDto.getIntelligence()
        );

        UUID idPowerStats = powerStatsRepository.createPowerStats(powerStatsDto);
        heroRepository.createHero(heroDto, idPowerStats);
        return heroDto;
    }
}