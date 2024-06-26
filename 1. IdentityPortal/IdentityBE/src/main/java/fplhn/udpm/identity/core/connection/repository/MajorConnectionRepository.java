package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.MajorConnectionResponse;
import fplhn.udpm.identity.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MajorConnectionRepository extends MajorRepository {

    @Query(
            value = """
                    SELECT
                        id as majorId,
                        id_bo_mon as departmentId,
                        ma as majorCode,
                        ten as majorName
                    FROM
                        chuyen_nganh
                    WHERE xoa_mem LIKE 'NOT_DELETED'
                    """,
            nativeQuery = true
    )
    List<MajorConnectionResponse> getAllByDeleteStatus();

}
