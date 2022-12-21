package ca.team3.laps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.team3.laps.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    public Admin findByUsername(String username);
}
