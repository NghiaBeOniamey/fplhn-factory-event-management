import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

const api = URL_API_ORGANIZER_MANAGEMENT + `/statistic-event/`;

export default class ORStatisticEventApi {
    static getAllSemester = () => {
        return request({
            method: "GET",
            url: api + "get-all-semester",
        });
    };

    static getTotalRole = (semesterId) => {
        return request({
            method: "GET",
            url: api + "get-total-role/" + semesterId,
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
}
