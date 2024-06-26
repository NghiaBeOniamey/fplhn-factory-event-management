package com.portalevent.core.admin.categorymanagement.model.response;

import com.portalevent.entity.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * interface này hứng data từ SQL để hiển thị lên giao diện
 */
@Projection(types = {Category.class})
public interface AdminCategoryManagementListReponse {

    @Value("#{target.indexs}")
    Integer getIndex();

    @Value("#{target.categoryId}")
    String getId();

    @Value("#{target.categoryName}")
    String getName();

}
