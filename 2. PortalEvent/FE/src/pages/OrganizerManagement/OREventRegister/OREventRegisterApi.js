import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

export default class OREventRegisterApi {

  static fetchAll = () => {
    return request({
      method: "GET",
      //Muốn thêm Path Variable thì điền trực tiếp ?<<tên biên>=<<value>> đường link của thuộc tính URL
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail`,
      //Muốn thêm Request Params thì thêm vào thuộc tính params
      //params: {
      //pageIndex : 1
      //}
    });
  };

  static register = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register`,
      data: data,
    });
  };

  static getAllCategory = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/get-all-category`,
    });
  };

  static createCategory = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/create-category`,
      data: data,
    });
  };

  static getSemesters = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/get-all-semesters`,
    });
  };

  static getMajors = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/get-all-majors`,
    });
  };

  static getDepartments = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/get-all-departments`,
    });
  };

  static getDepartmentsByCampus = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/get-all-departments-by-campus`,
    });
  };

  static getMajorsCampus = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/get-all-majors-campus`,
    });
  };
  
  static getObjects = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/get-all-objects`,
    });
  };
}