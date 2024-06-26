package com.portalevent.core.adminho.statisticseventmanagement.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface AdminHOseCategory extends IsIdentified {

    @Value("#{target.name}")
    String getName();

}
