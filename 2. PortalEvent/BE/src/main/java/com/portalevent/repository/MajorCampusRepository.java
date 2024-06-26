package com.portalevent.repository;

import com.portalevent.entity.MajorCampus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(MajorCampusRepository.NAME)
public interface MajorCampusRepository extends JpaRepository<MajorCampus, String> {

    public static final String NAME = "BaseMajorCampusRepository";

}
