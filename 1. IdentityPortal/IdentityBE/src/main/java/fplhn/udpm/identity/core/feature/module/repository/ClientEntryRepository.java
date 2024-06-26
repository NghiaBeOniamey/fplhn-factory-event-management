package fplhn.udpm.identity.core.feature.module.repository;

import fplhn.udpm.identity.core.feature.module.model.response.ClientResponse;
import fplhn.udpm.identity.repository.ClientRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientEntryRepository extends ClientRepository {

    @Query(
            value = """
                    SELECT
                        c.client_id as clientId,
                        c.client_secret as clientSecret
                    FROM client c
                    LEFT JOIN module m ON c.module_id = m.id
                    WHERE m.id = :id
                    """,
            nativeQuery = true
    )
    ClientResponse findByModuleId(Long id);

}
