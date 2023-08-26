package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsRepository;
import br.com.gubee.interview.model.dto.CompareHeroes;
import br.com.gubee.interview.model.dto.HeroDto;
import br.com.gubee.interview.model.dto.PowerStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    @Autowired
    private final HeroRepository heroRepository;

    @Autowired
    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public HeroDto createHero(HeroDto heroDto) {
        if(heroRepository.findHeroByName(heroDto.getName()).isPresent()){
            throw new DataIntegrityViolationException("There is already a hero named " + heroDto.getName());
        }

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

    @Transactional
    public HeroDto updateHero(HeroDto heroDto, UUID id){
        if(heroRepository.findHeroByName(heroDto.getName()).isPresent()){
            throw new DataIntegrityViolationException("There is already a hero named " + heroDto.getName());
        }

        heroRepository.updateHero(heroDto, id);
        UUID powerStatsId = heroRepository.getIdFromPowerStats(id);
        powerStatsRepository.updatePowerStats(heroDto, powerStatsId);
        return heroDto;
    }

    @Transactional
    public void deleteHeroById(UUID id){
        UUID idToTbeDeleted = heroRepository.getIdFromPowerStats(id);
        heroRepository.deleteById(id);
        powerStatsRepository.deletePowerStats(idToTbeDeleted);
    }

    public Optional<HeroDto> findHeroById(UUID id) {
        return Optional.ofNullable(heroRepository.findHeroById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hero not found with ID: " + id)));
    }


    public Optional<HeroDto> findHeroByName(String name){
        return Optional.ofNullable(heroRepository.findHeroByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Hero not found with name: " + name)));
    }


    public CompareHeroes compareStatsFromHeroes(CompareHeroes compareHeroes){
        HeroDto hero1 = heroRepository.findHeroById(compareHeroes.getHero1())
                .orElseThrow(() -> new EntityNotFoundException("Hero 1 not found"));
        HeroDto hero2 = heroRepository.findHeroById(compareHeroes.getHero2())
                .orElseThrow(() -> new EntityNotFoundException("Hero 2 not found"));

        compareHeroes.setStrengthDif(hero1.getStrength() - hero2.getStrength());
        compareHeroes.setAgilityDif(hero1.getAgility() - hero2.getAgility());
        compareHeroes.setDexterityDif(hero1.getDexterity() - hero2.getDexterity());
        compareHeroes.setIntelligenceDif(hero1.getIntelligence() - hero2.getIntelligence());

        return compareHeroes;
    }

}