package ca.team3.laps.service;

import ca.team3.laps.model.ExtraHour;

public interface ExtraHourService {
 
    ExtraHour createExtraHour(ExtraHour extraHour);

    void delExtraHour(Integer id);

    ExtraHour updExtraHour(Integer id, ExtraHour extraHour);

    ExtraHour approvExtraHour(Integer id);

    ExtraHour rejecExtraHour(Integer id);
}
