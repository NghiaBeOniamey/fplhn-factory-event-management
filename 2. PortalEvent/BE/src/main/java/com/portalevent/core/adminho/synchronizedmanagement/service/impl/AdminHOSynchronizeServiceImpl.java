package com.portalevent.core.adminho.synchronizedmanagement.service.impl;

import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOCampusRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHODepartmentCampusRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHODepartmentRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOMajorCampusRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOMajorRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOSemesterRequest;
import com.portalevent.core.adminho.synchronizedmanagement.repository.AdminSyHOCampusRepository;
import com.portalevent.core.adminho.synchronizedmanagement.repository.AdminSyHODepartmentCampusRepository;
import com.portalevent.core.adminho.synchronizedmanagement.repository.AdminSyHODepartmentRepository;
import com.portalevent.core.adminho.synchronizedmanagement.repository.AdminSyHOMajorCampusRepository;
import com.portalevent.core.adminho.synchronizedmanagement.repository.AdminSyHOMajorRepository;
import com.portalevent.core.adminho.synchronizedmanagement.repository.AdminSyHoSemesterRepository;
import com.portalevent.core.adminho.synchronizedmanagement.service.AdminHOSynchronizeService;
import com.portalevent.entity.Campus;
import com.portalevent.entity.Department;
import com.portalevent.entity.DepartmentCampus;
import com.portalevent.entity.Major;
import com.portalevent.entity.MajorCampus;
import com.portalevent.entity.Semester;
import com.portalevent.util.CallApiIdentity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AdminHOSynchronizeServiceImpl implements AdminHOSynchronizeService {

    private final CallApiIdentity callApiIdentity;

    private final AdminSyHOCampusRepository adminHOCampusRepository;

    private final AdminSyHODepartmentRepository adminHODepartmentRepository;

    private final AdminSyHOMajorRepository adminHOMajorRepository;

    private final AdminSyHODepartmentCampusRepository adminHODepartmentCampusRepository;

    private final AdminSyHOMajorCampusRepository adminHOMajorCampusRepository;

    private final AdminSyHoSemesterRepository adminSyHoSemesterRepository;

    public AdminHOSynchronizeServiceImpl(
            CallApiIdentity callApiIdentity,
            AdminSyHOCampusRepository adminHOCampusRepository,
            AdminSyHODepartmentRepository adminHODepartmentRepository,
            AdminSyHOMajorRepository adminHOMajorRepository,
            AdminSyHODepartmentCampusRepository adminHODepartmentCampusRepository,
            AdminSyHOMajorCampusRepository adminHOMajorCampusRepository,
            AdminSyHoSemesterRepository adminSyHoSemesterRepository
    ) {
        this.callApiIdentity = callApiIdentity;
        this.adminHOCampusRepository = adminHOCampusRepository;
        this.adminHODepartmentRepository = adminHODepartmentRepository;
        this.adminHOMajorRepository = adminHOMajorRepository;
        this.adminHODepartmentCampusRepository = adminHODepartmentCampusRepository;
        this.adminHOMajorCampusRepository = adminHOMajorCampusRepository;
        this.adminSyHoSemesterRepository = adminSyHoSemesterRepository;
    }

    @Override
    public void synchronizeAllIdentity() {

        synchronizeCampusIdentity();

        synchronizeDepartmentIdentity();

        synchronizeMajorIdentity();

        synchronizeDepartmentCampusIdentity();

        synchronizeMajorCampusIdentity();

    }

    @Override
    public void synchronizeCampusIdentity() {

        List<AdminHOCampusRequest> campusesList
                = callApiIdentity.handleCallApiGetCampusByStatus();
        List<Campus> campuses = new ArrayList<>();
        for (AdminHOCampusRequest request : campusesList) {
            Optional<Campus> campusOptional = adminHOCampusRepository
                    .findByCampusId(request.getCampusId());
            if (campusOptional.isPresent()) {
                campusOptional.get().setCode(request.getCampusCode());
                campusOptional.get().setName(request.getCampusName());
                campuses.add(campusOptional.get());
            } else {
                Campus campus = getCampus(request);

                campuses.add(campus);
            }
        }
        adminHOCampusRepository.saveAll(campuses);
        
    }

    private static Campus getCampus(AdminHOCampusRequest request) {
        Campus campus = new Campus();
        campus.setCampusId(request.getCampusId());
        campus.setCode(request.getCampusCode());
        campus.setName(request.getCampusName());
        return campus;
    }

    @Override
    public void synchronizeDepartmentIdentity() {

        List<AdminHODepartmentRequest> adminHODepartmentRequests
                = callApiIdentity.handleCallApiGetDepartmentsByStatus();
        List<Department> departments = new ArrayList<>();
        for (AdminHODepartmentRequest request : adminHODepartmentRequests){
            Optional<Department> departmentOptional = adminHODepartmentRepository
                    .findByDepartmentId(request.getDepartmentId());
            if (departmentOptional.isPresent()) {
                departmentOptional.get().setCode(request.getDepartmentCode());
                departmentOptional.get().setName(request.getDepartmentName());
                departments.add(departmentOptional.get());
            } else {
                Department department = getDepartment(request);

                departments.add(department);
            }
        }
        adminHODepartmentRepository.saveAll(departments);

    }

    private static Department getDepartment(AdminHODepartmentRequest request) {
        Department department = new Department();
        department.setDepartmentId(request.getDepartmentId());
        department.setCode(request.getDepartmentCode());
        department.setName(request.getDepartmentName());
        return department;
    }

    @Override
    public void synchronizeMajorIdentity() {

        List<AdminHOMajorRequest> adminHOMajorRequests
                = callApiIdentity.handleCallApiGetMajorsByStatus();
        List<Major> majors = new ArrayList<>();
        for (AdminHOMajorRequest request : adminHOMajorRequests){
            Optional<Major> majorOptional = adminHOMajorRepository
                    .findByMajorId(request.getMajorId());
            if (majorOptional.isPresent()) {
                majorOptional.get().setDepartmentId(request.getDepartmentId());
                majorOptional.get().setCode(request.getMajorCode());
                majorOptional.get().setName(request.getMajorName());
                majors.add(majorOptional.get());
            } else {
                Major major = getMajor(request);

                majors.add(major);
            }
        }
        adminHOMajorRepository.saveAll(majors);


    }

    private static Major getMajor(AdminHOMajorRequest request) {
        Major major = new Major();
        major.setMajorId(request.getMajorId());
        major.setDepartmentId(request.getDepartmentId());
        major.setCode(request.getMajorCode());
        major.setName(request.getMajorName());
        return major;
    }

    @Override
    public void synchronizeDepartmentCampusIdentity() {

        List<AdminHODepartmentCampusRequest> adminHODepartmentCampusRequests
                = callApiIdentity.handleCallApiGetDepartmentCampusByStatus();
        List<DepartmentCampus> departmentCampuses = new ArrayList<>();
        for (AdminHODepartmentCampusRequest request : adminHODepartmentCampusRequests){
            Optional<DepartmentCampus> departmentCampusOptional = adminHODepartmentCampusRepository
                    .findByDepartmentCampusId(request.getDepartmentCampusId());
            if (departmentCampusOptional.isPresent()) {
                departmentCampusOptional.get().setDepartmentId(request.getDepartmentId());
                departmentCampusOptional.get().setCampusId(request.getCampusId());
                departmentCampusOptional.get().setMail_of_manager(request.getEmailHeadDepartmentFe());
                departmentCampuses.add(departmentCampusOptional.get());
            } else {
                DepartmentCampus departmentCampus = getDepartmentCampus(request);

                departmentCampuses.add(departmentCampus);
            }
        }
        adminHODepartmentCampusRepository.saveAll(departmentCampuses);

    }

    private static DepartmentCampus getDepartmentCampus(AdminHODepartmentCampusRequest request) {
        DepartmentCampus departmentCampus = new DepartmentCampus();
        departmentCampus.setDepartmentCampusId(request.getDepartmentCampusId());
        departmentCampus.setDepartmentId(request.getDepartmentId());
        departmentCampus.setCampusId(request.getCampusId());
        departmentCampus.setMail_of_manager(request.getEmailHeadDepartmentFe());
        return departmentCampus;
    }

    @Override
    public void synchronizeMajorCampusIdentity() {

        List<AdminHOMajorCampusRequest> adminHOMajorCampusRequests
                = callApiIdentity.handleCallApiGetMajorCampusByStatus();
        List<MajorCampus> majorCampuses = new ArrayList<>();
        for (AdminHOMajorCampusRequest request : adminHOMajorCampusRequests){
            Optional<MajorCampus> majorCampusOptional = adminHOMajorCampusRepository
                    .findByMajorCampusId(request.getMajorCampusId());
            if (majorCampusOptional.isPresent()) {
                majorCampusOptional.get().setDepartmentCampusId(request.getDepartmentCampusId());
                majorCampusOptional.get().setMajorId(request.getMajorId());
                majorCampusOptional.get().setMail_of_manager(request.getEmailHeadMajorFE());
                majorCampuses.add(majorCampusOptional.get());
            } else {
                MajorCampus majorCampus = getMajorCampus(request);
                majorCampuses.add(majorCampus);
            }
        }
        adminHOMajorCampusRepository.saveAll(majorCampuses);

    }

    private static MajorCampus getMajorCampus(AdminHOMajorCampusRequest request) {
        MajorCampus majorCampus = new MajorCampus();
        majorCampus.setMajorCampusId(request.getMajorCampusId());
        majorCampus.setDepartmentCampusId(request.getDepartmentCampusId());
        majorCampus.setMajorId(request.getMajorId());
        majorCampus.setMail_of_manager(request.getEmailHeadMajorFE());
        return majorCampus;
    }

    @Override
    public void synchronizeSemestersIdentity() {

        List<AdminHOSemesterRequest> adminHOSemesterRequests
                = callApiIdentity.handleCallApiGetSemestersByStatus();
        List<Semester> semesters = new ArrayList<>();
        for (AdminHOSemesterRequest request : adminHOSemesterRequests){
            Optional<Semester> semesterOptional = adminSyHoSemesterRepository.
                    findBySemesterId(request.getId());
            if (semesterOptional.isPresent()) {
                semesterOptional.get().setStartTime(request.getStartTime() * 1000);
                semesterOptional.get().setEndTime(request.getEndTime() * 1000);
                semesterOptional.get().setStartTimeFirstBlock(request.getStartTimeFirstBlock() * 1000);
                semesterOptional.get().setEndTimeFirstBlock(request.getEndTimeFirstBlock() * 1000);
                semesterOptional.get().setStartTimeSecondBlock(request.getStartTimeSecondBlock() * 1000);
                semesterOptional.get().setEndTimeSecondBlock(request.getEndTimeSecondBlock() * 1000);
                semesterOptional.get().setName(request.getSemesterName());

                semesters.add(semesterOptional.get());
            } else {
                Semester semester = getSemester(request);

                semesters.add(semester);
            }
        }
        adminSyHoSemesterRepository.saveAll(semesters);

    }

    private static Semester getSemester(AdminHOSemesterRequest request) {
        Semester semester = new Semester();
        semester.setStartTime(request.getStartTime() * 1000);
        semester.setEndTime(request.getEndTime() * 1000);
        semester.setStartTimeFirstBlock(request.getStartTimeFirstBlock() * 1000);
        semester.setEndTimeFirstBlock(request.getEndTimeFirstBlock() * 1000);
        semester.setStartTimeSecondBlock(request.getStartTimeSecondBlock() * 1000);
        semester.setEndTimeSecondBlock(request.getEndTimeSecondBlock() * 1000);
        semester.setName(request.getSemesterName());
        semester.setSemesterId(request.getId());
        return semester;
    }

}
