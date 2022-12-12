package ca.team3.laps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.team3.laps.exception.DuplicateException;
import ca.team3.laps.model.Staff;
import ca.team3.laps.model.CalendarificAPI.Holiday;
import ca.team3.laps.model.LeaveTypes.AnnualLeave;
import ca.team3.laps.service.AdminService;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    AdminService adminService;

    /** GET request to retrieve all public holidays of a calendar year. */
    @GetMapping("/getholidays")
    public ResponseEntity<List<Holiday>> getHolidays(@RequestParam String year) {
        try {
            List<Holiday> holidays = adminService.getHolidays(year);
            return new ResponseEntity<>(holidays, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** POST request to insert a new staff record. */
    @PostMapping("/staff/create")
    public ResponseEntity createStaff(@RequestBody Staff staff) {
        try {
            Staff createdStaff = adminService.createStaff(staff);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStaff);
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //POST/PUT requests to manage leave types
    @PostMapping("/leave/createannual")
    public ResponseEntity createAnnualLeaveEntitlement(AnnualLeave AnnualLeave) {
        try {
            AnnualLeave createdAnnualLeave = adminService.createAnnualLeave(AnnualLeave);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnualLeave);
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
