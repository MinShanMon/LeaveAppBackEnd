package ca.team3.laps.service;

import java.util.List;
import java.util.Objects;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ca.team3.laps.model.Leave;
import ca.team3.laps.model.LeaveStatusEnum;
import ca.team3.laps.model.LeaveTypeEnum;
import ca.team3.laps.model.Staff;
import ca.team3.laps.model.CalendarificAPI.Holiday;
import ca.team3.laps.repository.CalendarRepo;
import ca.team3.laps.repository.LeaveRepository;
import ca.team3.laps.repository.StaffRepo;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    StaffRepo staffRepo;

    @Autowired
    CalendarRepo calendarRepo;

    @Override
    public List<Leave> leaveHistory(Integer staffid) {
        return leaveRepository.findByStaffid(staffid);
    }

    @Override
    public Leave updateLeaveHistory(Integer id, Leave leaves) {
        // TODO Auto-generated method stub
        Leave leave = leaveRepository.findById(id).get();
        if(leave.getStatus() != LeaveStatusEnum.SUBMITTED && leave.getStatus() != LeaveStatusEnum.UPDATED){
            return null;
        }
        leave.setStartDate(leaves.getStartDate());
        leave.setEndDate(leaves.getEndDate());

        LocalDate date1 = leaves.getStartDate();
			LocalDate date2 = leaves.getEndDate();

			List<Holiday> dates = calendarRepo.findByYear(date1.getYear());
            if(date1.getYear() != date2.getYear()){
            List<Holiday> dates1 = calendarRepo.findByYear(date2.getYear());
                for(Holiday h: dates1){
                    dates.add(h);
                }
            }
            
			int count = 0;
			for(int i = 0; i< dates.size(); i++ ){
				if((dates.get(i).getDate().isEqual(date1) || dates.get(i).getDate().isAfter(date1)) && (dates.get(i).getDate().isEqual(date2) || dates.get(i).getDate().isBefore(date2))){
					count++;
				}
                
			}

            
            int peri1= (int)(leaves.getEndDate().toEpochDay()- leaves.getStartDate().toEpochDay());
            int peri = peri1 - count;
            Staff staff = leave.getLeave();
            if(leave.getType() == LeaveTypeEnum.ANNUAL_LEAVE){
                if(peri>staff.getAnuLeave()){
                   return null;
                }
            }

            if(leave.getType() == LeaveTypeEnum.MEDICAL_LEAVE){
                if(peri>staff.getMediLeave()){
                    return null;
                 }
            }

            if(leave.getType() == LeaveTypeEnum.COMPENSATION_LEAVE){
                if(peri>staff.getCompLeave()){
                    return null;
                 }
            }

            
        leave.setStatus(LeaveStatusEnum.UPDATED);
        leave.setPeriod(peri);
        leave.setType(leaves.getType());
        leaveRepository.save(leave);
        
        return leave;
    }

    public List<Staff> getStaff(){
        return staffRepo.findAll();
    }

    @Override
    public List<Staff> getSubordinate(Integer id){
        return staffRepo.findSubordinates(id);
    }


    @Override
    public Leave getwithLeaveId(Integer id) {
        
        return leaveRepository.findById(id).get();
    }

    @Override
    public Leave createLeaveHistory(Integer id, Leave leaves) {
        Staff staff = staffRepo.findById(id).get();
        Leave leaveHistory = new Leave();
        leaveHistory.setStartDate(leaves.getStartDate());
        leaveHistory.setEndDate(leaves.getEndDate());
        leaveHistory.setLeave(staff);
        LocalDate date1 = leaves.getStartDate();
			LocalDate date2 = leaves.getEndDate();

			List<Holiday> dates = calendarRepo.findByYear(date1.getYear());
            if(date1.getYear() != date2.getYear()){
            List<Holiday> dates1 = calendarRepo.findByYear(date2.getYear());
                for(Holiday h: dates1){
                    dates.add(h);
                }
            }
            
			int count = 0;
			for(int i = 0; i< dates.size(); i++ ){
				if((dates.get(i).getDate().isEqual(date1) || dates.get(i).getDate().isAfter(date1)) && (dates.get(i).getDate().isEqual(date2) || dates.get(i).getDate().isBefore(date2))){
					count++;
				}
                
			}

            
            int peri1= (int)(leaves.getEndDate().toEpochDay()- leaves.getStartDate().toEpochDay());
            int peri= peri1-count;
            if(leaves.getType() == LeaveTypeEnum.ANNUAL_LEAVE){
                if(peri>staff.getAnuLeave()){
                   return null;
                }

            }

            if(leaves.getType() == LeaveTypeEnum.MEDICAL_LEAVE){
                if(peri>staff.getMediLeave()){
                    return null;
                 }

            }

            if(leaves.getType() == LeaveTypeEnum.COMPENSATION_LEAVE){
                if(peri>staff.getCompLeave()){
                    return null;
                }

            }
        
        leaveHistory.setPeriod(peri);
        leaveHistory.setReason(leaves.getReason());
        leaveHistory.setType(leaves.getType());
        leaveHistory.setWork(leaves.getWork());
        leaveHistory.setStatus(LeaveStatusEnum.SUBMITTED);
        leaveRepository.save(leaveHistory);
        return leaveHistory;
    }

    @Override
    public Leave approveLeave(Leave leave) {
        Leave leaves = getwithLeaveId(leave.getId());
        Staff staff = leaves.getLeave();
        if(leaves.getStatus() != LeaveStatusEnum.SUBMITTED && leaves.getStatus() != LeaveStatusEnum.UPDATED ){
            return null;
        }
        leaves.setStatus(leave.getStatus());
        if(leave.getStatus() == LeaveStatusEnum.APPROVED){

            if(leaves.getType() == LeaveTypeEnum.ANNUAL_LEAVE){
                int peri = leaves.getPeriod();
                int i = staff.getAnuLeave()-peri;
                staff.setAnuLeave(i);
                staffRepo.saveAndFlush(staff);
                leaveRepository.saveAndFlush(leaves);
                return leaves;
            }

            if(leaves.getType() == LeaveTypeEnum.MEDICAL_LEAVE){
                int peri = leaves.getPeriod();
                    int i = staff.getMediLeave()-peri;
                    staff.setMediLeave(i);
                    staffRepo.saveAndFlush(staff);
                    leaveRepository.saveAndFlush(leaves);
                    return leaves;
            }

            if(leaves.getType() == LeaveTypeEnum.COMPENSATION_LEAVE){
                int peri = leaves.getPeriod();
                    int i = staff.getCompLeave()-peri;
                    staff.setCompLeave(i);
                    staffRepo.saveAndFlush(staff);
                    leaveRepository.saveAndFlush(leaves);
                    return leaves;
            }
        }
        leaveRepository.saveAndFlush(leaves);
        return leaves;
    }

    @Override
    public Leave deleteLeave(Integer id) {
        
        Leave leave = getwithLeaveId(id);
        if(leave.getStatus() != LeaveStatusEnum.UPDATED && leave.getStatus() != LeaveStatusEnum.SUBMITTED){
            return null;
        }
        leave.setStatus(LeaveStatusEnum.DELETED);
        leaveRepository.saveAndFlush(leave);
        return getwithLeaveId(id);
    }

    @Override
    public Leave withdrawLeave(Integer id) {
        Leave leave = getwithLeaveId(id);
        if(leave.getStatus() != LeaveStatusEnum.UPDATED && leave.getStatus() != LeaveStatusEnum.SUBMITTED){
            return null;
        }
        leave.setStatus(LeaveStatusEnum.WITHDRAWN);
        leaveRepository.saveAndFlush(leave);
        return getwithLeaveId(id);
    }
    
}

