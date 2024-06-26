package com.portalevent.core.adminh.majormanagement.repository;//package com.portalevent.core.adminh.majormanagement.repository;
//
//import com.portalevent.core.adminh.majormanagement.model.response.AdminerHMajorResponse;
//import com.portalevent.entity.Major;
//import com.portalevent.repository.MajorRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface AdminerHMajorRepository extends MajorRepository {
//
//    @Query(value = """
//            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) as indexs,
//            m.id AS id,
//            m.code AS code,
//            m.name AS name,
//            m.mail_of_manager AS mailOfManager
//            FROM major m
//            WHERE (m.code LIKE CONCAT('%', :value '%')
//            OR m.name LIKE CONCAT('%', :value, '%')
//            OR m.mail_of_manager LIKE CONCAT('%', :value, '%'))
//            AND m.campus_code = :campusCode
//            """,countQuery = """
//            SELECT COUNT(*) FROM major m
//            WHERE (m.code LIKE CONCAT('%', :value '%')
//            OR m.name LIKE CONCAT('%', :value, '%')
//            OR m.mail_of_manager LIKE CONCAT('%', :value, '%'))
//            AND m.campus_code = :campusCode
//            """, nativeQuery = true)
//    List<AdminerHMajorResponse> getMajorList(@Param("value") String value, String campusCode);
//
//    @Query(value = """
//            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) as indexs,
//            m.id AS id,
//            m.code AS code,
//            m.name AS name,
//            m.mail_of_manager AS mailOfManager
//            FROM major m
//            WHERE m.campus_code = :campusCode
//            """, nativeQuery = true)
//    List<AdminerHMajorResponse> getAllMajors(String campusCode);
//
////    @Query(value = """
////        SELECT COUNT(*) FROM major
////        """, nativeQuery = true)
////    Integer count(@Param("idMajor") String idMajor);
//
//    Optional<Major> findByIdLongAndCampusId(Long idLong, Long campusId);
//
//}
