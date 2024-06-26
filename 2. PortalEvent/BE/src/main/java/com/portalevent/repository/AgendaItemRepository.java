package com.portalevent.repository;

import com.portalevent.entity.AgendaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(AgendaItemRepository.NAME)
public interface AgendaItemRepository extends JpaRepository<AgendaItem, String> {
    public static final String NAME = "BaseAgendaItemRepository";

}