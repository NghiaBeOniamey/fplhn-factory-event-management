package fplhn.udpm.identity.infrastructure.config.database.repository;

import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.repository.DepartmentRepository;

public interface DepartmentTempRepository extends DepartmentRepository {

    Department findByName(String ten);

}
