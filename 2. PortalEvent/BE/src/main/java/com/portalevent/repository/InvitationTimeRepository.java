package com.portalevent.repository;

import com.portalevent.entity.InvitationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(InvitationTimeRepository.NAME)
public interface InvitationTimeRepository extends JpaRepository<InvitationTime, String> {
    public static final String NAME = "BaseInvitationTimeRepository";
}
