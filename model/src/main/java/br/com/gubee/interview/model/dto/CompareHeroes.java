package br.com.gubee.interview.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompareHeroes {

    private UUID hero1;
    private UUID hero2;
    @JsonProperty(access = READ_ONLY)
    private int strengthDif;
    @JsonProperty(access = READ_ONLY)
    private int agilityDif;
    @JsonProperty(access = READ_ONLY)
    private int dexterityDif;
    @JsonProperty(access = READ_ONLY)
    private int intelligenceDif;

}
