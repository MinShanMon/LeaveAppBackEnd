package ca.team3.laps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.team3.laps.model.Staff;
import ca.team3.laps.service.AdminStaffService;

@RestController
@RequestMapping("/api")
public class AdminStaffController {

    @Autowired
    AdminStaffService adminStaffService;

    @PostMapping("/staff")
    public ResponseEntity createStaff(@RequestBody Staff staff) {
        adminStaffService.createStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
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
