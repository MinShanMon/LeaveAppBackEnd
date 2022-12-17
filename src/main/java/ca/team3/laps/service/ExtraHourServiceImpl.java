package ca.team3.laps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.team3.laps.model.ExtraHour;
import ca.team3.laps.model.LeaveStatusEnum;
import ca.team3.laps.repository.ExtraHourRepository;

@Service
public class ExtraHourServiceImpl implements ExtraHourService{
    @Autowired
    ExtraHourRepository extraHourRepository;

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
    public ExtraHour approvExtraHour(ExtraHour extraHour) {
        ExtraHour ext = extraHourRepository.findById(extraHour.getId()).get();
        if(ext.getStatus() != LeaveStatusEnum.SUBMITTED && ext.getStatus() != LeaveStatusEnum.UPDATED){
            return null;
        }
        
        return null;
    }

    
}
