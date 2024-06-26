package com.portalevent.core.admin.statisticseventmanagement.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface AdminerCategory extends IsIdentified {

    @Value("#{target.name}")
    String getName();

}
