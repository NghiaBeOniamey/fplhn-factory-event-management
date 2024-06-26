package com.portalevent.core.adminho.synchronizedmanagement.repository;

import com.portalevent.entity.Campus;
import com.portalevent.repository.CampusRepository;

import java.util.Optional;

public interface AdminSyHOCampusRepository extends CampusRepository {

    Optional<Campus> findByCampusId(Long id);

}
