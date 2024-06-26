package fplhn.udpm.identity.core.feature.module.repository;

import fplhn.udpm.identity.core.feature.module.model.request.ModulePaginationRequest;
import fplhn.udpm.identity.core.feature.module.model.response.ListModuleResponse;
import fplhn.udpm.identity.core.feature.module.model.response.ModuleResponse;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleExtendRepository extends ModuleRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY md.id DESC) AS orderNumber,
                md.id AS moduleId,
                md.ten AS moduleName,
                md.ma AS moduleCode,
                md.url AS moduleUrl,
                md.redirect_route AS redirectRoute,
                md.xoa_mem AS moduleStatus
            FROM module md
            WHERE
                  (:#{#request.listId} IS NULL
                  OR :#{#request.listId} = '' )
                  OR md.id IN :#{#request.listId}
            """,
            countQuery = """
                    SELECT 
                    	COUNT(md.id) 
                    FROM 
                    	module md
                    WHERE 
                    	( :#{#request.listId} IS NULL 
                    	OR :#{#request.listId} = '' ) 
                    	OR md.id IN :#{#request.listId}
                    """
            , nativeQuery = true)
    Page<ModuleResponse> searchModuleByListId(Pageable pageable, @Param("request") ModulePaginationRequest request);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY md.id DESC) AS orderNumber,
              md.id AS moduleId,
              md.ten AS moduleName,
              md.[url] AS moduleUrl,
              md.ma AS moduleCode,
              md.redirect_route AS redirectRoute,
              md.xoa_mem AS moduleStatus
            FROM module md
            """, countQuery = """
            SELECT 
            	COUNT(md.id) 
            FROM 
            	module md
            """,
            nativeQuery = true)
    Page<ModuleResponse> searchAllModule(Pageable pageable);

    Boolean existsByUrl(String url);

    Boolean existsByUrlAndId(String urlModule, Long id);

    Boolean existsByMa(String ma);

    Boolean existsByTen(String ten);

    @Query(value = """
            SELECT id AS moduleId,
                   ma AS moduleCode,
                   ten AS moduleName,
                   [url] AS moduleUrl
            FROM module
            """,
            nativeQuery = true
    )
    List<ListModuleResponse> getAllModule();
}
