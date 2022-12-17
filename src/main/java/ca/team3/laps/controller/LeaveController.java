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

import ca.team3.laps.model.Leave;

import ca.team3.laps.model.Staff;
import ca.team3.laps.service.LeaveService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/leaveHistory")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;
    //get leavehistory with Staffid
    @GetMapping(value = "/getWithStaffId/{id}", produces = "application/json")
    public @ResponseBody List<Leave> getHistory(@PathVariable("id") Integer id){
        List<Leave> Leaves = new ArrayList<Leave>();
        Leaves = leaveService.leaveHistory(id);
        return Leaves;
    }

    //update leavehistory before approve or reject
    @PutMapping(value="/put")
    public @ResponseBody Leave updateLeaveHistory(@RequestBody Leave leave){
        return leaveService.updateLeaveHistory(leave.getId(), leave);    
    }

    //create leavehistory
    @PostMapping(value="/post/{staffid}")
    public @ResponseBody Leave updateLeaveHistoryDisplay(@PathVariable("staffid") Integer id, @RequestBody Leave leave){
        return leaveService.createLeaveHistory(id, leave);
    }

    // @GetMapping(value = "/getStaff", produces = "application/json")
    // public @ResponseBody List<Staff> staffList(){
    //     return leaveService.getStaff();
    // }

    // @GetMapping(value = "/getStaff/{id}", produces = "application/json")
    // public @ResponseBody Staff getStaffwithid(@PathVariable("id") Integer id){
    //     return leaveService.getStaffWithStaffId(id);
    // }

        //get leave history with leaveid
    @GetMapping(value = "/getLeave/{id}", produces = "application/json")
    public @ResponseBody Leave getLeaveWithid(@PathVariable("id") Integer id){
        return leaveService.getwithLeaveId(id);
    }
    //get subordinate with managerid
    @GetMapping(value = "/getSubordinate/{id}", produces = "application/json")
    public @ResponseBody List<Staff> getSubordinate(@PathVariable("id") Integer id){
        return leaveService.getSubordinate(id);
    }
    //manager approve or reject

    @PutMapping(value = "/approve/put", produces = "application/json")
    public @ResponseBody Leave getLeaveWithid(@RequestBody Leave leave){
        return leaveService.approveLeave(leave);
    }
    //delete leavehistory
    @PutMapping(value = "/delete/put/{id}")
    public @ResponseBody Leave deleteLeave(@PathVariable("id") int id){
        return leaveService.deleteLeave(id);
    }

    //withdraw leavehistory
    @PutMapping(value = "/withdraw/put/{id}")
    public @ResponseBody Leave withdrawLeave(@PathVariable("id") int id){
        return leaveService.withdrawLeave(id);
    }
}
