package ca.team3.laps.model.LeaveTypes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("MC_Leave")
@Data
@NoArgsConstructor
public class MedicalLeave extends LeaveType {

    public MedicalLeave(float granularity, float leaveDays) {
        super(granularity, leaveDays);
    }
}
