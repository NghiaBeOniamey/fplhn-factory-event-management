import { request } from "../../../helper/Request.helper";
import {URL_API_ADMINISTRATIVE, URL_API_ORGANIZER_MANAGEMENT} from "../../ApiUrl";

const api = URL_API_ADMINISTRATIVE + "/hire-design";
export class ORHireDesignListApi {
  static sendMailLocation = (id) => {
    return request({
      method: "POST",
      url: api + `/sendMailLocation`,
      data: {
        id: id,
      },
    });
  };

  static sendMailImages = (id) => {
    return request({
      method: "POST",
      url: api + `/sendMailImages`,
      data: {
        id: id,
      },
    });
  };

  // Get all data và tìm kiếm (Có thể phân trang)
  static fetchAll = (data) => {
    return request({
      method: "POST",
      url: api + `/get-list-hire-design`,
      data: data,
    });
  };
  // End function fetchAll

  //Get all data của bộ môn
  static fetchAllMajor = () => {
    return request({
      method: "GET",
      url: api + `/list-major-hire-design`,
    });
  };
  //END get all data bộ môn

  // Get all địa điểm cần thiết kế
  static fetchListLocationOfHireDesign = (id) => {
    return request({
      method: "GET",
      url: api + `/list-location-by-id/${id}`,
    });
  };
  // END Get all địa điểm cần thiết kế

  // Get image của ảnh cần thiết kế
  static getImageOfHireDesign = (id) => {
    return request({
      method: "GET",
      url: api + `/get-image-by-id/${id}`,
    });
  };
  //END Get image của ảnh cần thiết kế

  // Get one địa điểm theo id
  static fetchLocationById = (id) => {
    return request({
      method: "GET",
      url: api + `/get-hire-design-by-id/` + id,
    });
    //End Get one địa điểm theo id
  };

  //Get all data người tổ chức
  static fetchAllOrganizer = () => {
    return request({
      method: "GET",
      url: api + `/list-organizer-hire-design`,
    });
  };
  //END Get all data người tổ chức

  //Get all data hình thức tổ chức
  static fetchAllFormality = () => {
    return request({
      method: "GET",
      url: api + `/list-formality-hire-design`,
    });
  };
  //END Get all data hình thức tổ chức

  //Thêm địa điểm cần thuê
  static addLocationOfHireDesign = (data) => {
    return request({
      method: "POST",
      url: api + `/add-location-hire-design`,
      data: data,
    });
  };
  //END Thêm địa điểm cần thuê

  //Cập nhật địa điểm cần thuê
  static updateLocationOfHireDesign = (id, data) => {
    return request({
      method: "PUT",
      url: api + `/update-location-hire-design/${id}`,
      data: data,
    });
  };
  //END Cập nhật địa điểm cần thuê

  //Xóa địa điểm cần thuê
  static deleteLocationOfHireDesign = (id, idEvent) => {
    return request({
      method: "DELETE",
      url: api + `/delete-location-hire-design/${id}`,
      params: {
        idEvent: idEvent,
      },
    });
  };
  //END Xóa địa điểm cần thuê

  // API lưu ảnh
  static uploadImage = (data) => {
    return request({
      method: "POST",
      url: api + "/upload-image",
      data: data,
    });
  };
  //END API lưu ảnh

  //DELETE Images Ở đây  cần truyền id về chứ k truyền url về
  static deleteImage = (id) => {
    return request({
      method: "DELETE",
      url: api + "/delete-image/" + id,
    });
  };
}
