package ca.team3.laps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.team3.laps.model.CalendarificAPI.Holiday;


public interface CalendarRepo extends JpaRepository<Holiday, Long> {
    @Query("SELECT h "
    + "FROM Holiday h "
    + "WHERE year(h.date) = :year "
    + "AND h.name NOT IN ('Saturday', 'Sunday')")
    public List<Holiday> findByYear(@Param("year") int year);

}
