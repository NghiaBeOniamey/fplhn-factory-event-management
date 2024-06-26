import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

export class OREventRegisteredApi {

  static fetchAll = (object) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-registered`,
      params: object,
    });
  };

  static fetchAllCategories = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-registered/get-all-category`,
    });
  };

  static fetchAllSemester = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-registered/get-all-semester`,
    });
  };

  static uploadFile = (object) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-register/upload-file`,
      params: (object)
    });
  }
}