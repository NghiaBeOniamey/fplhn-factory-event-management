package fplhn.udpm.identity.infrastructure.config.database.repository;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDefaultRepository extends RoleRepository {

    Optional<Role> findByCode(String ma);

}
