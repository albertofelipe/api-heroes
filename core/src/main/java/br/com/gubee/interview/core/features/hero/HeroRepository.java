package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.dto.HeroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
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

    public int updateHero(HeroDto heroDto, UUID id){
        var sql = """
                    UPDATE hero
                    SET name = ?, race =?, enabled = ?, updated_at = now()
                    WHERE id = ?
                  """;

        return jdbcTemplate.update(sql,
                heroDto.getName(),
                heroDto.getRace(),
                heroDto.isEnabled(),
                id);
    }

    public Optional<HeroDto> findHeroById(UUID id) {
        var sql = """
                    SELECT name, race, enabled, power_stats_id, strength, agility, dexterity, intelligence
                    FROM hero h
                    JOIN power_stats ps
                    ON h.power_stats_id = ps.id
                    WHERE h.id = ?;
                  """;

        return jdbcTemplate.query(sql, new HeroRowMapper(), id)
                .stream()
                .findFirst();
    }

    public void deleteById(UUID id) {
        var sql = """
                    DELETE FROM hero
                    WHERE id = ?;
                  """;
        jdbcTemplate.update(sql, id);
    }


    public UUID getIdFromPowerStats(UUID heroId){
        var sql = """
                    SELECT h.power_stats_id
                    FROM hero h
                    JOIN power_stats ps
                    ON h.power_stats_id = ps.id
                    WHERE h.id = ?;
                  """;

        return jdbcTemplate.queryForObject(sql, UUID.class, heroId);
    }

}

