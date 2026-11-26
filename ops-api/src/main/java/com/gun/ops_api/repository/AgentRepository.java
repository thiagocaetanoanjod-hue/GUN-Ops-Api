package com.gun.ops_api.repository;

import com.gun.ops_api.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    boolean existsByCodename(String codename);
}