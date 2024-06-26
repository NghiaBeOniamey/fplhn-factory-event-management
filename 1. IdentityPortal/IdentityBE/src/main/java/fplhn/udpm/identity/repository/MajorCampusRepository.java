package fplhn.udpm.identity.repository;

import fplhn.udpm.identity.entity.MajorCampus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorCampusRepository extends JpaRepository<MajorCampus, Long> {
}
