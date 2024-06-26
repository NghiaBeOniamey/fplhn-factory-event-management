package com.portalevent.core.adminh.categorymanagement.controller;

import com.portalevent.core.adminh.categorymanagement.model.request.AdminHCategoryManagementCreateRequest;
import com.portalevent.core.adminh.categorymanagement.model.request.AdminHCategoryManagementListRequest;
import com.portalevent.core.adminh.categorymanagement.service.AdminHCategoryManagementService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_CATEGORY_MANAGEMENT)
public class AdminHCategoryManagementController {

    private final AdminHCategoryManagementService apCategoryService;

    public AdminHCategoryManagementController(AdminHCategoryManagementService apCategoryService) {
        this.apCategoryService = apCategoryService;
    }

    //Hàm lấy ra tất cả thể loại
    @GetMapping("/list-category-approved")
    public PageableObject getListCategory(final AdminHCategoryManagementListRequest request) {
        return apCategoryService.getListCategory(request);
    }
    //END hàm lấy ra tất cả thể loại

    //Hàm lấy ra 1 thể loại trong list thể loại
    @GetMapping("/detail-category/{id}")
    public ResponseObject detailCategory(@PathVariable("id") String id) {
        return apCategoryService.getDetailCategory(id);
    }
    //END hàm lấy ra 1 thể loại trong list thể loại


    //Hàm thêm thể loại
    @PostMapping("/post-category")
    public ResponseObject postCategory(@RequestBody AdminHCategoryManagementCreateRequest request) {
        return new ResponseObject(apCategoryService.postCategory(request));
    }
    //END hàm thêm thể loại

    //Hàm cập nhật thể loại
    @PutMapping("/update-category/{id}")
    public ResponseObject updateCategory(@PathVariable("id") String id, @RequestBody AdminHCategoryManagementCreateRequest request) {
        return new ResponseObject(apCategoryService.updateCategory(id, request));
    }
    //END Hàm cập nhật thể loại


    //Hàm xóa thể loại
    @DeleteMapping("/delete-category/{id}")
    public ResponseObject deleteCategory(@PathVariable("id") String id) {
        return new ResponseObject(apCategoryService.deleteCategory(id));
    }
    //END hàm xóa thể loại


}
