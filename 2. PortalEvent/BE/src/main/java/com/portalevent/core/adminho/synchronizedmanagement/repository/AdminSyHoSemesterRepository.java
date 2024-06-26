package com.portalevent.core.adminho.synchronizedmanagement.repository;

import com.portalevent.entity.Major;
import com.portalevent.entity.Semester;
import com.portalevent.repository.SemesterRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminSyHoSemesterRepository extends SemesterRepository {

    Optional<Semester> findBySemesterId(Long id);
}
