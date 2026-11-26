package com.gun.ops_api.service;

import com.gun.ops_api.exception.BusinessRuleException;
import com.gun.ops_api.exception.ResourceNotFoundException;
import com.gun.ops_api.model.Agent;
import com.gun.ops_api.model.AgentStatus;
import com.gun.ops_api.model.ChaosEmerald;
import com.gun.ops_api.repository.AgentRepository;
import com.gun.ops_api.repository.ChaosEmeraldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChaosEmeraldService {
    
    private final ChaosEmeraldRepository chaosEmeraldRepository;
    private final AgentRepository agentRepository;

    public ChaosEmerald registerEmerald(ChaosEmerald chaosEmerald) {
        return chaosEmeraldRepository.save(chaosEmerald);
    }

    public List<ChaosEmerald> getAllEmeralds() {
        return chaosEmeraldRepository.findAll();
    }

    public ChaosEmerald assingToAgent(Long emeraldId, Long agentId) {
        ChaosEmerald emerald = chaosEmeraldRepository.findById(emeraldId).orElseThrow(() -> new ResourceNotFoundException("Esmeralda do Caos não encontrada com o ID: " + emeraldId));
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agente não encontrado com o ID: " + agentId));

        if (agent.getStatus() == AgentStatus.INJURED) {
            throw new BusinessRuleException("O agente está ferido e não pode receber a Esmeralda do Caos.");
        }

        emerald.setHolderAgent(agent);
        emerald.setCurrentLocation("Com o agente " + agent.getCodename());
        return chaosEmeraldRepository.save(emerald);
    }
}
