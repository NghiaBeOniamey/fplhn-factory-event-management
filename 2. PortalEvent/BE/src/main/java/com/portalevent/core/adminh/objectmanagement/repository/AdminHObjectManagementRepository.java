package com.portalevent.core.adminh.objectmanagement.repository;

import com.portalevent.core.adminh.objectmanagement.model.request.AdminHObjectManagementListRequest;
import com.portalevent.core.adminh.objectmanagement.model.response.AdminHObjectManagementListResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHObjectManagementRepository extends ObjectRepository {

    /**
     *
     * @param pageable phân trang
     * @param req tìm kiếm object
     * @return list object và tìm kiếm
     */
    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY o.last_modified_date DESC) as indexs,
                o.id as objectId,
                o.name as objectName
            FROM
                object o
            WHERE
                (:#{#req.name} IS NULL OR o.name LIKE :#{'%'+#req.name+'%'})
            """, countQuery = """
            SELECT
                    COUNT(*)
                    FROM
                    object o
                    WHERE
                    (:#{#req.name} IS NULL OR o.name LIKE :#{'%'+#req.name+'%'})
            """, nativeQuery = true)
    Page<AdminHObjectManagementListResponse> getObjectList(Pageable pageable, AdminHObjectManagementListRequest req);

    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY o.last_modified_date DESC) as indexs,
                o.id as objectId,
                o.name as objectName
            FROM
                object o WHERE o.name LIKE ?1
            """, countQuery = """
            SELECT
                    COUNT(*)
                    FROM
                    object o
                    WHERE o.name LIKE ?1
            """, nativeQuery = true)
    List<AdminHObjectManagementListResponse> findNameObject(@Param("name") String name);
}
