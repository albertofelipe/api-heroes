package br.com.gubee.interview.core.features.hero.common;

import br.com.gubee.interview.model.dto.HeroDto;
import br.com.gubee.interview.model.dto.PowerStatsDto;

import static br.com.gubee.interview.model.enums.Race.HUMAN;

public class Constants {

    public static final HeroDto HERO_DTO = HeroDto.builder()
            .name("batman")
            .race(String.valueOf(HUMAN))
            .strength(10)
            .agility(8)
            .dexterity(7)
            .intelligence(9)
            .build();

    public static final HeroDto INVALID_HERO = HeroDto.builder()
            .name("")
            .race(String.valueOf(HUMAN))
            .strength(10)
            .agility(0)
            .dexterity(7)
            .intelligence(-1)
            .build();

    public static final PowerStatsDto POWER_STATS_DTO = PowerStatsDto.builder()
            .strength(10)
            .agility(8)
            .dexterity(7)
            .intelligence(9)
            .build();

}