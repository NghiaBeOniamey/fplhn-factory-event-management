package fplhn.udpm.identity.infrastructure.excel.repository;

import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleExcelRepository extends ModuleRepository {

    @Query("SELECT m FROM Module m WHERE m.ma = :moduleCode")
    Optional<Module> findModuleByModuleCode(String moduleCode);

}
