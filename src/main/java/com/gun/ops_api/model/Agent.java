package com.gun.ops_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Agent {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do agente é obrigatório")
    private String name;

    @Column(unique = true)
    @NotBlank (message = "Codinome do agente é obrigatório")
    private String codename;

    @NotNull (message = "A especialidade do agente é obrigatório")
    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Enumerated (EnumType.STRING)
    private AgentStatus status = AgentStatus.AVAILABLE;
}
