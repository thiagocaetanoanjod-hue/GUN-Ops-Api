package com.gun.ops_api.service;

import com.gun.ops_api.exception.BusinessRuleException;
import com.gun.ops_api.model.Agent;
import com.gun.ops_api.model.AgentStatus;
import com.gun.ops_api.model.Mission;
import com.gun.ops_api.model.ThreatLevel;
import com.gun.ops_api.repository.AgentRepository;
import com.gun.ops_api.repository.MissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MissionServiceTest {
    
    @Mock
    private MissionRepository missionRepository;

    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private MissionService missionService;

    @Test
    @DisplayName ("Deve alocar agente disponível com sucesso para a missão")
    void shuldAssignAvailableAgentSuccessfully() {
        // Arrange (Preparação)
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setTitle("Resgate em Green Hill");

        Agent sonic = new Agent();
        sonic.setId(1L);
        sonic.setCodename("BLUE_BLUR")  ;
        sonic.setStatus(AgentStatus.AVAILABLE);

        when(missionRepository.findById(1L)).thenReturn(Optional.of(mission));
        when(agentRepository.findById(10L)).thenReturn(Optional.of(sonic));
        when(missionRepository.save(any(Mission.class))).thenReturn(mission);

        // Act (Execução)
        Mission result = missionService.assingAgent(1L, 10L);

        // Assert (Verificação)
        assertEquals(AgentStatus.ON_MISSION, sonic.getStatus());
        assertTrue(result.getAssignedAgents().contains(sonic));
        verify(agentRepository, times(1)).save(sonic);
        verify(missionRepository, times(1)).save(mission);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar alocar agente ferido")
    void shouldThrowExceptionWhenAgentIsInjured() {
        // Arrange
        Mission mission = new Mission();
        mission.setId(1L);

        Agent knuckles = new Agent();
        knuckles.setId(20L);
        knuckles.setCodename("RED_ECHIDNA");
        knuckles.setStatus(AgentStatus.INJURED);

        when(missionRepository.findById(1L)).thenReturn(Optional.of(mission));
        when(agentRepository.findById(20L)).thenReturn(Optional.of(knuckles));

        // Act & Assert
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> {
            missionService.assingAgent(1L, 20L);
        });

        assertTrue(ex.getMessage().contains("indisponível"));
        verify(missionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve impedir conclusão de missão OMEGA com menos de 2 agentes")
    void shouldThrowExceptionWhenCompletingOmegaMissionWithOneAgent() {
        // Arrange (Preparação)
        Mission omegaMission = new Mission();
        omegaMission.setId(2L);
        omegaMission.setThreatLevel(ThreatLevel.OMEGA);

        Agent tails = new Agent();
        tails.setId(30L);
        tails.setStatus(AgentStatus.ON_MISSION);

        omegaMission.getAssignedAgents().add(tails);

        when(missionRepository.findById(2L)).thenReturn(Optional.of(omegaMission));

        // Act & Assert (Execução e Verificação)
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> {
            missionService.completeMission(2L);
        });

        assertTrue(ex.getMessage().contains("OMEGA"));
        assertFalse(omegaMission.isCompleted());
    }
}
