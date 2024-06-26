import { request } from "../../../helper/Request.helper"
import { URL_API_APRROVER_MANAGEMENT, URL_API_ADMIN_HO_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/subject-event`;

export class APMajorManagementApi {

    static fetchListSubject = (searchName, searchCode, departmentCode, page) => {
        return request({
            method: "GET",
            url: api + "/get-list-subject",
            params: {
                name: searchName,
                code: searchCode,
                departmentCode: departmentCode,
                page: page,
            }
        });
    };

    static synchronizedIdentity = () => {
        return request({
            method: "POST",
            url: URL_API_ADMIN_HO_MANAGEMENT + `/synchronize-management/synchronized-campus-identity`,
        });
    };

}
