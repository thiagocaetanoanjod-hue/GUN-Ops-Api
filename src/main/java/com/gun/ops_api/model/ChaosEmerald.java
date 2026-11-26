package com.gun.ops_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class ChaosEmerald {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true)
    @NotBlank(message =  "A cor da Esmeralda do Caos é obrigatória")
    private String color;

    private String currentLocation;

    @OneToOne 
    @JoinColumn(name = "holder_agent_id", unique = true)
    private Agent holderAgent;
}
