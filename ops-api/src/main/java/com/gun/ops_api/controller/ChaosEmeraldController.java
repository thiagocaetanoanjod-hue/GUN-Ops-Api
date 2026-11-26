package com.gun.ops_api.controller;

import com.gun.ops_api.model.ChaosEmerald;
import com.gun.ops_api.service.ChaosEmeraldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emeralds")
@RequiredArgsConstructor
public class ChaosEmeraldController {
    
    private final ChaosEmeraldService chaosEmeraldService;

    @PostMapping
    public ResponseEntity<ChaosEmerald> create(@RequestBody @Valid ChaosEmerald chaosEmerald) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chaosEmeraldService.registerEmerald(chaosEmerald));
    }

    @GetMapping
    public ResponseEntity<List<ChaosEmerald>> listAll() {
        return ResponseEntity.ok(chaosEmeraldService.getAllEmeralds());
    }

    @PostMapping("/{emeraldId}/assign/{agentId}")
    public ResponseEntity<ChaosEmerald> assignToAgent(@PathVariable Long emeraldId, @PathVariable Long agentId) {
        return ResponseEntity.ok(chaosEmeraldService.assingToAgent(emeraldId, agentId));
    }
}
