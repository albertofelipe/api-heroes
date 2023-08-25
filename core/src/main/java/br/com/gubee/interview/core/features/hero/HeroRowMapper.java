package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.dto.HeroDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroRowMapper implements RowMapper<HeroDto> {
    @Override
    public HeroDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HeroDto(
                rs.getString("name"),
                rs.getString("race"),
                rs.getInt("strength"),
                rs.getInt("agility"),
                rs.getInt("dexterity"),
                rs.getInt("intelligence"),
                rs.getBoolean("enabled")
        );
    }
}
