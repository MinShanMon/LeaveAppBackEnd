package ca.team3.laps.service;

import java.util.List;

import ca.team3.laps.exception.AdminException;
import ca.team3.laps.model.Staff;

public interface AdminStaffService {
    List<Staff> findAllStaff();
    List<Staff> findAllActiveStaff();
    List<Staff> findAllInactiveStaff();
    List<Staff> findManagers();
    Staff findStaffById(int id);
    void createStaff(Staff staff) throws AdminException;
    void modifyStaff(Staff staff) throws AdminException;
    void deleteStaff(Staff staff);
    void updateAnLeaveEntitlement(Staff staff);
}
