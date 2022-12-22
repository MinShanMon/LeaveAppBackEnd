package ca.team3.laps.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
            List<Holiday> weekends = new ArrayList<>();
            getWeekendsAndUpdate(yearInt, holidays, weekends);
            calendarRepo.saveAllAndFlush(holidays);
            calendarRepo.saveAllAndFlush(weekends);
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
        return holidays;
    }

    private List<Holiday> getWeekendsAndUpdate(int year, List<Holiday> holidays, List<Holiday> weekends) {
        LocalDate firstDayOfYear = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate saturday = firstDayOfYear.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        LocalDate sunday = firstDayOfYear.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

        do {
            Holiday saturdayRec = new Holiday("Saturday", saturday);
            saturday = saturday.plus(Period.ofDays(7));
            Holiday sundayRec = new Holiday("Sunday", sunday);
            sunday = sunday.plus(Period.ofDays(7));

            // Filter duplicate dates
            Set<LocalDate> holidayDates = new HashSet<>();
            holidays.forEach(holiday -> {
                holidayDates.add(holiday.getDate());
            });
            if (!holidayDates.contains(saturdayRec.getDate())) {
                weekends.add(saturdayRec);
            }
            if (!holidayDates.contains(sundayRec.getDate()) && sundayRec.getDate().getYear() == year) {
                weekends.add(sundayRec);
            }
        } while (saturday.getYear() == year);
        return weekends;
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
