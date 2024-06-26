import { request } from "../../../helper/Request.helper";
import { URL_API_ADMIN_H_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_ADMIN_H_MANAGEMENT + `/statistic-event/`;

export default class APStatisticsEventApi {

    static getAllSemester = () => {
        return request({
            method: "GET",
            url: api + "get-all-semester",
        });
    };

    static getAllMajor = () => {
        return request({
            method: "GET",
            url: api + "get-all-department",
        });
    };

    static getListOrganizer = (semesterId, idDepartment) => {
        return request({
            method: "GET",
            url: api + "get-list-organizer",
            params: { idSemester: semesterId, idDepartment: idDepartment }
        });
    };

    static getTopEvent = (semesterId, idDepartment) => {
        return request({
            method: "GET",
            url: api + "get-top-event",
            params: { idSemester: semesterId, idDepartment: idDepartment }
        });
    };

    static getEventBySemesterAndOrganizer = (semesterId, idDepartment) => {
        return request({
            method: "GET",
            url: api + "get-all-event",
            params: { idSemester: semesterId, idDepartment: idDepartment }
        });
    };

    static getSumEvent = (semesterId, idDepartment) => {
        return request({
            method: "GET",
            url: api + "get-sum-event",
            params: { idSemester: semesterId, idDepartment: idDepartment }
        });
    };

    static getEventInMajor = (semesterId, idDepartment) => {
        return request({
            method: "GET",
            url: api + "get-event-in-major",
            params: { idSemester: semesterId, idDepartment: idDepartment }
        });
    };

    static getAllCategory = () => {
        return request({
            method: "GET",
            url: api + "get-all-category"
        });
    };

    static getParticipantInEvent = (semesterId, idDepartment) => {
        return request({
            method: "GET",
            url: api + "get-parcitipant-in-evenet",
            params: { idSemester: semesterId, idDepartment: idDepartment }
        });
    }

    static getParticipantInEventByCategory = (semesterId, idDepartment, idCategory) => {
        return request({
            method: "GET",
            url: api + "get-participant-in-event-by-category",
            params: { idSemester: semesterId, idDepartment: idDepartment, idCategory: idCategory }
        });
    }

    static getLecturerInEvent = (semesterId, idDepartment) => {
        return request({
            method: "GET",
            url: api + "get-lecturer-in-event",
            params: { idSemester: semesterId, idDepartment: idDepartment }
        });
    }
}
