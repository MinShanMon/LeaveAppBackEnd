package ca.team3.laps.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.team3.laps.exception.AdminException;
import ca.team3.laps.exception.ErrorJson;
import ca.team3.laps.model.LeaveTypes.AnnualLeave;
import ca.team3.laps.model.LeaveTypes.CompensationLeave;
import ca.team3.laps.model.LeaveTypes.LeaveType;
import ca.team3.laps.model.LeaveTypes.MedicalLeave;
import ca.team3.laps.repository.LeaveTypeRepo;
import ca.team3.laps.repository.StaffRepo;

@Service
public class AdminLeaveServiceImpl implements AdminLeaveService {

    @Autowired
    StaffRepo staffRepo;

    @Autowired
    LeaveTypeRepo leaveRepo;

    @Override
    public AnnualLeave createAnnualLeaveEntitlement(AnnualLeave annualLeave) throws AdminException {
        String jobTitle = annualLeave.getJobTitle().toLowerCase();
        if (leaveRepo.findByJobTitle(jobTitle) != null) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "annual leave entitlement for " + jobTitle + " already exists."));
        }
        return leaveRepo.save(annualLeave);
    }

    @Override
    public AnnualLeave modifyAnnualLeaveEntitlement(AnnualLeave annualLeave) throws AdminException {
        if (annualLeave.getGranularity() <= 0 || annualLeave.getLeaveDays() <= 0) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "granularity and leavedays must be more than zero"));
        }
        String jobTitle = annualLeave.getJobTitle().toLowerCase();
        AnnualLeave annualLeaveEntitlement = leaveRepo.findByJobTitle(jobTitle);
        if (annualLeaveEntitlement == null) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "annual leave entitlement for " + jobTitle + " not found. Please create one."));
        }
        annualLeaveEntitlement.setGranularity(annualLeave.getGranularity());
        annualLeaveEntitlement.setLeaveDays(annualLeave.getLeaveDays());
        return leaveRepo.save(annualLeaveEntitlement);
    }

    @Override
    public void deleteAnnualLeaveEntitlement(AnnualLeave annualLeave) throws AdminException {
        String jobTitle = annualLeave.getJobTitle().toLowerCase();
        AnnualLeave annualLeaveEntitlement = leaveRepo.findByJobTitle(jobTitle);
        if (annualLeaveEntitlement == null) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "annual leave entitlement for " + jobTitle + " does not exist."));
        }
        leaveRepo.delete(annualLeaveEntitlement);
    }

    @Override
    public MedicalLeave createMedicalLeaveEntitlement(MedicalLeave medicalLeave) throws AdminException {
        if (leaveRepo.findMedicalLeaveEntitlement() != null) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "Medical Leave Entitlement exists. Please edit instead."));
        }
        return leaveRepo.save(medicalLeave);
    }

    @Override
    public MedicalLeave modifyMedicalLeaveEntitlement(MedicalLeave medicalLeave) throws AdminException {
        MedicalLeave medicalLeaveEntitlement = leaveRepo.findMedicalLeaveEntitlement();
        if (medicalLeaveEntitlement == null) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "Medical Leave Entitlement not found. Please create a new one."));
        }
        medicalLeaveEntitlement.setGranularity(medicalLeave.getGranularity());
        medicalLeaveEntitlement.setLeaveDays(medicalLeave.getLeaveDays());
        return leaveRepo.save(medicalLeaveEntitlement);
    }

    @Override
    public CompensationLeave createCompensationLeave(CompensationLeave compensationLeave) throws AdminException {
        if (leaveRepo.findCompLeaveEntitlement() != null) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "Compensation Leave Entitlement exists. Please edit instead."));
        }
        compensationLeave.setLeaveDays(0);
        return leaveRepo.save(compensationLeave);
    }

    @Override
    public CompensationLeave modifyCompensationLeave(CompensationLeave compensationLeave) throws AdminException {
        CompensationLeave compLeaveEntitlement = leaveRepo.findCompLeaveEntitlement();
        if (compLeaveEntitlement == null) {
            throw new AdminException(
                    new ErrorJson(HttpStatus.BAD_REQUEST.value(),
                            "Compensation Leave Entitlement not found. Please create a new one."));
        }
        compLeaveEntitlement.setGranularity(compensationLeave.getGranularity());
        compLeaveEntitlement.setLeaveDays(compensationLeave.getLeaveDays());
        compLeaveEntitlement.setOvertimeRatio(compensationLeave.getOvertimeRatio());
        return leaveRepo.save(compLeaveEntitlement);
    }

    @Override
    public List<AnnualLeave> getAnnualLeaveEntitlements() {
        return leaveRepo.findAnnualLeavesEntitlement();
    }

    @Override
    public MedicalLeave getMedicalLeaveEntitlement() {
        return leaveRepo.findMedicalLeaveEntitlement();
    }

    @Override
    public CompensationLeave getCompLeaveEntitlement() {
        return leaveRepo.findCompLeaveEntitlement();
    }

    @Override
    public List<LeaveType> getAllLeaveEntitlements() {
        List<LeaveType> entitlements = new ArrayList<LeaveType>();
        getAnnualLeaveEntitlements().forEach(e -> entitlements.add(e));
        if (getMedicalLeaveEntitlement() != null) {
            entitlements.add(getMedicalLeaveEntitlement());
        }
        if (getCompLeaveEntitlement() != null) {
            entitlements.add(getCompLeaveEntitlement());
        }
        return entitlements;
    }
}
