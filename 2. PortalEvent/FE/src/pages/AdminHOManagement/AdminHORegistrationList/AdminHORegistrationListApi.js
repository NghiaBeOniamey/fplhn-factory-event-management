import {request} from "../../../helper/Request.helper";
import {URL_API_ADMIN_HO_MANAGEMENT} from "../../ApiUrl";
import Cookies from "js-cookie";
import {v4 as uuidv4} from "uuid";

export class AdminHORegistrationListApi {
    // lấy ra danh sách người đăng ký
    static fetchAll = (data) => {
        return request({
            method: "GET",
            url: URL_API_ADMIN_HO_MANAGEMENT + `/registration-list`,
            params: data
        });
    };

    // lấy ra danh sách để xuất file excel
    static getListExport = (id, data) => {
        return request({
            method: "GET",
            url: URL_API_ADMIN_HO_MANAGEMENT + `/registration-list/${id}`,
            params: {
                className: data?.className,
                email: data?.email,
                lecturer: data?.lecturer,
                size: 1_000_000,
            }
        });
    };

    static handleExportRegistration = (eventId) => {
        const params = new URLSearchParams({
            idEvent: eventId
        });

        fetch(URL_API_ADMIN_HO_MANAGEMENT + `/registration-list/export-registration?${params.toString()}`, {
            method: "POST",
            headers: {
                'Authorization': 'Bearer ' + Cookies.get('token'),
            },
        })
            .then(response => response.blob())
            .then(blob => {
                let url = window.URL.createObjectURL(blob);
                let a = document.createElement('a');
                a.href = url;
                a.download = uuidv4() + '.xlsx';
                a.click();
            });
    };
}