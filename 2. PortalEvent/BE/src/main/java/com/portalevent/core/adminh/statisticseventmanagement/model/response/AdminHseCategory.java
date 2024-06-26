package com.portalevent.core.adminh.statisticseventmanagement.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface AdminHseCategory extends IsIdentified {

    @Value("#{target.name}")
    String getName();

}
