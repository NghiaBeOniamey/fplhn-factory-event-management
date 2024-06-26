import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/semester`;

export class APSemesterApi {
  static fetchAll = (page, searchName) => {
    return request({
      method: "GET",
      url: api + `/list-semester`,
      params: {
        page: page,
        searchName: searchName,
      },
    });
  };

  static create = (data) => {
    return request({
      method: "POST",
      url: api + `/add`,
      data: data,
    });
  };

  static update = (data) => {
    return request({
      method: "PUT",
      url: api + `/update/` + data.id,
      data: data,
    });
  };

  static delete = (id) => {
    return request({
      method: "DELETE",
      url: api + `/delete/` + id,
    });
  };

  static register = (data) => {
    return request({
      method: "POST",
      url: URL_API_APRROVER_MANAGEMENT + `/event-detail`,
      data: data,
    });
  };
}
