package ca.team3.laps.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ca.team3.laps.model.LeaveTypes.LeaveType;
import lombok.Data;

import javax.persistence.JoinColumn;

@Data
@Entity
public class Staff {


    @Id
    @Column(name="staff_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stfId;

    @Column(name = "managerid")
    private String managerId;

    private String username;

    private String password;

    @Column(name = "role_id", nullable = false)
    private int roleId;

    @Column(name="job_title", nullable = false)
    private String title;

    private String firstname;

    private String lastname;

    private boolean status;

    private String email;

    @Column(name="annual_leave_entitlement", nullable = false)
	private int anuLeave;

	@Column(name="medi_requested_entitlement", nullable = false)
    private int mediLeave;

	@Column(name="comp_requested_entitlement", nullable = false)
    private int compLeave;

    @JsonIgnore
    @OneToMany(mappedBy = "leave")
	private List<Leave> staffLeave;

    @ManyToMany(targetEntity = LeaveType.class,cascade = {CascadeType.ALL, CascadeType.PERSIST}, fetch=FetchType.EAGER)
    @JoinTable(name = "StaffAndLeaveType", joinColumns = {
        @JoinColumn(name="staff_id", referencedColumnName = "staff_id")},
        inverseJoinColumns = { @JoinColumn(name="LTid", referencedColumnName = "id")}
        )
        private List<LeaveType> LTSet;


    public Staff(){}
    
    public Staff(int stfId, String managerId, String username, String password, int roleId, String title,
            String firstname, String lastname, boolean status, String email, int anuLeave, int mediLeave,
            int compLeave) {
        this.stfId = stfId;
        this.managerId = managerId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
        this.email = email;
        this.anuLeave = anuLeave;
        this.mediLeave = mediLeave;
        this.compLeave = compLeave;
    }
    

}
