package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.DepartmentConnectionResponse;
import fplhn.udpm.identity.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentConnectorRepository extends DepartmentRepository {

    @Query(
            value = """
                    SELECT
                        id as departmentId,
                        ma as departmentCode,
                        ten as departmentName
                    FROM
                        bo_mon
                    WHERE xoa_mem = 'NOT_DELETED'
                    """,
            nativeQuery = true
    )
    List<DepartmentConnectionResponse> getAllByDeleteStatus();

}
