package ca.team3.laps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.team3.laps.model.ExtraHour;

public interface ExtraHourRepository extends JpaRepository<ExtraHour, Integer> {
    
}
