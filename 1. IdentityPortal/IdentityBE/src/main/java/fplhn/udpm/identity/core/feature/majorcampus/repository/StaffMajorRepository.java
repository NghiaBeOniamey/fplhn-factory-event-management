package fplhn.udpm.identity.core.feature.majorcampus.repository;

import fplhn.udpm.identity.core.feature.majorcampus.model.response.StaffResponse;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffMajorRepository extends StaffRepository {

    @Query(
            value = """
                    SELECT
                    	nv.id as staffId,
                    	nv.ho_ten + ' ' + nv.ma_nhan_vien as staffNameCode
                    FROM
                    	nhan_vien nv
                    """,
            nativeQuery = true
    )
    List<StaffResponse> getAllStaff();

}
