package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.dto.CompareHeroes;
import br.com.gubee.interview.model.dto.HeroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HeroDto create(@Validated @RequestBody HeroDto heroDto) {
        return heroService.createHero(heroDto);
    }


    @PutMapping(value = "/{heroId}")
    public ResponseEntity<HeroDto> updateHero(@PathVariable UUID heroId,
                                              @RequestBody HeroDto heroDto){

        Optional<HeroDto> hero = heroService.findHeroById(heroId);
        if(hero.isPresent()){
            return ResponseEntity.ok(heroService.updateHero(heroDto, heroId));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{heroId}")
    public ResponseEntity<HeroDto> findHeroById(@PathVariable("heroId") UUID heroId){
        return heroService.findHeroById(heroId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping(value = "/{heroId}")
    public ResponseEntity<Void> deleteHeroById(@PathVariable("heroId") UUID heroId){
        Optional<HeroDto> hero = heroService.findHeroById(heroId);
        if(hero.isPresent()){
            heroService.deleteHeroById(heroId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}