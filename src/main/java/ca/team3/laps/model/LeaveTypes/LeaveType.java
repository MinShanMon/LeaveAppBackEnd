package ca.team3.laps.model.LeaveTypes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.team3.laps.model.Staff;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
@Entity
@Inheritance
@DiscriminatorColumn(name = "Leave_Type")
@Data
@NoArgsConstructor
@Table(name = "LeaveType")
public abstract class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float granularity;
    private float leaveDays;
    @Column(name = "Leave_Type", nullable = false, updatable = false, insertable = false)
    private String leaveType;

    @JsonIgnore
    @ManyToMany(targetEntity = Staff.class,cascade = {CascadeType.ALL, CascadeType.PERSIST}, fetch=FetchType.EAGER)
    @JoinTable(name = "StaffAndLeaveType", joinColumns = {
    @JoinColumn(name="LTid", referencedColumnName = "id")},
    inverseJoinColumns = { @JoinColumn(name="staff_id", referencedColumnName = "staff_id")}
    )
    private List<LeaveType> LTSet;

    public LeaveType(float granularity, float leaveDays) {
        this.granularity = granularity;
        this.leaveDays = leaveDays;
    }
}
