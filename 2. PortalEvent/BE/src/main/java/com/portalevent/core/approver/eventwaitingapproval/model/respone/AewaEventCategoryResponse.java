package com.portalevent.core.approver.eventwaitingapproval.model.respone;

import com.portalevent.entity.Category;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Category.class)
public interface AewaEventCategoryResponse extends IsIdentified {
    String getName();
}

