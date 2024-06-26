package com.portalevent.repository;

import com.portalevent.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(DepartmentRepository.NAME)
public interface DepartmentRepository extends JpaRepository<Department, String> {

    public static final String NAME = "BaseDepartmentRepository";

}
