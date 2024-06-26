package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.CampusConnectionResponse;
import fplhn.udpm.identity.repository.CampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusConnectorRepository extends CampusRepository {

    @Query(
            value = """
                    SELECT
                        id as campusId,
                        ma as campusCode,
                        ten as campusName
                    FROM
                        co_so
                    WHERE xoa_mem LIKE 'NOT_DELETED'
                    """,
            nativeQuery = true
    )
    List<CampusConnectionResponse> getAllByDeleteStatus();

}
