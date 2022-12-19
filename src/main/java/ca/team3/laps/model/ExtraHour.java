package ca.team3.laps.model;



import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ExtraHour")
public class ExtraHour {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="extraHour_id",nullable=false)
    @Id
    private int id;

    private int Staff_id;

    private LocalDate date;

    @Column(name = "status", columnDefinition = "ENUM('SUBMITTED', 'APPROVED', 'UPDATED', 'REJECTED') default 'SUBMITTED'")
    @Enumerated(EnumType.STRING)
    private LeaveStatusEnum status;

    private int working_hour;

    
}
