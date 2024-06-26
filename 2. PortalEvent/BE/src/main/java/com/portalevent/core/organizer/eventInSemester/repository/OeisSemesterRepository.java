//package com.portalevent.core.organizer.eventInSemester.repository;
//
//import com.portalevent.core.common.TokenFindRequest;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisSemesterResponse;
//import com.portalevent.repository.SemesterRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface OeisSemesterRepository extends SemesterRepository {
//    @Query(value = """
//            SELECT s.id AS value, s.name AS label
//            FROM semester s
//            """, nativeQuery = true)
//    List<OeisSemesterResponse> getAllSemester();
//}
