package ca.team3.laps;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.team3.laps.model.Leave;
import ca.team3.laps.model.LeaveStatusEnum;
import ca.team3.laps.model.LeaveTypeEnum;
import ca.team3.laps.model.Staff;
import ca.team3.laps.repository.LeaveRepository;
import ca.team3.laps.repository.StaffRepo;

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

	@Bean
	public CommandLineRunner run(StaffRepo staffRepository, LeaveRepository leaveRepository) {
		return args -> {
			Staff javis = staffRepository.save(new Staff(1, 0, "Javis", "password", 3, "alrigh", "javis", "john",
					true, "Javis@gmail.com", 5, 10, 1));
			staffRepository.save(new Staff(2, 1, "Goh", "password", 3, "alrigh", "goh", "john",
					true, "goh@gmail.com", 5, 10, 1));

					Leave leave1 = new Leave(LeaveTypeEnum.MEDICAL_LEAVE, LocalDate.now(), LocalDate.now().plusDays(15), LocalDate.now().plusDays(10).getDayOfYear()-LocalDate.now().plusDays(5).getDayOfYear(),LeaveStatusEnum.SUBMITTED, "null", "null", javis);
					leaveRepository.saveAndFlush(leave1);
					Leave leave2 = new Leave(LeaveTypeEnum.MEDICAL_LEAVE, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), 2, LeaveStatusEnum.SUBMITTED, "null", "null", javis);
					leaveRepository.saveAndFlush(leave2);	

		};
	}
}
