package ca.team3.laps.service;

import java.util.List;

import ca.team3.laps.exception.AdminException;
import ca.team3.laps.model.Admin;
import ca.team3.laps.model.Staff;
import ca.team3.laps.model.CalendarificAPI.Holiday;

public interface AdminService {
    /**
     * Retrieve all public holidays of a calendar year.
     * If it does not exist in the database, calls Calendarific API and persist in
     * database.
     */
    List<Holiday> getHolidays(String year);

    boolean authenticate(Admin account);


}
