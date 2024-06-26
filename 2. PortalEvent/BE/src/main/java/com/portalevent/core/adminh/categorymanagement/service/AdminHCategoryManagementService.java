package com.portalevent.core.adminh.categorymanagement.service;

import com.portalevent.core.adminh.categorymanagement.model.request.AdminHCategoryManagementCreateRequest;
import com.portalevent.core.adminh.categorymanagement.model.request.AdminHCategoryManagementListRequest;
import com.portalevent.core.adminh.categorymanagement.model.response.AdminHCategoryManagementListReponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Category;
import jakarta.validation.Valid;

public interface AdminHCategoryManagementService {

    //hiển thị thể loại có phân trang và tìm kiếm
    PageableObject<AdminHCategoryManagementListReponse> getListCategory(AdminHCategoryManagementListRequest request);

    //hiển thị 1 thể loại trong list thể loại
    ResponseObject getDetailCategory(String id);

    //thêm thể loại
    Category postCategory(@Valid AdminHCategoryManagementCreateRequest request);

    //cập nhật thể loại
    Category updateCategory(String id, @Valid AdminHCategoryManagementCreateRequest request);

    //xóa thể loại
    Boolean deleteCategory(String id);

}
