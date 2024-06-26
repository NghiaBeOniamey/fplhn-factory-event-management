package fplhn.udpm.identity.core.feature.campus.repository;

import fplhn.udpm.identity.core.feature.campus.model.request.CampusPaginationRequest;
import fplhn.udpm.identity.core.feature.campus.model.response.CampusResponse;
import fplhn.udpm.identity.core.feature.campus.model.response.MultipleCampusResponse;
import fplhn.udpm.identity.repository.CampusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusExtendRepository extends CampusRepository {

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER(
            	ORDER BY cs.id DESC) AS orderNumber,
            	cs.id as campusId,
            	cs.ten as campusName,
            	cs.ma as campusCode,
            	cs.xoa_mem as campusStatus
            FROM 
            	co_so cs 
            WHERE 
            	( :#{#req.searchValues} IS NULL 
            	OR :#{#req.searchValues} = '' ) 
            	OR cs.id IN :#{#req.searchValues} 
            """, countQuery = """
            SELECT
            	COUNT(cs.id)
            FROM
            	co_so cs
            WHERE 
            	( :#{#req.searchValues} IS NULL 
            	OR :#{#req.searchValues} = '' ) 
            	OR cs.id IN :#{#req.searchValues} 
            """, nativeQuery = true)
    Page<CampusResponse> search(Pageable pageable, @Param("req") CampusPaginationRequest req);

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER( 
            	ORDER BY cs.id DESC) AS orderNumber, 
            	cs.id as campusId, 
            	cs.ten as campusName, 
            	cs.ma as campusCode,
            	cs.xoa_mem as campusStatus 
            FROM 
            	co_so cs 
            """, countQuery = """
            SELECT 
            	COUNT(cs.id) 
            FROM 
            	co_so cs 
            """, nativeQuery = true)
    Page<CampusResponse> search(Pageable pageable);

    Boolean existsByCode(String ma);

    @Query(value = """
            SELECT id as campusId , ten as campusName, ma as campusCode FROM co_so
            """, nativeQuery = true)
    List<MultipleCampusResponse> getListAllCampus();

}
