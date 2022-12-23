package ca.team3.laps.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.team3.laps.exception.LeaveException;
import ca.team3.laps.model.Leave;

import ca.team3.laps.service.LeaveService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    // get leavehistory with Staffid
    @GetMapping(value = "/getWithStaffId/{id}", produces = "application/json")
    public ResponseEntity getHistory(@PathVariable("id") Integer id) {
        try {
            List<Leave> Leaves = new ArrayList<Leave>();
            Leaves = leaveService.leaveHistory(id);
            return new ResponseEntity<>(Leaves, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update leavehistory before approve or reject
    @PutMapping(value = "/leave/put")
    public ResponseEntity updateLeaveHistory(@RequestBody Leave leave) {
        try {
            // return
            // ResponseEntity.status(HttpStatus.CREATED).body(leaveService.updateLeaveHistory(leave.getId(),
            // leave));
            return new ResponseEntity<>(leaveService.updateLeaveHistory(leave.getId(), leave), HttpStatus.CREATED);

        } catch (LeaveException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create leavehistory
    @PostMapping(value = "/post/{staffid}")
    public ResponseEntity updateLeaveHistoryDisplay(@PathVariable("staffid") Integer id, @RequestBody Leave leave) {
        try {
            return new ResponseEntity<>(leaveService.createLeaveHistory(id, leave), HttpStatus.CREATED);
            // leaveService.createLeaveHistory(id, leave);
        } catch (LeaveException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // get leave history with leaveid
    @GetMapping(value = "/getLeave/{id}", produces = "application/json")
    public ResponseEntity getLeaveWithid(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(leaveService.getwithLeaveId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get subordinate with managerid
    @GetMapping(value = "/getSubordinate/{id}", produces = "application/json")
    public ResponseEntity getSubordinate(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(leaveService.getSubordinate(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // manager approve or reject

    @PutMapping(value = "/approve/put", produces = "application/json")
    public ResponseEntity getLeaveWithid(@RequestBody Leave leave) {
        try {
            return new ResponseEntity<>(leaveService.approveLeave(leave), HttpStatus.CREATED);
        } catch (LeaveException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete leavehistory
    @PutMapping(value = "/delete/put/{id}")
    public @ResponseBody Leave deleteLeave(@PathVariable("id") int id) {
        return leaveService.deleteLeave(id);
    }

    // withdraw leavehistory
    @PutMapping(value = "/withdraw/put/{id}")
    public @ResponseBody Leave withdrawLeave(@PathVariable("id") int id) {
        return leaveService.withdrawLeave(id);
    }

    // get all pending
    @GetMapping("/viewpending/{id}")
    public ResponseEntity<List<Leave>> getPending(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(leaveService.viewMulPendingDetails(id), HttpStatus.OK);
    }

    // get only one pending
    @GetMapping("/viewdetail/{id}")
    public ResponseEntity<Leave> getDetail(@PathVariable int id) throws LeaveException {
        
        try {
            return new ResponseEntity<>(leaveService.viewOnePendingDetail(id), HttpStatus.OK);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getByday/{day}", produces = "application/json")
    public ResponseEntity getDayLeave(@PathVariable("day") String day) {
        try {
            List<Leave> Leaves = new ArrayList<Leave>();
            Leaves = leaveService.dateLeave(day);
            return new ResponseEntity<>(Leaves, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
