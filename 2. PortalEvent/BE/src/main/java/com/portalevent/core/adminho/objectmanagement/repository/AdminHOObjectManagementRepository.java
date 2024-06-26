package com.portalevent.core.adminho.objectmanagement.repository;

import com.portalevent.core.adminho.objectmanagement.model.request.AdminHOObjectManagementListRequest;
import com.portalevent.core.adminho.objectmanagement.model.response.AdminHOObjectManagementListResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHOObjectManagementRepository extends ObjectRepository {

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
    Page<AdminHOObjectManagementListResponse> getObjectList(Pageable pageable, AdminHOObjectManagementListRequest req);

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
    List<AdminHOObjectManagementListResponse> findNameObject(@Param("name") String name);
}
