import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/category-management`;

export class APCategoryApi {
  //load data, tìm kiếm, phân trang thể loại
  static categoryList = (page, searchName) => {
    return request({
      method: "GET",
      url: api + `/list-category-approved`,
      params: {
        name: searchName,
        page: page,
      },
    });
  };
  //END load data, tìm kiếm, phân trang thể loại

  //lấy ra 1 phần tử trong list thể loại
  static detailCategory = (id) => {
    return request({
      method: "GET",
      url: api + `/detail-category/` + id,
    });
  };
  //END lấy ra 1 phần tử trong list thể loại

  //Thêm thể loại
  static addCategory = (data) => {
    return request({
      method: "POST",
      url: api + `/post-category`,
      data: data,
    });
  };
  //END Thêm thể loại

  //Cập nhật thể loại
  static updateCategory = (id, data) => {
    return request({
      method: "PUT",
      url: api + `/update-category/` + id,
      data: data,
    });
  };
  //END cập nhật thể loại

  //Xóa thể loại
  static deleteCategory = (id) => {
    return request({
      method: "DELETE",
      url: api + `/delete-category/` + id,
    });
  };
  //END Xóa thể loại
}
