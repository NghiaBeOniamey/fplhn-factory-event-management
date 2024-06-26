package fplhn.udpm.identity.core.feature.staff.repository;

import fplhn.udpm.identity.core.feature.staff.model.request.PaginationStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.response.DetailStaffResponse;
import fplhn.udpm.identity.core.feature.staff.model.response.StaffResponse;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffCustomRepository extends StaffRepository {

    @Query(
            value = """
                    SELECT
                        ROW_NUMBER() OVER (ORDER BY nv.id DESC ) as orderNumber,
                        nv.id as id,
                        nv.ma_nhan_vien as staffCode,
                        nv.ho_ten AS staffName,
                        nv.tai_khoan_fe as accountFe,
                        nv.tai_khoan_fpt as accountFpt,
                        CONCAT(bm.ma, '-', bm.ten) as departmentName,
                        CONCAT(cs.ma, '-', cs.ten) as campusName,
                        nv.so_dien_thoai as phoneNumber,
                        nv.xoa_mem as staffStatus
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        bo_mon_theo_co_so bmtcs on nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                        bo_mon bm on bmtcs.id_bo_mon = bm.id
                    LEFT JOIN
                        co_so cs on cs.id = COALESCE(bmtcs.id_co_so, nv.id_co_so)
                    WHERE
                     (:#{#request.staffName} IS NULL OR nv.ho_ten LIKE CONCAT('%',:#{#request.staffName},'%'))
                        AND (:#{#request.staffCode} IS NULL OR nv.ma_nhan_vien LIKE CONCAT('%',:#{#request.staffCode},'%'))
                        AND (:#{#request.campusCode} IS NULL OR cs.ma = :#{#request.campusCode})
                    GROUP BY
                        nv.id, nv.ma_nhan_vien,
                        nv.ho_ten,
                        nv.tai_khoan_fe,
                        nv.tai_khoan_fpt,
                        bm.ten, nv.so_dien_thoai,
                        nv.xoa_mem, bm.ma, cs.ten, cs.ma
                    """,
            countQuery = """
                    SELECT
                        COUNT(DISTINCT nv.id)
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        bo_mon_theo_co_so bmtcs on nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                        bo_mon bm on bmtcs.id_bo_mon = bm.id
                    LEFT JOIN
                    co_so cs on cs.id = COALESCE(bmtcs.id_co_so, nv.id_co_so)
                    WHERE
                     (:#{#request.staffName} IS NULL OR nv.ho_ten LIKE CONCAT('%',:#{#request.staffName},'%'))
                        AND (:#{#request.staffCode} IS NULL OR nv.ma_nhan_vien LIKE CONCAT('%',:#{#request.staffCode},'%'))
                        AND (:#{#request.campusCode} IS NULL OR cs.ma = :#{#request.campusCode})
                    """,
            nativeQuery = true
    )
    Page<List<StaffResponse>> findAllNhanVien(PaginationStaffRequest request, Pageable pageable);

    Optional<Staff> findByAccountFE(String emailFe);

    Optional<Staff> findByAccountFPT(String emailFpt);

    Optional<Staff> findByStaffCode(String maNhanVien);

    boolean existsByAccountFE(String emailFe);

    boolean existsByAccountFPT(String emailFpt);

    boolean existsByStaffCode(String maNhanVien);

    @Query(
            value = """

                    SELECT
                    	nv.ma_nhan_vien as staffCode,
                    	nv.ho_ten as staffName,
                    	bm.id as departmentId,
                    	cs.id as campusId,
                    	nv.tai_khoan_fpt as emailFpt,
                    	nv.tai_khoan_fe as emailFe,
                    	nv.so_dien_thoai as phoneNumber
                    FROM
                    	nhan_vien nv
                     LEFT JOIN
                    	[nhan_vien-chuc_vu] nvcv on nv.id = nvcv.id_nhan_vien
                    LEFT JOIN
                    	chuc_vu cv on
                    	nvcv.id_chuc_vu = cv.id
                    LEFT JOIN
                    	bo_mon_theo_co_so bmtcs on
                    	nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                    	bo_mon bm ON
                    	bmtcs.id_bo_mon = bm.id
                    LEFT JOIN
                        co_so cs ON
                        cs.id = COALESCE(bmtcs.id_co_so, nv.id_co_so)
                    WHERE
                        nv.id = :id
                    group by nv.ma_nhan_vien, nv.tai_khoan_fe, nv.tai_khoan_fpt, nv.ho_ten, bm.id, cs.id, nv.so_dien_thoai
                    """,
            nativeQuery = true
    )
    DetailStaffResponse findDetailById(Long id);

    @Query(
            value = """
                        SELECT
                        nv.id as id,
                        nv.ma_nhan_vien as maNhanVien,
                        nv.ho_ten AS tenNhanVien,
                        nv.tai_khoan_fe as taiKhoanFE,
                        nv.tai_khoan_fpt as taiKhoanFPT,
                        bm.ten as tenBoMon
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        bo_mon_theo_co_so bmtcs on nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                        bo_mon bm on bmtcs.id_bo_mon = bm.id
                    WHERE nv.xoa_mem = 'NOT_DELETED'
                    GROUP BY
                        nv.id, nv.ma_nhan_vien, nv.tai_khoan_fe, nv.ho_ten, nv.tai_khoan_fpt, bm.ten
                    """,
            nativeQuery = true
    )
    List<StaffResponse> findAllNhanVien();

    Optional<Staff> findByPhoneNumber(String phoneNumber);

}
