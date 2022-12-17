package ca.team3.laps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.team3.laps.model.ExtraHour;
import ca.team3.laps.model.LeaveStatusEnum;
import ca.team3.laps.model.Staff;
import ca.team3.laps.repository.ExtraHourRepository;
import ca.team3.laps.repository.StaffRepo;

@Service
public class ExtraHourServiceImpl implements ExtraHourService{
    @Autowired
    ExtraHourRepository extraHourRepository;

    @Autowired
    StaffRepo staffRepo;
    @Override
    public ExtraHour createExtraHour(ExtraHour extraHour) {
        ExtraHour newex= new ExtraHour();
        newex.setStaff_id(extraHour.getStaff_id());
        newex.setDate(extraHour.getDate());
        newex.setWorking_hour(extraHour.getWorking_hour());
        newex.setStatus(extraHour.getStatus());
        extraHourRepository.saveAndFlush(newex);
        return newex;
    }

    @Override
    public void delExtraHour(Integer id) {
        ExtraHour ext = extraHourRepository.findById(id).get();
        if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
            return;
        }
        extraHourRepository.delete(ext);
    }

    @Override
    public ExtraHour updExtraHour(Integer id, ExtraHour extraHour) {
        ExtraHour ext = extraHourRepository.findById(id).get();
        if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
            return null;
        }
        ext.setDate(extraHour.getDate());
        ext.setWorking_hour(extraHour.getWorking_hour());
        ext.setStatus(LeaveStatusEnum.UPDATED);
        extraHourRepository.saveAndFlush(ext);
        return ext;
    }

    @Override
    public ExtraHour approvExtraHour(Integer id) {
        ExtraHour ext = extraHourRepository.findById(id).get();
        if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
            return null;
        }
        ext.setStatus(LeaveStatusEnum.APPROVED);
        Staff staff = staffRepo.findById(ext.getStaff_id()).get();
        double value = ext.getWorking_hour()/8;
        staff.setCompLeave(value);
        staffRepo.saveAndFlush(staff);
        extraHourRepository.saveAndFlush(ext);
        return ext;
    }

    @Override
    public ExtraHour rejecExtraHour(Integer id){
        ExtraHour ext = extraHourRepository.findById(id).get();
        ext.setStatus(LeaveStatusEnum.REJECTED);
        extraHourRepository.saveAndFlush(ext);
        return ext;
    }

    
}
