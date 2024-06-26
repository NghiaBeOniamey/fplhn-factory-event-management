package fplhn.udpm.identity.core.feature.student.repository;

import fplhn.udpm.identity.core.feature.student.model.response.CampusResponse;
import fplhn.udpm.identity.core.feature.student.model.response.DepartmentResponse;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentCampusStudentRepository extends DepartmentCampusRepository {

    @Query(
            value = """
                    SELECT
                        bm.id AS id,
                        bm.ten AS name
                    FROM
                     bo_mon bm
                    """
            , nativeQuery = true
    )
    List<DepartmentResponse> getListDepartment();


    @Query(
            value = """
                    SELECT
                        cs.id AS id,
                        cs.ten AS name
                    FROM
                     co_so cs
                    """
            , nativeQuery = true
    )
    List<CampusResponse> getListCampus();

    Optional<DepartmentCampus> findDepartmentCampusByCampus_IdAndDepartment_Id(Long campusId, Long departmentId);

}
