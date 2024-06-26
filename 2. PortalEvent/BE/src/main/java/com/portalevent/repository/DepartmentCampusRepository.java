package com.portalevent.repository;

import com.portalevent.entity.DepartmentCampus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(DepartmentCampusRepository.NAME)
public interface DepartmentCampusRepository extends JpaRepository<DepartmentCampus, String> {

    public static final String NAME = "BaseDepartmentCampusRepository";

}
