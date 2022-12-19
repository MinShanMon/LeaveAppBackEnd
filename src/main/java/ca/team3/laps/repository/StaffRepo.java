package ca.team3.laps.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.team3.laps.model.Staff;

@Transactional
public interface StaffRepo extends JpaRepository<Staff, Integer> {
    
    @Query("SELECT s "
    + "FROM Staff s "
    + "WHERE username = :username")
    public List<Staff> findByUsername(@Param("username") String username);

    public boolean existsByUsername(String username);

    public Staff findByStfId(Integer id);

    public int countByUsernameContaining(String username);

    public int countByUsername(String username);

    @Query("SELECT e2 FROM Staff e1, Staff e2 WHERE e1.stfId = e2.managerId AND e1.stfId = :eid")
    List<Staff> findSubordinates(@Param("eid") Integer eid);
}
