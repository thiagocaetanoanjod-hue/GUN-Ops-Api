package com.gun.ops_api.controller;

import com.gun.ops_api.model.Mission;
import com.gun.ops_api.service.MissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {
    
    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<Mission> create(@RequestBody @Valid Mission mission) {
        return ResponseEntity.status(HttpStatus.CREATED).body(missionService.createMission(mission));
    }

    @GetMapping
    public ResponseEntity<List<Mission>> listAll() {
        return ResponseEntity.ok(missionService.getAllMissions());
    }

    @PostMapping("/{missionId}/assign/{agentId}")
    public ResponseEntity<Mission> assignAgent(@PathVariable Long missionId, @PathVariable Long agentId) {
        return ResponseEntity.ok(missionService.assingAgent(missionId, agentId));
    }

    @PatchMapping("/{missionId}/complete")
    public ResponseEntity<Mission> completeMission(@PathVariable Long missionId) {
        return ResponseEntity.ok(missionService.completeMission(missionId));
    }  
}
