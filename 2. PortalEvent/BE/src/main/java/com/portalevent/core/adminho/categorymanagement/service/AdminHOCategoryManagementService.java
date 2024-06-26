package com.portalevent.core.adminho.categorymanagement.service;

import com.portalevent.core.adminho.categorymanagement.model.request.AdminHOCategoryManagementCreateRequest;
import com.portalevent.core.adminho.categorymanagement.model.request.AdminHOCategoryManagementListRequest;
import com.portalevent.core.adminho.categorymanagement.model.response.AdminHOCategoryManagementListReponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Category;
import jakarta.validation.Valid;

public interface AdminHOCategoryManagementService {

    //hiển thị thể loại có phân trang và tìm kiếm
    PageableObject<AdminHOCategoryManagementListReponse> getListCategory(AdminHOCategoryManagementListRequest request);

    //hiển thị 1 thể loại trong list thể loại
    ResponseObject getDetailCategory(String id);

    //thêm thể loại
    Category postCategory(@Valid AdminHOCategoryManagementCreateRequest request);

    //cập nhật thể loại
    Category updateCategory(String id, @Valid AdminHOCategoryManagementCreateRequest request);

    //xóa thể loại
    Boolean deleteCategory(String id);

}
