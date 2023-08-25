package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.dto.HeroDto;
import br.com.gubee.interview.model.dto.PowerStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UUID createPowerStats(PowerStatsDto powerStatsDto) {
        var sql = """
                  INSERT INTO power_stats
                  (id, strength, agility, dexterity, intelligence)
                  VALUES (uuid_generate_v4(), ?, ?, ?, ?)
                  RETURNING id;
                  """;

        return jdbcTemplate.queryForObject(sql, UUID.class,
                powerStatsDto.getStrength(),
                powerStatsDto.getAgility(),
                powerStatsDto.getDexterity(),
                powerStatsDto.getIntelligence());
    }
    }
