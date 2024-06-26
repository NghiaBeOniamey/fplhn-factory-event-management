package fplhn.udpm.identity.core.feature.module.repository;

import fplhn.udpm.identity.core.feature.module.model.response.ColumnsRoleProperties;
import fplhn.udpm.identity.core.feature.module.model.response.ModuleRoleStaffResponse;
import fplhn.udpm.identity.entity.StaffRoleModule;
import fplhn.udpm.identity.repository.StaffRoleModuleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffModuleRoleAdminRepository extends StaffRoleModuleRepository {

    @Query(
            value = """
                     SELECT
                        nv.id AS staffId,
                        nv.ma_nhan_vien + ' - ' + nv.ho_ten AS staffInfo,
                        STRING_AGG(cv.ma, ', ') AS listRoleCode
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        [nhan_vien-chuc_vu] nvcv ON nv.id = nvcv.id_nhan_vien AND nvcv.id_modules = :moduleId
                    LEFT JOIN
                        chuc_vu cv ON nvcv.id_chuc_vu = cv.id
                    LEFT JOIN
                        module m ON nvcv.id_modules = m.id
                    WHERE
                        (m.id = :moduleId OR nvcv.id IS NULL)
                        AND (:listStaffCode IS NULL OR :listStaffCode = '' OR nv.ma_nhan_vien IN (
                            SELECT value FROM STRING_SPLIT(:listStaffCode, ',')
                        ))
                    GROUP BY
                        nv.id,
                        nv.ma_nhan_vien,
                        nv.ho_ten
                    """,
            countQuery = """
                    SELECT
                        COUNT(DISTINCT nv.id)
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        [nhan_vien-chuc_vu] nvcv ON nv.id = nvcv.id_nhan_vien AND nvcv.id_modules = :moduleId
                    LEFT JOIN
                        chuc_vu cv ON nvcv.id_chuc_vu = cv.id
                    LEFT JOIN
                        module m ON nvcv.id_modules = m.id
                    WHERE
                        (m.id = :moduleId OR nvcv.id IS NULL)
                        AND (:listStaffCode IS NULL OR :listStaffCode = '' OR nv.ma_nhan_vien IN (
                            SELECT value FROM STRING_SPLIT(:listStaffCode, ',')
                        ))
                    """,
            nativeQuery = true
    )
    Page<List<ModuleRoleStaffResponse>> getStaffRoleModuleByModuleId(Long moduleId, String listStaffCode, Pageable pageable);

    @Query(
            value = """
                     SELECT
                        nv.id AS staffId,
                        nv.ma_nhan_vien + ' - ' + nv.ho_ten AS staffInfo,
                        STRING_AGG(cv.ma, ', ') AS listRoleCode
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        [nhan_vien-chuc_vu] nvcv ON nv.id = nvcv.id_nhan_vien AND nvcv.id_modules = ?1
                    LEFT JOIN
                        chuc_vu cv ON nvcv.id_chuc_vu = cv.id
                    LEFT JOIN
                        module m ON nvcv.id_modules = m.id
                    WHERE
                        (m.id = ?1 OR nvcv.id IS NULL)
                    GROUP BY
                        nv.id,
                        nv.ma_nhan_vien,
                        nv.ho_ten
                    """,
            countQuery = """
                    SELECT
                        COUNT(DISTINCT nv.id)
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        [nhan_vien-chuc_vu] nvcv ON nv.id = nvcv.id_nhan_vien AND nvcv.id_modules = ?1
                    LEFT JOIN
                        chuc_vu cv ON nvcv.id_chuc_vu = cv.id
                    LEFT JOIN
                        module m ON nvcv.id_modules = m.id
                    WHERE
                        (m.id = ?1 OR nvcv.id IS NULL)
                    """,
            nativeQuery = true
    )
    Page<List<ModuleRoleStaffResponse>> getStaffRoleModuleByModuleIdNoSearch(Long moduleId, Pageable pageable);

    @Query(
            value = """
                    SELECT
                        cv.id as id,
                        cv.ten as name,
                        cv.ma as code
                    FROM chuc_vu cv
                    WHERE cv.xoa_mem = 'NOT_DELETED' AND cv.ma != 'SINH_VIEN'
                    """,
            nativeQuery = true
    )
    List<ColumnsRoleProperties> getListRoleAvailable();

//    @Modifying
//    @Query(
//            """
//                    DELETE FROM StaffRoleModule srm
//                    WHERE srm.staff.id = :staffId AND srm.module.id = :moduleId
//                     """
//    )
//    void deleteStaffRoleModuleByStaffIdAndModuleId(Long staffId, Long moduleId);

    @Query("SELECT s FROM StaffRoleModule s WHERE s.staff.id = :staffId AND s.module.id = :moduleId")
    List<StaffRoleModule> findStaffRoleModuleByStaffIdAndModuleId(Long staffId, Long moduleId);

    @Modifying
    @Query(
            """
                    DELETE FROM StaffRoleModule srm
                    WHERE srm.staff.id = :staffId AND srm.module.id = :moduleId AND srm.role.code = :roleMa
                    """
    )
    void deleteByStaffIdAndModuleIdAndRoleMa(Long staffId, Long moduleId, String roleMa);

    @Modifying
    @Query(
            """
                    DELETE FROM StaffRoleModule srm
                    WHERE srm.staff.id = :staffId AND srm.module.id = :moduleId AND srm.role.code IN :rolesToRemove
                    """
    )
    void deleteByStaffIdAndModuleIdAndRoleMaIn(Long staffId, Long moduleId, List<String> rolesToRemove);

    @Modifying
    @Query(
            """
                    DELETE FROM StaffRoleModule srm
                    WHERE srm.staff.id = :staffId AND srm.module.id = :moduleId AND srm.role.code IN :rolesToRemove
                    """
    )
    void deleteByStaffIdAndModuleIdAndRoleCodeIn(Long staffId, Long moduleId, List<String> rolesToRemove);
}
