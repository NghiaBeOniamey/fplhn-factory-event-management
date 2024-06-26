import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_ORGANIZER_MANAGEMENT + `/periodic-event`;

export default class ORPeriodicEventAPI {
  static getPage = (filter) => {
    return request({
      method: "GET",
      url: api,
      params: filter,
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

  static registerPeriodicEvent = (idPeriodicEvent) => {
    return request({
      method: "POST",
      url: api + "/register-periodic-event?idPeriodicEvent=" + idPeriodicEvent,
    });
  };
}
