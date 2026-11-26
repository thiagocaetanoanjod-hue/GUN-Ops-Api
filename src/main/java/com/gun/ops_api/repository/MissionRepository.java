package com.gun.ops_api.repository;

import com.gun.ops_api.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}