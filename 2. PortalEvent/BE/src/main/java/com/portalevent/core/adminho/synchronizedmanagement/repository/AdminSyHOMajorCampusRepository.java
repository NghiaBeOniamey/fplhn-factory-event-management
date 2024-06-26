package com.portalevent.core.adminho.synchronizedmanagement.repository;

import com.portalevent.entity.MajorCampus;
import com.portalevent.repository.MajorCampusRepository;

import java.util.Optional;

public interface AdminSyHOMajorCampusRepository extends MajorCampusRepository {

    Optional<MajorCampus> findByMajorCampusId(Long id);

}
