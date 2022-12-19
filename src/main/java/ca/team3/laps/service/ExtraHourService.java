package ca.team3.laps.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import ca.team3.laps.exception.LeaveException;
import ca.team3.laps.model.ExtraHour;

public interface ExtraHourService {
    
    List<ExtraHour> suboExtraHour(Integer id);

    ExtraHour createExtraHour(ExtraHour extraHour);

    boolean delExtraHour(Integer id);

    ExtraHour updExtraHour(Integer id, ExtraHour extraHour) throws LeaveException;

    ExtraHour approvExtraHour(Integer id) throws LeaveException;

    ExtraHour rejecExtraHour(Integer id) throws LeaveException;

    static ResponseEntity createExtra(ExtraHour extraHour) {
        return null;
    }
}
