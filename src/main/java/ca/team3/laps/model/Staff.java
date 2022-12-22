package ca.team3.laps.model;

import java.sql.Date;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ca.team3.laps.model.LeaveTypes.LeaveType;
import lombok.Data;

import javax.persistence.JoinColumn;

@Data
@Entity
@Table(name = "Staffs")
public class Staff {


    @Id
    @Column(name="staff_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stfId;

    @Column(name = "managerid", nullable = true)
    private String managerId;

    
	@Column(nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 64)
	private String password;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(name = "job_title", nullable = false)
	private String title;

	private String firstname;

	private String lastname;

	private boolean status;

	@Column(name = "one_time_password")
	private String otp;

	@Column(name = "otp_requested_time")
	private Date otpReqTime;

	@Column(name="anual_leave_entitlemnt")
	private int anuLeave;

	@Column(name="medi_requested_entitlement")
	private int mediLeave;

	@Column(name="comp_requested_entitlement")
	private double compLeave;

	@Column(name = "failed_attempt", nullable = false)
	private int failedAttempt;
    
    @JsonIgnore
    @OneToMany(mappedBy = "leave")
	private List<Leave> staffLeave;


    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.ALL, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "staff_role", joinColumns = {@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")}, 
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private List<Role> roles;


    public Staff(){}
    
    public Staff(String managerId, String username, String password,String title,
            String firstname, String lastname, boolean status, String email, int anuLeave, int mediLeave,
            double compLeave) {
        this.managerId = managerId;
        this.username = username;
        this.password = password;
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
