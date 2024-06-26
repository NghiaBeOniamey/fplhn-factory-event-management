package com.portalevent.core.adminho.synchronizedmanagement.repository;

import com.portalevent.entity.Major;
import com.portalevent.repository.MajorRepository;

import java.util.Optional;

public interface AdminSyHOMajorRepository extends MajorRepository {

    Optional<Major> findByMajorId(Long id);

}
