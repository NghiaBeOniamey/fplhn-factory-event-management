package com.portalevent.core.adminh.objectmanagement.model.response;

import com.portalevent.entity.Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
/**
 * Class này để chứa dữ liệu bên MySQL để hiển thị list object
 */
@Projection(types = {Object.class})
public interface AdminHObjectManagementListResponse {

    @Value("#{target.indexs}")
    Integer getIndex();

    @Value("#{target.objectId}")
    String getId();

    @Value("#{target.objectName}")
    String getName();

}
