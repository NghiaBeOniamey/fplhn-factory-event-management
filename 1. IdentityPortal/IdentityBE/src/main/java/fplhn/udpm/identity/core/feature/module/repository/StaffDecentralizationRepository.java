package fplhn.udpm.identity.core.feature.module.repository;

import fplhn.udpm.identity.core.feature.module.model.response.StaffInfoResponse;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffDecentralizationRepository extends StaffRepository {

    @Query(
            value = """
                     SELECT TOP 10
                        nv.ma_nhan_vien AS staffCode,
                        nv.ho_ten AS staffName
                    FROM
                        nhan_vien nv
                    WHERE
                        nv.ma_nhan_vien LIKE CONCAT('%', :staffCode, '%')
                    """,
            nativeQuery = true
    )
    List<StaffInfoResponse> getStaffInfoByStaffCode(String staffCode);

}
