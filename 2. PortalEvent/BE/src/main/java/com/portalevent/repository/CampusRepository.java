package com.portalevent.repository;

import com.portalevent.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(CampusRepository.NAME)
public interface CampusRepository extends JpaRepository<Campus, String> {

    public static final String NAME = "BaseCampusRepository";

}
