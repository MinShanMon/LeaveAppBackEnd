package ca.team3.laps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.team3.laps.model.Admin;
import ca.team3.laps.model.CalendarificAPI.Holiday;
import ca.team3.laps.service.AdminService;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin account, HttpServletRequest request) {
        if (adminService.authenticate(account)) {
            HttpSession sessionObj = request.getSession();
            sessionObj.setAttribute("username", account.getUsername());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
    }

    /** GET request to retrieve all public holidays of a calendar year. */
    @GetMapping("/getholidays")
    public ResponseEntity getHolidays(@RequestParam String year) {
        try {
            List<Holiday> holidays = adminService.getHolidays(year);
            return ResponseEntity.status(HttpStatus.OK).body(holidays);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
