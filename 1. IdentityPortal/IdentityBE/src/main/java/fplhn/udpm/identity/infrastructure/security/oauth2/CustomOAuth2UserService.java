package fplhn.udpm.identity.infrastructure.security.oauth2;

import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.StaffRoleModule;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.exception.OAuth2AuthenticationProcessingException;
import fplhn.udpm.identity.infrastructure.security.oauth2.user.OAuth2UserInfo;
import fplhn.udpm.identity.infrastructure.security.oauth2.user.OAuth2UserInfoFactory;
import fplhn.udpm.identity.infrastructure.security.repository.ModuleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.RoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffModuleRoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
import fplhn.udpm.identity.infrastructure.security.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final StaffAuthRepository staffAuthRepository;

    private final StudentAuthRepository studentAuthRepository;

    private final RoleAuthRepository roleAuthRepository;

    private final StaffModuleRoleAuthRepository staffModuleRoleAuthRepository;

    private final ModuleAuthRepository moduleAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(
                        oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                        oAuth2User.getAttributes()
                );
        if (oAuth2UserInfo.getEmail() == null || oAuth2UserInfo.getEmail().isBlank()) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Staff> staffOptional = staffAuthRepository.findByAccountFE(oAuth2UserInfo.getEmail());
        Optional<Staff> staffOptionalFPT = staffAuthRepository.findByAccountFPT(oAuth2UserInfo.getEmail());
        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            Staff staffExist = (Staff) updateExistingUser(staff, oAuth2UserInfo);
            List<String> roles = roleAuthRepository.findRoleByStaffId(staffExist.getId());
            return UserPrincipal.create(staffExist, oAuth2User.getAttributes(), roles);
        } else if (staffOptionalFPT.isPresent()) {
            Staff staff = staffOptionalFPT.get();
            Staff staffExist = (Staff) updateExistingUser(staff, oAuth2UserInfo);
            List<String> roles = roleAuthRepository.findRoleByStaffId(staffExist.getId());
            return UserPrincipal.create(staffExist, oAuth2User.getAttributes(), roles);
        }

        Optional<Student> studentOptional = studentAuthRepository.findByEmailFpt(oAuth2UserInfo.getEmail());
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            Student updateExistingStudent = (Student) updateExistingUser(student, oAuth2UserInfo);
            return UserPrincipal.create(updateExistingStudent);
        }

        Object user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        if (user instanceof Staff staff) {
            return UserPrincipal.create(staff);
        } else if (user instanceof Student student) {
            return UserPrincipal.create(student);
        } else {
            throw new OAuth2AuthenticationProcessingException("Invalid user type");
        }
    }

    private Object registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        String email = oAuth2UserInfo.getEmail();
        if (email.matches(".*[a-zA-Z]+\\d{5}@fpt\\.edu\\.vn$") || email.endsWith("@gmail.com")) {
            if (email.matches("p[a-zA-Z]\\d{5}@fpt\\.edu\\.vn$")) {
                String maSinhVien = email.substring(0, email.indexOf("@"));
                Student student = new Student();
                student.setEmailFpt(email);
                student.setStudentCode(maSinhVien);
                student.setFullName(oAuth2UserInfo.getName());
                student.setAvatar(oAuth2UserInfo.getImageUrl());
                student.setEntityStatus(EntityStatus.NOT_DELETED);
                return studentAuthRepository.save(student);
            }
            Student student = new Student();
            student.setEmailFpt(email);
            student.setFullName(oAuth2UserInfo.getName());
            student.setAvatar(oAuth2UserInfo.getImageUrl());
            student.setEntityStatus(EntityStatus.NOT_DELETED);
            return studentAuthRepository.save(student);
        } else if (email.endsWith("@fpt.edu.vn") || email.endsWith("@fe.edu.vn")) {
            Staff staff = new Staff();
            if (email.endsWith("@fpt.edu.vn")) {
                staff.setAccountFPT(email);
            } else {
                staff.setAccountFE(email);
            }
            staff.setStaffCode(email.substring(0, email.indexOf("@")));
            staff.setAvatar(oAuth2UserInfo.getImageUrl());
            staff.setFullName(oAuth2UserInfo.getName());
            staff.setEntityStatus(EntityStatus.NOT_DELETED);
            Role role = createRoleStaffIfNotFound();
            List<Module> modules = moduleAuthRepository.findAll();
            for (Module module : modules) {
                StaffRoleModule staffRoleModule = new StaffRoleModule();
                staffRoleModule.setStaff(staff);
                staffRoleModule.setRole(role);
                staffRoleModule.setModule(module);
                staffModuleRoleAuthRepository.save(staffRoleModule);
            }
            return staffAuthRepository.save(staff);
        } else {
            throw new OAuth2AuthenticationProcessingException("Invalid email format");
        }
    }

    private Object updateExistingUser(Student existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFullName(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        if (existingUser.getEntityStatus() == null) existingUser.setEntityStatus(EntityStatus.NOT_DELETED);
        return studentAuthRepository.save(existingUser);
    }

    private Object updateExistingUser(Staff existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFullName(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        if (existingUser.getEntityStatus() == null) existingUser.setEntityStatus(EntityStatus.NOT_DELETED);
        return staffAuthRepository.save(existingUser);
    }

    private Role createRoleStaffIfNotFound() {
        Optional<Role> role = roleAuthRepository.findRoleStaff();
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setCode("GIAO_VIEN");
            newRole.setName("GIAO_VIEN");
            return roleAuthRepository.save(newRole);
        }
        return role.get();
    }

}
