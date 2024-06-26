import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/periodic-event`;
export class APPeriodicEventAPI {
  static getPage = (filter) => {
    return request({
      method: "GET",
      url: api,
      params: filter,
    });
  };

  static getPageEventWaitApprover = (filter) => {
    return request({
      method: "GET",
      url: api + "/wait-approver",
      params: filter,
    });
  };

  static create = (data) => {
    return request({
      method: "POST",
      url: api,
      data: data,
    });
  };

  static update = (data) => {
    return request({
      method: "PUT",
      url: api,
      data: data,
    });
  };

  static getListCategory = () => {
    return request({
      method: "GET",
      url: api + "/list-category",
    });
  };

  static getListObject = () => {
    return request({
      method: "GET",
      url: api + "/list-object",
    });
  };

  static getListMajor = () => {
    return request({
      method: "GET",
      url: api + "/list-major",
    });
  };

  static detail = (id) => {
    return request({
      method: "GET",
      url: api + "/detail?id=" + id,
    });
  };

  static delete = (id) => {
    return request({
      method: "DELETE",
      url: api + "?id=" + id,
    });
  };
}
