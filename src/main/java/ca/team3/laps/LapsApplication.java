package ca.team3.laps;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.team3.laps.model.Admin;
import ca.team3.laps.model.Leave;
import ca.team3.laps.model.LeaveStatusEnum;
import ca.team3.laps.model.LeaveTypeEnum;
import ca.team3.laps.model.Role;
import ca.team3.laps.model.Staff;
import ca.team3.laps.repository.AdminRepository;
import ca.team3.laps.repository.ExtraHourRepository;
import ca.team3.laps.repository.LeaveRepository;
import ca.team3.laps.repository.RoleRepository;
import ca.team3.laps.repository.StaffRepo;
import ca.team3.laps.service.AdminService;
import ca.team3.laps.service.ExtraHourService;

@SpringBootApplication
public class LapsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsApplication.class, args);
	}

	// @Bean
	// public WebClient webClient(WebClient.Builder webClientBuilder) {
	// return webClientBuilder
	// .baseUrl("https://holidayapi.com/v1/holidays")
	// .build();
	// }

	@Autowired
	StaffRepo staffRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	AdminService adminService;

	@Bean
	public CommandLineRunner run(StaffRepo staffRepository, LeaveRepository leaveRepository,
			ExtraHourService extraHourService, ExtraHourRepository extraHourRepository) {
		return args -> {

			String year = Year.now().toString();
			Admin adminAcct = new Admin("admin", "admin");
			adminRepository.save(adminAcct);
			adminService.getHolidays(year);
		};
	}
}
