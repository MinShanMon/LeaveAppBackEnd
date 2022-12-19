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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.JoinColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // @JsonIgnore
    // @ManyToMany(targetEntity = Staff.class, cascade = {CascadeType.ALL, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    // @JoinTable(name = "staff_role", joinColumns = {@JoinColumn(name = "rold_id", referencedColumnName = "role_id")}, 
    // inverseJoinColumns = {@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")})
    // private List<Role> roles;
}