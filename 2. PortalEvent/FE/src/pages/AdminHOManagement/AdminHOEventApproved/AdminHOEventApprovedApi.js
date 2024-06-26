import {request} from "../../../helper/Request.helper";
import {URL_API_ADMIN_HO_MANAGEMENT} from "../../ApiUrl";
import Cookies from "js-cookie";

const api = URL_API_ADMIN_HO_MANAGEMENT + `/event-approved`;

export class AdminHOEventApprovedApi {

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

    static fetchAttendanceList = (data, idEvent) => {
        return request({
            method: "POST",
            url: api + `/` + idEvent,
            data: data,
        });
    };

    // static fetchAllParentMajor = () => {
    //     return request({
    //         method: "GET",
    //         url: api + `/parent-major`,
    //     });
    // };

    static handleExport = (code) => {
        fetch(api + `/export?subjectCode=${code}`, {
            method: "GET",
            headers: {
                'Authorization': 'Bearer ' + Cookies.get('token'),
            },
        })
            .then(response => response.blob())
            .then(blob => {
                let url = window.URL.createObjectURL(blob);
                let a = document.createElement('a');
                a.href = url;
                a.download = 'participants.xlsx';
                a.click();
            });
    };

    static handleExportEvent = () => {
        fetch(api + `/export-event`, {
            method: "GET",
            headers: {
                'Authorization': 'Bearer ' + Cookies.get('token'),
            },
        })
            .then(response => response.blob())
            .then(blob => {
                let url = window.URL.createObjectURL(blob);
                let a = document.createElement('a');
                a.href = url;
                a.download = 'events.xlsx';
                a.click();
            });
    };
}