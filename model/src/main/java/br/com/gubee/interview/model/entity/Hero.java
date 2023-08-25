package br.com.gubee.interview.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hero {

    private UUID id;
    private String name;
    private String race;
    private UUID powerStatsId;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;

}