import {request} from "../../../helper/Request.helper";
import {URL_API_APRROVER_MANAGEMENT} from "../../ApiUrl";
import Cookies from "js-cookie";
import {v4 as uuidv4} from "uuid";

const api = URL_API_APRROVER_MANAGEMENT + `/event-approval`;

export class APEventWaitingApprovalApi {


    static fetchListCategory = () => {
        return request({
            method: "GET",
            url: api + `/event-category/list`,
        });
    };

    static fetchListEventMajor = () => {
        return request({
            method: "GET",
            url: api + `/event-major/list`,
        });
    };

    static fetchListEventWaiting = (data) => {
        return request({
            method: "POST",
            url: api + `/list-event-waiting-approve`,
            data: data
        });
    };

    static detail = (idEvent) => {
        return request({
            method: "GET",
            //Muốn thêm Path Variable thì điền trực tiếp ?<<tên biên>=<<value>> đường link của thuộc tính URL
            url: api + `/detail/` + idEvent,
            //Muốn thêm Request Params thì thêm vào thuộc tính params
            //params: {
            //pageIndex : 1
            //}
        });
    };

    static register = (data) => {
        return request({
            method: "POST",
            url: URL_API_APRROVER_MANAGEMENT + `/event-detail`,
            data: data,
        });
    };

    static fetchAllSemester = () => {
        return request({
            method: "GET",
            url: api + `/semester`,
        });
    };

    static handleExportParticipants = (semesterName) => {
        fetch(api + `/export/participants?semesterName=${semesterName}`, {
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
                a.download = uuidv4() + '.xlsx';
                a.click();
            });
    };

    static handleExportParticipantsLecturer = (semesterName) => {
        fetch(api + `/export/participants-lecturer?semesterName=${semesterName}`, {
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
                a.download = uuidv4() + '.xlsx';
                a.click();
            });
    };

    static handleExportEvent = (semesterId) => {
        const params = new URLSearchParams({
            idSemester: semesterId
        });

        fetch(api + `/export-event?${params.toString()}`, {
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