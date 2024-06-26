package fplhn.udpm.identity.infrastructure.config.database.repository;

import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.repository.CampusRepository;
import org.springframework.stereotype.Repository;

@Repository("campusTempRepository")
public interface CampusTempRepository extends CampusRepository {

    Campus findByName(String ten);

}
