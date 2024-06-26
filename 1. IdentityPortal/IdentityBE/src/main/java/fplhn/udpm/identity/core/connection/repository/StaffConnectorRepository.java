package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.StaffResponse;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffConnectorRepository extends StaffRepository {

    @Query(
            """
                    SELECT s
                    FROM Staff s
                    LEFT JOIN StaffRoleModule srm ON s.id = srm.staff.id
                    LEFT JOIN Role r ON srm.role.id = r.id
                    LEFT JOIN Staff s2 ON s2.id = srm.staff.id
                    LEFT JOIN Department d ON s2.departmentCampus.department.id = d.id
                    LEFT JOIN Campus c ON s2.departmentCampus.campus.id = c.id
                    WHERE r.code = :roleCode
                    AND d.code IN :departmentCode
                    AND c.code = :campusCode
                    """
    )
    List<Staff> findByRoleCodeDepartmentCodesAndCampusCode(String roleCode, List<String> departmentCode, String campusCode);

//    @Query(
//            """
//                    SELECT s
//                    FROM Staff s
//                    LEFT JOIN StaffRoleModule srm ON s.id = srm.staff.id
//                    LEFT JOIN Role r ON srm.role.id = r.id
//                    LEFT JOIN Staff s2 ON s2.id = srm.staff.id
//                    LEFT JOIN Department d ON s2.departmentCampus.department.id = d.id
//                    LEFT JOIN Campus c ON s2.departmentCampus.campus.id = c.id
//                    WHERE r.code = :roleCode
//                    AND c.code = :campusCode
//                    """
//    )
//    List<Staff> findByRoleCodeAndCampusCode(String roleCode, String campusCode);

    @Query(
            """
                    SELECT r
                    FROM Staff s
                    LEFT JOIN StaffRoleModule srm ON s.id = srm.staff.id
                    LEFT JOIN Role r ON srm.role.id = r.id
                    WHERE s.staffCode = :staffCode
                    """
    )
    List<Role> findRoleCodeByStaffId(String staffCode);

    @Query(
            """
                            SELECT s
                            FROM Staff s
                            LEFT JOIN Department d ON s.departmentCampus.department.id = d.id
                            LEFT JOIN Campus c ON s.departmentCampus.campus.id = c.id
                            WHERE
                            s.staffCode = :staffCode
                            AND c.code = :campusCode
                            AND d.code IN :listDepartmentCode
                    """
    )
    Optional<Staff> findByStaffCodeAndDepartmentCodeAndCampusCode(String staffCode, String campusCode, List<String> listDepartmentCode);

    @Query(
            """
                            SELECT s
                            FROM Staff s
                            LEFT JOIN Department d ON s.departmentCampus.department.id = d.id
                            LEFT JOIN Campus c ON s.departmentCampus.campus.id = c.id
                            WHERE
                            s.staffCode = :staffCode
                    """
    )
    Optional<Staff> findByStaffCode(String staffCode);

    @Query(
            value = """
                    SELECT
                        s.id AS id,
                        s.ma_nhan_vien AS staffCode,
                        s.ho_ten AS staffName
                    FROM
                        nhan_vien s
                    LEFT JOIN
                        bo_mon_theo_co_so bmtcs on s.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                        bo_mon bm on bmtcs.id_bo_mon = bm.id
                    LEFT JOIN
                        co_so cs on bmtcs.id_co_so = cs.id
                    WHERE
                        bm.ma IN :listDepartmentCode AND cs.ma = :campusCode
                    """,
            nativeQuery = true
    )
    List<StaffResponse> findByStaffInfo(List<String> listDepartmentCode, String campusCode);

    @Query(
            """
                    SELECT s
                    FROM Staff s
                    LEFT JOIN StaffRoleModule srm ON s.id = srm.staff.id
                    LEFT JOIN Role r ON srm.role.id = r.id
                    LEFT JOIN Staff s2 ON s2.id = srm.staff.id
                    LEFT JOIN Department d ON s2.departmentCampus.department.id = d.id
                    LEFT JOIN Campus c ON s2.departmentCampus.campus.id = c.id
                    WHERE r.code = :roleCode
                    AND c.code = :campusCode
                    """
    )
    List<Staff> findByRoleCodeAndCampusCode(String roleCode, String campusCode);
}
