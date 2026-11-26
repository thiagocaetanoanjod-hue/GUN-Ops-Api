package com.gun.ops_api.service;

import com.gun.ops_api.exception.BusinessRuleException;
import com.gun.ops_api.exception.ResourceNotFoundException;
import com.gun.ops_api.model.Agent;
import com.gun.ops_api.model.AgentStatus;
import com.gun.ops_api.model.Mission;
import com.gun.ops_api.model.ThreatLevel;
import com.gun.ops_api.repository.AgentRepository;
import com.gun.ops_api.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MissionService {
    
    private final MissionRepository missionRepository;
    private final AgentRepository agentRepository;

    public Mission createMission(Mission mission) {
        return missionRepository.save(mission);
    }

    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    public Mission getMissionById(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Missão não encontrada com o ID: " + id));
    }

    public Mission assingAgent(Long missionId, Long agentId) {
        Mission mission = getMissionById(missionId);
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agente não encontrado com o ID: " + agentId));

        if (mission.isCompleted()) {
            throw new BusinessRuleException("A missão já foi concluída e não pode receber novos agentes.");
        }

        if (agent.getStatus() != AgentStatus.AVAILABLE) {
            throw new BusinessRuleException("Agente " + agent.getCodename() + " indisponível (Status atual: " + agent.getStatus() + ")");
        }

        agent.setStatus(AgentStatus.ON_MISSION);
        mission.getAssignedAgents().add(agent);

        agentRepository.save(agent);
        return missionRepository.save(mission);
    }

    public Mission completeMission(Long missionId) {
        Mission mission = getMissionById(missionId);

        if (mission.isCompleted()) {
            throw new BusinessRuleException("A missão já foi concluída.");
        }
        
        if (mission.getThreatLevel() == ThreatLevel.OMEGA && mission.getAssignedAgents().size() < 2) {
            throw new BusinessRuleException("Missões de nível OMEGA devem ter pelo menos 2 agentes designados.");
        }

        mission.setCompleted(true);
        for (Agent agent : mission.getAssignedAgents()) {
            agent.setStatus(AgentStatus.AVAILABLE);
            agentRepository.save(agent);
        }

        return missionRepository.save(mission);
    }
}
