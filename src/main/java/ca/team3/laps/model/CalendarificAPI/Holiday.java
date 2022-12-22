package ca.team3.laps.model.CalendarificAPI;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;

    @JsonCreator
    public Holiday(String name, Date date) {
        this.name = name;
        this.date = date.getIso();
    }

    public Holiday(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    @Data
    static class Date {
        private LocalDate iso;

        @JsonCreator
        public Date(@JsonFormat(pattern = "yyyy-MM-dd") LocalDate iso) {
            this.iso = iso;
        }
    }
}
