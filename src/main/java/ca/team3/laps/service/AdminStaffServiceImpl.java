package ca.team3.laps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.team3.laps.model.Role;
import ca.team3.laps.model.Staff;
import ca.team3.laps.model.LeaveTypes.AnnualLeave;
import ca.team3.laps.model.LeaveTypes.CompensationLeave;
import ca.team3.laps.model.LeaveTypes.MedicalLeave;
import ca.team3.laps.repository.LeaveTypeRepo;
import ca.team3.laps.repository.RoleRepository;
import ca.team3.laps.repository.StaffRepo;

@Service
public class AdminStaffServiceImpl implements AdminStaffService {

    @Autowired
    StaffRepo staffRepo;

    @Autowired
    LeaveTypeRepo leaveTypeRepo;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Staff> findAllStaff() {
        return staffRepo.findAll();
    }

    @Override
    public List<Staff> findAllActiveStaff() {
        return staffRepo.findByStatusTrue();
    }

    @Override
    public Staff findStaffById(int id) {
        return staffRepo.findByStfId(id);
    }

    @Override
    public void createStaff(Staff staff) {
        createAccount(staff);
        setLeaveEntitlements(staff);
        staffRepo.save(staff);
    }

    @Override
    public void modifyStaff(Staff staff) {
        Staff staffRec = staffRepo.findByStfId(staff.getStfId());
        setRolesFromReactForm(staff, staffRec);
        staffRec.setAnuLeave(staff.getAnuLeave());
        staffRec.setTitle(staff.getTitle());
        staffRec.setMediLeave(staff.getMediLeave());
        staffRec.setCompLeave(staff.getCompLeave());
        staffRec.setStatus(staff.isStatus());
        staffRec.setEmail(staff.getEmail());
        staffRec.setManagerId(staff.getManagerId());
        staffRepo.saveAndFlush(staffRec);
    }

    private void setRolesFromReactForm(Staff staffForm, Staff staffRec) {
        List<Role> staffRolesFromForm = staffForm.getRoles();
        List<Role> staffRolesToSet = new ArrayList<>();
        staffRolesFromForm.forEach(e -> {
            Role role = roleRepository.getReferenceById(e.getId());
            staffRolesToSet.add(role);
        });
        staffRec.setRoles(staffRolesToSet);
    }

    @Override
    public void deleteStaff(Staff staff) {
        Staff staffRec = staffRepo.findByStfId(staff.getStfId());
        staffRec.setStatus(false);
        staffRepo.save(staffRec);
    }

    @Override
    public void updateAnLeaveEntitlement(Staff staff) {
        Staff staffRec = staffRepo.findByStfId(staff.getStfId());
        staffRec.setAnuLeave(staff.getAnuLeave());
        staffRepo.save(staffRec);
    }

    // Utils
    private void createAccount(Staff staff) {
        staff.setUsername(generateUsername(staff));
        staff.setPassword(generatePassword());
        staff.setStatus(true);
    }

    private String generateUsername(Staff staff) {
        String firstName = staff.getFirstname();
        String lastName = staff.getLastname();
        if (firstName == null || firstName.isEmpty()) {
            return lastName + countUsernameContaining(lastName);
        } else if (lastName == null || lastName.isEmpty()) {
            return firstName + countUsernameContaining(firstName);
        } else {
            String username = firstName + "." + lastName;
            return username + countUsernameContaining(username);
        }
    }

    private String countUsernameContaining(String name) {
        int count = staffRepo.countByUsernameContaining(name);
        if (count == 0) {
            return "";
        }
        return Integer.toString(count);
    }

    private String generatePassword() {
        Random rnd = new Random();
        char[] password = new char[8];
        for (int i = 0; i < password.length; i++) {
            password[i] = (char) (rnd.nextInt(90) + 33);
        }
        return String.copyValueOf(password);
    }

    private void setLeaveEntitlements(Staff staff) {
        AnnualLeave annualLeave = leaveTypeRepo.findByJobTitle(staff.getTitle());
        if (annualLeave == null) {
            staff.setAnuLeave(0);
        } else {
            staff.setAnuLeave((int) annualLeave.getLeaveDays());
        }
        MedicalLeave medicalLeave = leaveTypeRepo.findMedicalLeaveEntitlement();
        if (medicalLeave == null) {
            staff.setMediLeave(0);
        } else {
            staff.setMediLeave((int) leaveTypeRepo.findMedicalLeaveEntitlement().getLeaveDays());
        }
        CompensationLeave compLeave = leaveTypeRepo.findCompLeaveEntitlement();
        if (compLeave == null) {
            staff.setCompLeave(0);
        } else {
            staff.setCompLeave(leaveTypeRepo.findCompLeaveEntitlement().getLeaveDays());
        }
    }
}
