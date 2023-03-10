package ca.team3.laps.repository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.team3.laps.model.Leave;


public interface LeaveRepository extends JpaRepository<Leave, Integer>{
    @Query("SELECT l FROM Leave l JOIN l.leave f WHERE f.stfId= :staffid")
    public List<Leave> findByStaffid(@Param("staffid") Integer staffid);
    @Query("SELECT l FROM Leave l WHERE l.id =:id")
    Leave getDetail(@Param("id") int id);
    @Query("SELECT l FROM Leave l WHERE l.endDate >= :day and l.startDate <= :day")
    public List<Leave> findByday(@Param("day") LocalDate day);
}
