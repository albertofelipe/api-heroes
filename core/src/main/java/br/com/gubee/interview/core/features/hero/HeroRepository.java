package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.dto.HeroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class HeroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createHero(HeroDto heroDto, UUID idPowerStats) {
        var sql = """
                  INSERT INTO hero
                  (id, name, race, power_stats_id)
                  VALUES(uuid_generate_v4(), ?, ?, ?);
                  """;

        return jdbcTemplate.update(sql,
                heroDto.getName(),
                heroDto.getRace(),
                idPowerStats);
    }
}

