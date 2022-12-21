package ca.team3.laps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.team3.laps.model.LeaveTypes.AnnualLeave;
import ca.team3.laps.model.LeaveTypes.CompensationLeave;
import ca.team3.laps.model.LeaveTypes.LeaveType;
import ca.team3.laps.model.LeaveTypes.MedicalLeave;


public interface LeaveTypeRepo extends JpaRepository<LeaveType, Long> {


    @Query("FROM AnnualLeave")
    public List<AnnualLeave> findAnnualLeavesEntitlement(); 

    @Query("FROM MedicalLeave")
    public MedicalLeave findMedicalLeaveEntitlement(); 

    @Query("FROM CompensationLeave")
    public CompensationLeave findCompLeaveEntitlement(); 

    @Query("FROM AnnualLeave")
    public AnnualLeave findByJobTitleIgnoreCase(String jobTitle);
}
