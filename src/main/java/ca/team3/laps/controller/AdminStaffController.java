package ca.team3.laps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.team3.laps.exception.AdminException;
import ca.team3.laps.model.Staff;
import ca.team3.laps.service.AdminStaffService;

@RestController
@RequestMapping("/api")
public class AdminStaffController {

    @Autowired
    AdminStaffService adminStaffService;

    @GetMapping("/managers")
    public ResponseEntity findManagers() {
        List<Staff> managers = adminStaffService.findManagers();
        return ResponseEntity.status(HttpStatus.OK).body(managers);
    }

    @GetMapping("/staff")
    public ResponseEntity getAllActiveStaff() {
        List<Staff> staffList = adminStaffService.findAllActiveStaff();
        return ResponseEntity.status(HttpStatus.OK).body(staffList);
    }

    @GetMapping("/staff/inactive")
    public ResponseEntity getAllInactiveStaff() {
        List<Staff> staffList = adminStaffService.findAllInactiveStaff();
        return ResponseEntity.status(HttpStatus.OK).body(staffList);
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity findStaffById(@PathVariable int id) {
        Staff staff = adminStaffService.findStaffById(id);
        return ResponseEntity.status(HttpStatus.OK).body(staff);
    }

    @GetMapping("/password/{id}")
    public ResponseEntity findStaffPassword(@PathVariable int id) {
        Staff staff = adminStaffService.findStaffById(id);
        return ResponseEntity.status(HttpStatus.OK).body(staff.getPassword());
    }

    @PostMapping("/staff")
    public ResponseEntity createStaff(@RequestBody Staff staff) {
        try {
            adminStaffService.createStaff(staff);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (AdminException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getError());
        }

    }

    @PutMapping("/staff")
    public ResponseEntity modifyStaff(@RequestBody Staff staff) {
        try {
            adminStaffService.modifyStaff(staff);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AdminException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getError());
        }
    }

    @DeleteMapping("/staff")
    public ResponseEntity deleteStaff(@RequestBody Staff staff) {
        adminStaffService.deleteStaff(staff);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping("/staff/leave")
    public ResponseEntity updateAnLeaveEntitlement(@RequestBody Staff staff) {
        adminStaffService.updateAnLeaveEntitlement(staff);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
