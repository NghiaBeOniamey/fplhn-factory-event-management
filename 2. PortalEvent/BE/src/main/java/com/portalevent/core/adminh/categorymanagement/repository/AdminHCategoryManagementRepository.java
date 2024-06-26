package com.portalevent.core.adminh.categorymanagement.repository;

import com.portalevent.core.adminh.categorymanagement.model.request.AdminHCategoryManagementListRequest;
import com.portalevent.core.adminh.categorymanagement.model.response.AdminHCategoryManagementListReponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHCategoryManagementRepository extends CategoryRepository {

    /**
     * @param pageable Phân trang
     * @param req      các trường cần tìm kiếm
     * @return trả ra list data của thể loại và tích hợp tìm kiếm
     */
    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC) as indexs,
                c.id as categoryId,
                c.name as categoryName
            FROM
                category c
            WHERE
                (:#{#req.name} IS NULL OR c.name LIKE :#{'%'+#req.name+'%'} )
            """,
            countQuery = """
                    SELECT
                        COUNT(*)
                                FROM
                                    category c
                                WHERE
                                    (:#{#req.name} IS NULL OR c.name LIKE :#{'%'+#req.name+'%'} )
                    """,
            nativeQuery = true)
    Page<AdminHCategoryManagementListReponse> getCategoryList(Pageable pageable, AdminHCategoryManagementListRequest req);

    @Query(value = """
            SELECT
                ROW_NUMBER() OVER(ORDER BY c.last_modified_date DESC) as indexs,
                c.id as categoryId,
                c.name as categoryName
            FROM
                category c WHERE c.name LIKE ?1
            """, nativeQuery = true)
    List<AdminHCategoryManagementListReponse> getCategoryByName(@Param("name") String categoryName);

}
