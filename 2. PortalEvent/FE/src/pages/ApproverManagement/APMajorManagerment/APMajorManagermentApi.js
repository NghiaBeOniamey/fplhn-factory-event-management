import { request } from "../../../helper/Request.helper";
import { URL_API_APRROVER_MANAGEMENT } from "../../ApiUrl";

export class APMajorManagermentApi {
  static fetchAll = (data) => {
    return request({
      method: "GET",
      //Muốn thêm Path Variable thì điền trực tiếp ?<<tên biên>=<<value>> đường link của thuộc tính URL
      url: URL_API_APRROVER_MANAGEMENT + `/major-manager`,
      //Muốn thêm Request Params thì thêm vào thuộc tính params
      params: {
        value: data?.value,
        mainMajor: data?.mainMajor,
      },
    });
  };

  // static fetchAllParentMajor = () => {
  //   return request({
  //     method: "GET",
  //     url: URL_API_APRROVER_MANAGEMENT + `/major-manager/parent-major`,
  //   });
  // };

  static getOneMajor = (id) => {
    return request({
      method: "GET",
      url: URL_API_APRROVER_MANAGEMENT + `/major-manager/${id}`,
    });
  };

  static register = (data) => {
    return request({
      method: "POST",
      url: URL_API_APRROVER_MANAGEMENT + `/major-manager`,
      data: data,
    });
  };

  static update = (id, data) => {
    return request({
      method: "PUT",
      url: URL_API_APRROVER_MANAGEMENT + `/major-manager/${id}`,
      data: data,
    });
  };

  static delete = (id) => {
    return request({
      method: "DELETE",
      url: URL_API_APRROVER_MANAGEMENT + `/major-manager/${id}`,
    });
  };

}
