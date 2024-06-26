import {request} from "../../../helper/Request.helper";
import {URL_API_ADMIN_MANAGEMENT} from "../../ApiUrl";
import Cookies from "js-cookie";
import {v4 as uuidv4} from "uuid";

const api = URL_API_ADMIN_MANAGEMENT + `/attendance-list`;
export class ADAttendanceListApi {
    static fetchAttendanceList = (data) => {
        return request({
            method: "POST",
            url: api,
            data: data,
        });
    };

    static handleExportAttendance = (eventId) => {
        const params = new URLSearchParams({
            idEvent: eventId
        });

        fetch(api + `/export-attendance?${params.toString()}`, {
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
