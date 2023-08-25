package br.com.gubee.interview.model.enums.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PowerStatsDto {
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
}
