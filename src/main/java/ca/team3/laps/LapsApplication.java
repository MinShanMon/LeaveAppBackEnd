package ca.team3.laps;

import java.time.LocalDate;
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

	@Bean
	public CommandLineRunner run(StaffRepo staffRepository, LeaveRepository leaveRepository,
			ExtraHourService extraHourService, ExtraHourRepository extraHourRepository) {
		return args -> {
			
        // staffRepository.saveAndFlush(new Staff(null, "may","may", "Manager", "May","Tan",
        // true, "May_Tan@gmail.com", 18, 14, 0.5));

		// staffRepository.saveAndFlush(new Staff(null, "john","john", "Manager", "John","Tan",
        // true, "John_Tan@gmail.com", 18, 14, 2.5));

        // Staff Lynn =  staffRepository.saveAndFlush(new Staff("1", "lynn", "lynn","Data Analyst", "Ying", "Li",
		// true, "Ying_Li@gmail.com", 18, 14, 2.5));

		// staffRepository.saveAndFlush(new Staff("1","lexi", "lexi", "Business Analyst", "Shan", "Feng", 
		// true, "Shan_Feng@gmail.com", 18, 14, 0));
			
		// staffRepository.saveAndFlush(new Staff("1","cailei", "Cailei", "Software Developer", "Cai Lei", "Zhang", 
		// true, "Cailei_Zhang@gmail.com", 18, 14, 0));
			
        // staffRepository.saveAndFlush(new Staff("2","oscar", "oscar", "Software Developer", "Shan Mon", "Min", 
		// true, "ShanMon_Min@gmail.com", 18, 14, 5));
			
		// staffRepository.saveAndFlush(new Staff("2","travis", "travis", "Software Architect", "La Pyae Htun", "Soe", 
		// true, "e1045754@u.nus.edu", 18, 14, 6.5));
			
        // staffRepository.saveAndFlush(new Staff("2","ivan", "ivan", "Admin", "Ivan Tse Khiang ", "Eng", 
		// true, "Ivan_Eng@gmail.com", 14, 14, 0));
	

		Leave leave1 = new Leave(LeaveTypeEnum.ANNUAL_LEAVE, LocalDate.now(), LocalDate.now().plusDays(10), 10,LeaveStatusEnum.SUBMITTED, "null", "null", Lynn);
		leaveRepository.saveAndFlush(leave1);

		Role manager = roleRepository.save(new Role("manager"));
		Role employee = roleRepository.save(new Role("employee"));
		Role admin = roleRepository.save(new Role("admin"));
		List<Role> roles = new ArrayList<>();
		roles.add(manager);
		roles.add(employee);
		roles.add(admin);
			
		
		// 			ExtraHour newex= new ExtraHour();
			

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
