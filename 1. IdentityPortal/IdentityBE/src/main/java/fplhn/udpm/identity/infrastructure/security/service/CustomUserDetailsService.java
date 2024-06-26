package fplhn.udpm.identity.infrastructure.security.service;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.security.repository.RoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
import fplhn.udpm.identity.infrastructure.security.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final StaffAuthRepository staffRepository;

    private final StudentAuthRepository studentRepository;

    private final RoleAuthRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<Staff> staffFpt = staffRepository.findByAccountFPT(email);
        if (staffFpt.isPresent()) {
            List<String> roles = roleRepository.findRoleByStaffId(staffFpt.get().getId());
            return UserPrincipal.create(staffFpt.get(), roles);
        }

        Optional<Staff> staffFe = staffRepository.findByAccountFE(email);
        if (staffFe.isPresent()) {
            List<String> roles = roleRepository.findRoleByStaffId(staffFe.get().getId());
            return UserPrincipal.create(staffFe.get(), roles);
        }

        if (studentRepository.findByEmailFpt(email).isPresent()) {
            log.info("Student: ");
            return UserPrincipal.create(studentRepository.findByEmailFpt(email).get());
        }

        throw new UsernameNotFoundException("User not found with email : " + email);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isPresent()) {
            List<String> roles = roleRepository.findRoleByStaffId(id);
            return UserPrincipal.create(staff.get(), roles);
        }
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return UserPrincipal.create(student.get());
        }
        throw new UsernameNotFoundException("User not found with id : " + id);
    }

}