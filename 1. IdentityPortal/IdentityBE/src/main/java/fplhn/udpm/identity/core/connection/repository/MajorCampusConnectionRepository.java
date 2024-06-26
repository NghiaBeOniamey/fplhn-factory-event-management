package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.MajorCampusConnectionResponse;
import fplhn.udpm.identity.repository.MajorCampusRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MajorCampusConnectionRepository extends MajorCampusRepository {

    @Query(
            value = """
                    SELECT
                        cntcs.id as majorCampusId,
                        cntcs.id_bo_mon_theo_co_so as departmentCampusId,
                        cntcs.id_chuyen_nganh as majorId,
                        nv.tai_khoan_fpt as emailHeadMajorFpt,
                        nv.tai_khoan_fe as emailHeadMajorFe
                    FROM
                        chuyen_nganh_theo_co_so cntcs
                    LEFT JOIN nhan_vien nv on cntcs.id_truong_mon = nv.id
                    WHERE cntcs.xoa_mem LIKE 'NOT_DELETED'
                    """,
            nativeQuery = true
    )
    List<MajorCampusConnectionResponse> getAllByDeleteStatus();

}
