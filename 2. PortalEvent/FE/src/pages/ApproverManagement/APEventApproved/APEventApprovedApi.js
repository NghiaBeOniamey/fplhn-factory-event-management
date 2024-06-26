import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/event-approved`;
export class APEventApprovedApi {

  static fetchAll = (data) => {
    return request({
      method: "POST",
      url: api + `/list-event-approved`,
      data: data
    });
  };

  static fetchListCategory = () => {
    return request({
      method: "GET",
      url: api + `/event-category/list`,
    });
  };
}