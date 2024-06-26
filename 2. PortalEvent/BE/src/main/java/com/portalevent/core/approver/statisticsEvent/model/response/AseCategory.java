package com.portalevent.core.approver.statisticsEvent.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface AseCategory extends IsIdentified {
    @Value("#{target.name}")
    String getName();
}
