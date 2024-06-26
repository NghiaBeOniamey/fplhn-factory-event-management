import {request} from "../../../helper/Request.helper";
import {URL_API_APRROVER_MANAGEMENT} from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/statistic-event/`;

export default class APStatisticsEventApi {
    static getAllSemester = () => {
        return request({
            method: "GET",
            url: api + "get-all-semester",
        });
    };

    static getListOrganizer = (semesterId) => {
        return request({
            method: "GET",
            url: api + "get-list-organizer/" + semesterId,
        });
    };

    static getTopEvent = (semesterId) => {
        return request({
            method: "GET",
            url: api + "get-top-event/" + semesterId,
        });
    };

    static getEventBySemesterAndOrganizer = (semesterId) => {
        return request({
            method: "GET",
            url: api + "get-all-event/" + semesterId,
        });
    };

    static getSumEvent = (semesterId) => {
        return request({
            method: "GET",
            url: api + "get-sum-event/" + semesterId,
        });
    };

    static getEventInMajor = (semesterId) => {
        return request({
            method: "GET",
            url: api + "get-event-in-major/" + semesterId,
        });
    };

    static getAllCategory = () => {
        return request({
            method: "GET",
            url: api + "get-all-category"
        });
    };

    static getParticipantInEvent = (semesterId) => {
        return request ({
           method: "GET",
           url: api + "get-parcitipant-in-evenet/" + semesterId
        });
    }

    static getParticipantInEventByCategory = (idSemester, idCategory) => {
        return request ({
            method: "GET",
            url: api + "get-participant-in-event-by-category?idSemester=" + idSemester + "&idCategory=" + idCategory
        });
    }

    static getLecturerInEvent = (semesterId) => {
        return request ({
            method: "GET",
            url: api + "get-lecturer-in-event/" + semesterId
        });
    }
}
