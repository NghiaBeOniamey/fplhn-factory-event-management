import { request } from "../../../helper/Request.helper";
import { URL_API_ADMIN_MANAGEMENT, URL_API_ADMIN_HO_MANAGEMENT } from "../../ApiUrl";

export class ADMajorManagermentApi {
    
    static fetchAllCampus = () => {
        return request({
            method: "GET",
            url: URL_API_ADMIN_MANAGEMENT + `/department-manager/campus-fetch`,
        });
    };

    static fetchAll = (data) => {
        return request({
            method: "GET",
            url: URL_API_ADMIN_MANAGEMENT + `/department-manager`,
            params: {
                value: data?.value
            },
        });
    };

    static synchronizedIdentity = () => {
        return request({
            method: "POST",
            url: URL_API_ADMIN_HO_MANAGEMENT + `/synchronize-management/synchronized-campus-identity`,
        });
    };

}
