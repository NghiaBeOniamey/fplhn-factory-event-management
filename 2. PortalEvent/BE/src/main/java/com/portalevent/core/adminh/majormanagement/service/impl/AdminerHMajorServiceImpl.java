package com.portalevent.core.adminh.majormanagement.service.impl;//package com.portalevent.core.adminh.majormanagement.service.impl;
//
//import com.portalevent.core.adminh.majormanagement.model.response.AdminerHMajorResponse;
//import com.portalevent.core.adminh.majormanagement.repository.AdminerHMajorRepository;
//import com.portalevent.core.adminh.majormanagement.service.AdminerHMajorService;
//import com.portalevent.core.common.DepartmentResponse;
//import com.portalevent.entity.Major;
//import com.portalevent.util.CallApiIdentity;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AdminerHMajorServiceImpl implements AdminerHMajorService {
//
//    private final AdminerHMajorRepository majorRepository;
//
//    private final CallApiIdentity callApiIdentity;
//
//    public AdminerHMajorServiceImpl(AdminerHMajorRepository majorRepository, CallApiIdentity callApiIdentity) {
//        this.majorRepository = majorRepository;
//        this.callApiIdentity = callApiIdentity;
//    }
//
//    @Override
//    public void fetchAndSaveDepartments() {
//        List<DepartmentResponse> departments = callApiIdentity.handleCallApiGetDepartments();
//        List<Major> majorList = new ArrayList<>();
//        for (DepartmentResponse department : departments) {
//            Optional<Major> majorOptional = majorRepository
//                    .findByIdLongAndCampusId(department.getDepartmentId(), department.getCampusId());
//            if (majorOptional.isPresent()) {
//                majorOptional.get().setCode(department.getDepartmentCode());
//                majorOptional.get().setName(department.getDepartmentName());
//                majorOptional.get().setMailOfManager(department.getEmailHeadDepartmentFe());
//                majorOptional.get().setCampusId(department.getCampusId());
//                majorOptional.get().setCampusCode(department.getCampusCode());
//                majorList.add(majorOptional.get());
//            } else {
//                Major major = new Major();
//                major.setIdLong(department.getDepartmentId());
//                major.setCode(department.getDepartmentCode());
//                major.setName(department.getDepartmentName());
//                major.setMailOfManager(department.getEmailHeadDepartmentFe());
//                major.setCampusId(department.getCampusId());
//                major.setCampusCode(department.getCampusCode());
//                majorList.add(major);
//            }
//        }
//        majorRepository.saveAll(majorList);
//    }
//
//    /**
//     * @param value mã, tên, email trưởng bộ môn
//     * @return danh sách bộ môn
//     */
//    @Override
//    public List<AdminerHMajorResponse> getMajorList(String value, String campusCode) {
//        return majorRepository.getMajorList(value, campusCode);
//    }
//
//    /**
//     * @return danh sách bộ môn
//     */
//    @Override
//    public List<AdminerHMajorResponse> getAllMajors(String campusCode) {
//        return majorRepository.getAllMajors(campusCode);
//    }
//}
