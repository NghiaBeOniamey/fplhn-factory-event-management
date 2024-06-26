package com.portalevent.core.admin.categorymanagement.service;

import com.portalevent.core.admin.categorymanagement.model.request.AdminCategoryManagementCreateRequest;
import com.portalevent.core.admin.categorymanagement.model.request.AdminCategoryManagementListRequest;
import com.portalevent.core.admin.categorymanagement.model.response.AdminCategoryManagementListReponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Category;
import jakarta.validation.Valid;

public interface AdminCategoryManagementService {

    //hiển thị thể loại có phân trang và tìm kiếm
    PageableObject<AdminCategoryManagementListReponse> getListCategory(AdminCategoryManagementListRequest request);

    //hiển thị 1 thể loại trong list thể loại
    ResponseObject getDetailCategory(String id);

    //thêm thể loại
    Category postCategory(@Valid AdminCategoryManagementCreateRequest request);

    //cập nhật thể loại
    Category updateCategory(String id, @Valid AdminCategoryManagementCreateRequest request);

    //xóa thể loại
    Boolean deleteCategory(String id);

}
