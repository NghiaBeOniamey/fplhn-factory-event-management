package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.SemesterConnectionResponse;
import fplhn.udpm.identity.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterConnectionRepository extends SemesterRepository {

    @Query(
            value = """
                    SELECT
                        hk.id AS id,
                        hk.ten_hoc_ky AS semesterName,
                        hk.bat_dau AS startTime,
                        hk.ket_thuc AS endTime,
                        hk.bat_dau_block_1 AS startTimeFirstBlock,
                        hk.ket_thuc_block_1 AS endTimeFirstBlock,
                        hk.bat_dau_block_2 AS startTimeSecondBlock,
                        hk.ket_thuc_block_2 AS endTimeSecondBlock
                    FROM
                        hoc_ky hk
                    """,
            nativeQuery = true
    )
    List<SemesterConnectionResponse> findAllSemesterConnection();

}
