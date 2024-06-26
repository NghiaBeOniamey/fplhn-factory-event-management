package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleAuthRepository extends ModuleRepository {

    @Query("SELECT m FROM Module m WHERE m.url = :url")
    Optional<Module> findByUrl(String url);

    @Query("SELECT m.url FROM Module m")
    List<String> getAllUrl();


    @Query("SELECT m FROM Module m WHERE m.url LIKE %:defaultTargetUrlIdentity%")
    Optional<Module> findByIdentityStartWithUrl(String defaultTargetUrlIdentity);

}
