package com.gun.ops_api.service;

import com.gun.ops_api.exception.BusinessRuleException;
import com.gun.ops_api.exception.ResourceNotFoundException;
import com.gun.ops_api.model.Agent;
import com.gun.ops_api.model.AgentStatus;
import com.gun.ops_api.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {
    
    private final AgentRepository agentRepository;

    public Agent createAgent(Agent agent) {
        if (agentRepository.existsByCodename(agent.getCodename())) {
            throw new BusinessRuleException("Já existe um agente com o codinome: " + agent.getCodename());
        }
        return agentRepository.save(agent);
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Agent getAgentById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agente não encontrado com o ID: " + id));
    }

    public Agent updateStatus(Long id, AgentStatus newStatus) {
        Agent agent = getAgentById(id);
        agent.setStatus(newStatus);
        return agentRepository.save(agent);
    }
}
