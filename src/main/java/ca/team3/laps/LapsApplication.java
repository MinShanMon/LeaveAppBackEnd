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


	

		// Leave leave1 = new Leave(LeaveTypeEnum.ANNUAL_LEAVE, LocalDate.now(), LocalDate.now().plusDays(10), 10,LeaveStatusEnum.SUBMITTED, "null", "null", Lynn);
		// leaveRepository.saveAndFlush(leave1);

		// 	Role manager = roleRepository.save(new Role("manager"));
		// 	Role employee = roleRepository.save(new Role("employee"));
		// 	Role admin = roleRepository.save(new Role("admin"));
		// 	List<Role> roles = new ArrayList<>();
		// 	roles.add(manager);
		// 	roles.add(employee);
		// 	roles.add(admin);

		// 	String year = Year.now().toString();

		// 	adminService.getHolidays(year);

			// ExtraHour newex= new ExtraHour();

			// newex.setStaff_id(2);
			// newex.setDate(LocalDate.now().minusDays(1));
			// newex.setWorking_hour(4);
			// newex.setStatus(LeaveStatusEnum.SUBMITTED);
			// extraHourService.createExtraHour(newex);

			// Staff staff = staffRepository.findById(newex.getStaff_id()).orElse(null);
			// double value = staff.getCompLeave() + (newex.getWorking_hour()/8);
			// staff.setCompLeave(value);
			// staffRepository.saveAndFlush(staff);
			// // Staff sta = staffRepository.findById(2).get();
			// System.out.println(subo);

		};
	}
}
