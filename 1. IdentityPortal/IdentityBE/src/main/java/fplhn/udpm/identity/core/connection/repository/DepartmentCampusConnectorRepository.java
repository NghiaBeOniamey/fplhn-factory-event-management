package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.DepartmentCampusConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentOldResponse;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentCampusConnectorRepository extends DepartmentCampusRepository {

    @Query(
            value = """
                    SELECT
                        d.id as departmentId,
                        d.ma as departmentCode,
                        d.ten as departmentName,
                        nv.tai_khoan_fpt as emailHeadDepartmentFpt,
                        nv.tai_khoan_fe as emailHeadDepartmentFe,
                        c.ma as campusCode,
                        c.id as campusId
                    FROM
                        bo_mon d
                    LEFT JOIN dbo.bo_mon_theo_co_so bmtcs on d.id = bmtcs.id_bo_mon
                    LEFT JOIN dbo.nhan_vien nv on bmtcs.id_chu_nhiem_bo_mon = nv.id
                    LEFT JOIN dbo.co_so c on bmtcs.id_co_so = c.id
                    WHERE c.ma = :campusCode
                    """,
            nativeQuery = true
    )
    List<DepartmentOldResponse> getDepartmentByCampusCode(String campusCode);

    @Query(
            value = """
                    SELECT
                        bmtcs.id as departmentCampusId,
                        bmtcs.id_bo_mon as departmentId,
                        bmtcs.id_co_so as campusId,
                        nv.tai_khoan_fpt as emailHeadDepartmentFpt,
                        nv.tai_khoan_fe as emailHeadDepartmentFe
                    FROM
                        bo_mon_theo_co_so bmtcs
                    LEFT JOIN nhan_vien nv on bmtcs.id_chu_nhiem_bo_mon = nv.id
                    WHERE bmtcs.xoa_mem LIKE 'NOT_DELETED'
                    """,
            nativeQuery = true
    )
    List<DepartmentCampusConnectionResponse> getAllByDeleteStatus();

}
