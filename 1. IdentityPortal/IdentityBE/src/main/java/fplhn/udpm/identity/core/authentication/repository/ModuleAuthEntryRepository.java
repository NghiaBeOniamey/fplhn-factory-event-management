package fplhn.udpm.identity.core.authentication.repository;

import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleAuthEntryRepository extends ModuleRepository {

    Optional<Module> findByUrl(String url);

    Optional<Module> findByMa(String ma);

    @Query("SELECT m FROM Module m WHERE m.url LIKE %:defaultTargetUrlIdentity%")
    Optional<Module> findByIdentityStartWithUrl(String defaultTargetUrlIdentity);

}
