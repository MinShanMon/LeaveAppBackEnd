package ca.team3.laps.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.team3.laps.exception.LeaveException;
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
    public Leave updateLeaveHistory(Integer id, Leave leaves) throws ca.team3.laps.exception.LeaveException{
        // TODO Auto-generated method stub
        Leave leave = leaveRepository.findById(id).get();
        if(leave.getStatus() != LeaveStatusEnum.SUBMITTED && leave.getStatus() != LeaveStatusEnum.UPDATED){
            throw new LeaveException("status cannot update, it is already '"+ leave.getStatus()+".'");
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

            Staff staff = leave.getLeave();
            if(leaves.getType() == LeaveTypeEnum.COMPENSATION_LEAVE){
                if(leaves.getPeriod()>staff.getCompLeave()){
                    throw new LeaveException("Check Start Date and End Date and period, You only left "+ staff.getCompLeave()+". You are trying to enter " + leaves.getPeriod()+" days");
                }            
                int l = leaves.getPeriod()-count;
                leave.setPeriod(l);
            }

            else{
                int peri1= (int)(leaves.getEndDate().toEpochDay()- leaves.getStartDate().toEpochDay());
                int peri = peri1 - count;
                
                if(leave.getType() == LeaveTypeEnum.ANNUAL_LEAVE){
                    if(peri>staff.getAnuLeave()){
                    throw new LeaveException("Check Start Date and End Date, You only left "+ staff.getAnuLeave()+"You are trying to enter " + peri+" days");
                    }
                }

                if(leave.getType() == LeaveTypeEnum.MEDICAL_LEAVE){
                    if(peri>staff.getMediLeave()){
                        throw new LeaveException("Check Start Date and End Date, You only left "+ staff.getMediLeave()+". You are trying to enter " + peri+" days");
                    
                    }
                }

                // if(leave.getType() == LeaveTypeEnum.COMPENSATION_LEAVE){
                //     if(peri>staff.getCompLeave()){
                //         return null;
                //      }
                // }
                leave.setPeriod(peri);
            }
        leave.setStatus(LeaveStatusEnum.UPDATED);
        leave.setWork(leaves.getWork());
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
    public Leave createLeaveHistory(Integer stfid, Leave leaves) throws LeaveException{
        Staff staff = staffRepo.findById(stfid).get();
        Leave leaveHistory = new Leave();
        leaveHistory.setStartDate(leaves.getStartDate());
        leaveHistory.setEndDate(leaves.getEndDate());
        leaveHistory.setLeave(staff);
        leaveHistory.setReason("null");
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

        if(leaves.getType() == LeaveTypeEnum.COMPENSATION_LEAVE){
            if(leaves.getPeriod()>staff.getCompLeave()){
                throw new LeaveException("Check Start Date and End Date and period, You only left "+ staff.getCompLeave()+". You are trying to enter " + leaves.getPeriod()+" days");
            }            
            int l = leaves.getPeriod()-count;
            leaveHistory.setPeriod(l);
        }
        else{
            int peri1= (int)(leaves.getEndDate().toEpochDay()- leaves.getStartDate().toEpochDay());
            int peri= peri1-count;
            if(leaves.getType() == LeaveTypeEnum.ANNUAL_LEAVE){
                if(peri>staff.getAnuLeave()){
                    throw new LeaveException("Check Start Date and End Date, You only left "+ staff.getAnuLeave()+". You are trying to enter " + peri+" days");
                }

            }

            if(leaves.getType() == LeaveTypeEnum.MEDICAL_LEAVE){
                if(peri>staff.getMediLeave()){
                    throw new LeaveException("Check Start Date and End Date, You only left "+ staff.getMediLeave()+". You are trying to enter " + peri+" days");
                 }

            }
        
            leaveHistory.setPeriod(peri);
        }
        // leaveHistory.setReason(leaves.getReason());
        leaveHistory.setType(leaves.getType());
        leaveHistory.setWork(leaves.getWork());
        leaveHistory.setStatus(LeaveStatusEnum.SUBMITTED);
        leaveRepository.save(leaveHistory);
        return leaveHistory;
    }

    @Override
    public Leave approveLeave(Leave leave) throws LeaveException{
        Leave leaves = getwithLeaveId(leave.getId());
        Staff staff = leaves.getLeave();
        if(leaves.getStatus() != LeaveStatusEnum.SUBMITTED && leaves.getStatus() != LeaveStatusEnum.UPDATED ){
            throw new LeaveException("it is already "+ leaves.getStatus()+". ");
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
                    double i = staff.getCompLeave()-peri;
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
        if(leave.getStatus() != LeaveStatusEnum.APPROVED){
            return null;
        }
        leave.setStatus(LeaveStatusEnum.WITHDRAWN);
        leaveRepository.saveAndFlush(leave);
        return getwithLeaveId(id);
    }

    @Override
    @Transactional
    public List<Leave> viewMulPendingDetails() {
        List<Leave> leaves = (List<Leave>)leaveRepository.findAll();
        leaves = leaves.stream().filter(u -> u.getStatus().equals(LeaveStatusEnum.SUBMITTED)).collect(Collectors.toList());
        return leaves; 
    }


    @Override
    @Transactional
    public Leave viewOnePendingDetail(int id) throws LeaveException{
        Leave leave = leaveRepository.findById(id).get();
        if(leave.getStatus() == LeaveStatusEnum.SUBMITTED){
        return leaveRepository.getDetail(id);
        }else{
            throw new LeaveException("Record not found!");
        }
    }
}

