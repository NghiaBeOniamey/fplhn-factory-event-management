    import {request} from "../../../helper/Request.helper";
import {URL_API_ADMIN_HO_MANAGEMENT} from "../../ApiUrl";

const api = URL_API_ADMIN_HO_MANAGEMENT + `/statistic-event/`;

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
            url: api + "get-all-major",
        });
    };

    static getMajorByCampusId = (campusId) => {
        return request({
            method: "GET",
            url: api + `get-major-by-id/${campusId}`
        });
    }

    static getAllCampus = () => {
        return request({
            method: "GET",
            url: api + "get-all-campus"
        })
    }

    static getDepartmentByCampusId = (campusId) => {
        return request({
            method: "GET",
            url: api + `get-department/${campusId}`
        });
    }

    static getListOrganizer = (semesterId, campusId, departmentId) => {
        return request({
            method: "GET",
            url: api + "get-list-organizer",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId}
        });
    };

    static getTopEvent = (semesterId, campusId, departmentId) => {
        return request({
            method: "GET",
            url: api + "get-top-event",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId}
        });
    };

    static getEventBySemesterAndOrganizer = (semesterId, campusId, departmentId) => {
        return request({
            method: "GET",
            url: api + "get-all-event",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId}
        });
    };

    static getSumEvent = (semesterId, campusId, departmentId) => {
        return request({
            method: "GET",
            url: api + "get-sum-event",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId}
        });
    };

    static getEventInMajor = (semesterId, campusId, departmentId) => {
        return request({
            method: "GET",
            url: api + "get-event-in-major",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId}
        });
    };

    static getAllCategory = () => {
        return request({
            method: "GET",
            url: api + "get-all-category"
        });
    };

    static getParticipantInEvent = (semesterId, campusId, departmentId) => {
        return request({
            method: "GET",
            url: api + "get-parcitipant-in-evenet",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId}
        });
    }

    static getParticipantInEventByCategory = (semesterId, campusId, departmentId, categoryId) => {
        return request({
            method: "GET",
            url: api + "get-participant-in-event-by-category",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId, idCategory: categoryId}
        });
    }

    static getLecturerInEvent = (semesterId, campusId, departmentId) => {
        return request({
            method: "GET",
            url: api + "get-lecturer-in-event",
            params: {idSemester: semesterId, idCampus: campusId, idDepartment: departmentId}
        });
    }
}
