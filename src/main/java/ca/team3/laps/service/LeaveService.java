package ca.team3.laps.service;

import java.util.List;

import ca.team3.laps.model.Leave;

import ca.team3.laps.model.Staff;

import java.time.LocalDate;
public interface LeaveService {
    List<Leave> leaveHistory(Integer staffid);
    
    // Leave updateLeave(Leave leave, String leaveId);
    Leave updateLeaveHistory(Integer id, Leave leave);

    Leave getwithLeaveId(Integer id);

    Leave createLeaveHistory(Integer id,Leave leave);

    public List<Staff> getStaff();

    

    List<Staff> getSubordinate(Integer id);

    Leave approveLeave(Leave leave);

    Leave deleteLeave(Integer id);

    Leave withdrawLeave(Integer id);
}
