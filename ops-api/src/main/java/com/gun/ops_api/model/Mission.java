package com.gun.ops_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Mission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Titulo da missão é obrigatório")
    private String title;

    @NotBlank(message = "A zona/localização da missão é obrigatória")
    private String location;

    @NotNull(message = "O nível de ameaça da missão é obrigatório")
    @Enumerated(EnumType.STRING)
    private ThreatLevel threatLevel;

    private boolean completed = false;

    @ManyToMany
    @JoinTable(
            name = "mission_agents",
            joinColumns = @JoinColumn(name = "mission_id"),
            inverseJoinColumns = @JoinColumn(name = "agent_id")
    )
    private List<Agent> assignedAgents = new ArrayList<>();
}