package com.portalevent.core.adminho.synchronizedmanagement.repository;

import com.portalevent.entity.Department;
import com.portalevent.repository.DepartmentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminSyHODepartmentRepository extends DepartmentRepository {

    Optional<Department> findByDepartmentId(Long id);

}
