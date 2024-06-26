package fplhn.udpm.identity.core.feature.majorcampus.repository;

import fplhn.udpm.identity.core.feature.majorcampus.model.response.MajorResponse;
import fplhn.udpm.identity.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusMajorRepository extends MajorRepository {

    @Query(
            value = """
                    SELECT
                        cn.id as majorId,
                        cn.ten as majorName
                    FROM
                        chuyen_nganh cn
                    """,
            nativeQuery = true
    )
    List<MajorResponse> getAllMajors();

}
