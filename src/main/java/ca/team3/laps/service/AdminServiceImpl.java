package ca.team3.laps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ca.team3.laps.exception.AdminException;
import ca.team3.laps.exception.ErrorJson;
import ca.team3.laps.model.Admin;
import ca.team3.laps.model.Staff;
import ca.team3.laps.model.CalendarificAPI.CalendarificAPIResponse;
import ca.team3.laps.model.CalendarificAPI.Holiday;
import ca.team3.laps.repository.AdminRepository;
import ca.team3.laps.repository.CalendarRepo;
import ca.team3.laps.repository.LeaveTypeRepo;
import ca.team3.laps.repository.StaffRepo;
import reactor.core.publisher.Mono;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    Environment env;

    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    WebClient webClient;

    @Override
    public List<Holiday> getHolidays(String year) {
        int yearInt = Integer.parseInt(year);
        List<Holiday> holidays = calendarRepo.findByYear(yearInt);
        if (holidays.isEmpty()) {
            holidays = getHolidaysFromCalendarificAPI(year);
        }
        return holidays;
    }

    /**
     * Use Calendarific API to retrieve public holidays and persist to database.
     **/
    private List<Holiday> getHolidaysFromCalendarificAPI(String year) {
        String key = env.getProperty("calenderific.key");
        String country = env.getProperty("calenderific.country");
        Mono<CalendarificAPIResponse> response = webClient.get()
                .uri(UriBuilder -> UriBuilder
                        .queryParam("api_key", key)
                        .queryParam("country", country)
                        .queryParam("year", year)
                        .queryParam("type", "national")
                        .build())
                .retrieve()
                .bodyToMono(CalendarificAPIResponse.class);
        List<Holiday> holidays = response.block().getHolidays();
        holidays.forEach(holiday -> calendarRepo.save(holiday));
        return holidays;
    }

    public boolean authenticate(Admin account) {
        Admin adminRec = adminRepository.findByUsername(account.getUsername());
        if (adminRec == null) {
            return false;
        }
        if (adminRec.getPassword().equals(account.getPassword())) {
            return true;
        }
        return false;
    }

}
