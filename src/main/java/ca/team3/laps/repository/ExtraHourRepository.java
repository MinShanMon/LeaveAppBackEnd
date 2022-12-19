package ca.team3.laps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.team3.laps.model.ExtraHour;

public interface ExtraHourRepository extends JpaRepository<ExtraHour, Integer> {
    @Query("SELECT s FROM ExtraHour s WHERE Staff_id = :staffid")
    public List<ExtraHour> findBystfid(@Param("staffid") Integer id);
}
