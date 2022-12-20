package ca.team3.laps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.team3.laps.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}