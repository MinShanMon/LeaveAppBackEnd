package ca.team3.laps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.team3.laps.model.Admin;
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
			Staff javis = staffRepository.save(new Staff(1, 0, "Javis", "password", "alrigh", "javis", "john",
					true, "Javis@gmail.com", 5, 10, 1));
			Staff john = staffRepository.save(new Staff(2, 1, "Goh", "password", "alrigh", "goh", "john",
					true, "goh@gmail.com", 5, 10, 1));
			Role manager = roleRepository.save(new Role("manager"));
			Role employee = roleRepository.save(new Role("employee"));
			List<Role> roles = new ArrayList<>();
			roles.add(manager);
			javis.setRoles(roles);
			john.setRoles(roles);
			staffRepository.saveAndFlush(javis);
			staffRepository.saveAndFlush(john);
			adminRepository.saveAndFlush(new Admin("admin", "admin"));
		};
	}
}
