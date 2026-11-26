package com.gun.ops_api.controller;

import com.gun.ops_api.model.Agent;
import com.gun.ops_api.model.AgentStatus;
import com.gun.ops_api.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<Agent> create(@RequestBody @Valid Agent agent) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agentService.createAgent(agent));
    }

    @GetMapping
    public ResponseEntity<List<Agent>> listAll() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getById(@PathVariable Long id) {
        return ResponseEntity.ok(agentService.getAgentById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Agent> updateStatus(@PathVariable Long id, @RequestParam AgentStatus status) {
        return ResponseEntity.ok(agentService.updateStatus(id, status));
    }
}