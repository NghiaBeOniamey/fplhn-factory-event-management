package com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone;

import com.portalevent.entity.Category;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Category.class)
public interface AdminHewEventCategoryResponse extends IsIdentified {
    String getName();
}

