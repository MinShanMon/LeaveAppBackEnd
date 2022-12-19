package ca.team3.laps;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.team3.laps.model.ExtraHour;
import ca.team3.laps.model.Leave;
import ca.team3.laps.model.LeaveStatusEnum;
import ca.team3.laps.model.LeaveTypeEnum;
import ca.team3.laps.model.Staff;
import ca.team3.laps.repository.LeaveRepository;
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

	@Bean
	public CommandLineRunner run(StaffRepo staffRepository, LeaveRepository leaveRepository, ExtraHourService extraHourService) {
		return args -> {
			// Staff javis = staffRepository.save(new Staff(1, 0, "Javis", "password", 3, "alrigh", "javis", "john",
			// 		true, "Javis@gmail.com", 5, 10, 1));
			// staffRepository.save(new Staff(2, 1, "Goh", "password", 3, "alrigh", "goh", "john",
			// 		true, "goh@gmail.com", 5, 10, 1));
			// Staff javiss = staffRepository.save(new Staff(1, 0, "minshan", "password", "shanmon@gmail.com", "notitile", "shan", "mon", true, null, null, 60, 60, 10, 0, null, null));
			Staff javis = staffRepository.save(new Staff(1, 0, "shan", "password", "nottile", "shan", "mon", true, "shanmon2017@gmail.com", 60, 60, 60));
					Leave leave1 = new Leave(LeaveTypeEnum.MEDICAL_LEAVE, LocalDate.now(), LocalDate.now().plusDays(15), LocalDate.now().plusDays(10).getDayOfYear()-LocalDate.now().plusDays(5).getDayOfYear(),LeaveStatusEnum.REJECTED, "null", "null", javis);
					leaveRepository.saveAndFlush(leave1);
					Leave leave2 = new Leave(LeaveTypeEnum.MEDICAL_LEAVE, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), 2, LeaveStatusEnum.SUBMITTED, "null", "null", javis);
					leaveRepository.saveAndFlush(leave2);	

					ExtraHour newex= new ExtraHour();

        newex.setStaff_id(1);
        newex.setDate(LocalDate.now().minusDays(1));
        newex.setWorking_hour(4);
		newex.setStatus(LeaveStatusEnum.SUBMITTED);
		extraHourService.createExtraHour(newex);

		};
	}
}
