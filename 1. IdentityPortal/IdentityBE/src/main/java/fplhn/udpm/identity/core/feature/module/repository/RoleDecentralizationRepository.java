package fplhn.udpm.identity.core.feature.module.repository;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDecentralizationRepository extends RoleRepository {

    List<Role> findAllByCode(String ma);

    @Query(value = "SELECT * FROM chuc_vu WHERE ma IN :rolesToAdd", nativeQuery = true)
    List<Role> findAllByCodeIn(List<String> rolesToAdd);
}
