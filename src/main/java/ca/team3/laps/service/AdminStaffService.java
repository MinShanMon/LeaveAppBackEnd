package ca.team3.laps.service;

import java.util.List;

import ca.team3.laps.model.Staff;

public interface AdminStaffService {
    List<Staff> findAllStaff();
    List<Staff> findAllActiveStaff();
    Staff findStaffById(int id);
    void createStaff(Staff staff);
    void modifyStaff(Staff staff);
    void deleteStaff(Staff staff);
    void updateAnLeaveEntitlement(Staff staff);
}
