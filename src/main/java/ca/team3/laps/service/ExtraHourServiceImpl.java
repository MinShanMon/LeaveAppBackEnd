package ca.team3.laps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.team3.laps.exception.LeaveException;
import ca.team3.laps.model.ExtraHour;
import ca.team3.laps.model.Leave;
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

    @Autowired
    LeaveService leaveService;

    @Override
    public List<ExtraHour> suboExtraHour(Integer stfid){
        return extraHourRepository.findBystfid(stfid);
    }

    @Override
    public ExtraHour findExtWithExtid(Integer extid){
        return extraHourRepository.findById(extid).orElse(null);
    }
    
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
    public boolean delExtraHour(Integer id) {
        try{
            ExtraHour ext = extraHourRepository.findById(id).get();
            if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
                return false;
            }
            extraHourRepository.delete(ext);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    @Override
    public ExtraHour updExtraHour(Integer id, ExtraHour extraHour) throws LeaveException{
        ExtraHour ext = extraHourRepository.findById(id).get();
        if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
            throw new LeaveException("status cannot change, it is already "+ext.getStatus());
        }
        ext.setDate(extraHour.getDate());
        ext.setWorking_hour(extraHour.getWorking_hour());
        ext.setStatus(LeaveStatusEnum.UPDATED);
        extraHourRepository.saveAndFlush(ext);
        return ext;
    }

    @Override
    public ExtraHour approvExtraHour(Integer id) throws LeaveException{
        ExtraHour ext = extraHourRepository.findById(id).get();
        if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
            throw new LeaveException("status cannot change, it is already "+ext.getStatus());
        }
        ext.setStatus(LeaveStatusEnum.APPROVED);
        Staff staff = staffRepo.findById(ext.getStaff_id()).get();
        double value = staff.getCompLeave() + (ext.getWorking_hour()/8);
        staff.setCompLeave(value);
        staffRepo.saveAndFlush(staff);
        extraHourRepository.saveAndFlush(ext);
        return ext;
    }

    @Override
    public ExtraHour rejecExtraHour(Integer id) throws LeaveException{
        ExtraHour ext = extraHourRepository.findById(id).get();
        if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
            throw new LeaveException("status cannot change, it is already "+ext.getStatus());
        }
        ext.setStatus(LeaveStatusEnum.REJECTED);
        extraHourRepository.saveAndFlush(ext);
        return ext;
    }

    
}
