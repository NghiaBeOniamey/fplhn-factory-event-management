package com.portalevent.core.adminho.synchronizedmanagement.repository;

import com.portalevent.entity.DepartmentCampus;
import com.portalevent.repository.DepartmentCampusRepository;

import java.util.Optional;

public interface AdminSyHODepartmentCampusRepository extends DepartmentCampusRepository {

    Optional<DepartmentCampus> findByDepartmentCampusId(Long id);

}
