package fplhn.udpm.identity.core.connection.repository;

import fplhn.udpm.identity.core.connection.model.response.StudentResponse;
import fplhn.udpm.identity.core.feature.student.repository.StudentsRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentConnectionRepository extends StudentsRepository {

    @Query(
            value = """
                    SELECT s.id as id,
                           s.ma_sinh_vien as studentCode,
                           s.ho_ten as studentName,
                           s.email_fpt as studentEmail,
                           d.ma as departmentCode,
                           d.ten as departmentName,
                           c.ma as campusCode,
                           c.ten as campusName,
                           s.avatar as picture
                    FROM sinh_vien s
                    LEFT JOIN bo_mon_theo_co_so bmtcs on s.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN bo_mon d on bmtcs.id_bo_mon = d.id
                    LEFT JOIN co_so c on bmtcs.id_co_so = c.id
                    WHERE s.ma_sinh_vien IN :studentCode
                    """,
            nativeQuery = true
    )
    List<StudentResponse> findByStudentCodes(List<String> studentCode);

}
