package ca.team3.laps.model.LeaveTypes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("Comp_Leave")
@NoArgsConstructor
public class CompensationLeave extends LeaveType {

    private float overtimeRatio;

    public CompensationLeave(float granularity, float leaveDays, float overtimeRatio) {
        super(granularity, leaveDays);
        this.overtimeRatio = overtimeRatio;
    }
}