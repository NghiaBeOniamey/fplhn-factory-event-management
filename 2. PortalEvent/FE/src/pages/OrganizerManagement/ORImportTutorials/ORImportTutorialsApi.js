import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

export default class ORImportTutorialsApi {
  static getSemesters = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/import-tutorials/get-all-semester`,
    });
  };

  static importTutorials = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/import-tutorials`,
      data: data
    });
  };

  static getAllTutorials = (data) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/import-tutorials/get-all-tutorials`,
      data: data
    });
  };

}