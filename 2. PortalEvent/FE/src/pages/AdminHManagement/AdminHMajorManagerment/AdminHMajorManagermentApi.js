import {request} from "../../../helper/Request.helper";
import {URL_API_ADMIN_H_MANAGEMENT} from "../../ApiUrl";

export class AdminHMajorManagermentApi {
    static fetchAll = (data) => {
        return request({
            method: "GET",
            url: URL_API_ADMIN_H_MANAGEMENT + `/major-manager`,
            params: {
                value: data?.value
            },
        });
    };

    static fetchMajors = () => {
        return request({
            method: "GET",
            url: URL_API_ADMIN_H_MANAGEMENT + `/major-manager/majors-fetch`,
        });
    };
}
