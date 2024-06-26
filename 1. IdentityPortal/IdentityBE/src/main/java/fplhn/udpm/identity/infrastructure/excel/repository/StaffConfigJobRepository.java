package fplhn.udpm.identity.infrastructure.excel.repository;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.excel.model.StaffExportDTO;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("excelNhanVienRepository")
public interface StaffConfigJobRepository extends StaffRepository {

    Optional<Staff> findByStaffCode(String maNhanVien);

    @Query("SELECT s.staffCode FROM Staff s")
    List<String> findAllStaffCode();

    @Query("""
            SELECT s FROM Staff s
            LEFT JOIN s.departmentCampus dc
            WHERE dc.campus.code = :campusCode
            """)
    List<Staff> findAllByCampusCode(String campusCode);

    @Query(
            value = """
                    SELECT
                        ROW_NUMBER() OVER (ORDER BY nv.id) as orderNumber,
                        nv.ma_nhan_vien as staffCode,
                        nv.ho_ten as fullName,
                        nv.tai_khoan_fpt as mailFpt,
                        nv.tai_khoan_fe as mailFe,
                        CONCAT(bm.ten, '-', cs.ten) as departmentCampusInfo
                    FROM nhan_vien nv
                    LEFT JOIN bo_mon_theo_co_so bmtcs on nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN bo_mon bm on bmtcs.id_bo_mon = bm.id
                    LEFT JOIN co_so cs on bmtcs.id_co_so = cs.id
                    WHERE cs.ma = :campusCode
                    """,
            nativeQuery = true
    )
    List<StaffExportDTO> findByCampusCode(String campusCode);

}
