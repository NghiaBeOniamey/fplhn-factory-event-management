import { request } from "../../../helper/Request.helper";
import {URL_API_ADMINISTRATIVE} from "../../ApiUrl";

export class OREventInSemesterApi {
    static fetchAll = (data) => {
        return request({
            method: "GET",
            url: URL_API_ADMINISTRATIVE + `/event-in-semester`,
            params: {
                name: data?.name,
                semester: data?.semester,
                organizer: data?.organizer,
                page: data?.page,
                size: data?.size,
            }
        });
    };

    static getAllSemester = () => {
        return request({
            method: "GET",
            url: URL_API_ADMINISTRATIVE + `/event-in-semester/all-semester`,
        });
    };

    static getAllOrganizer = () => {
        return request({
            method: "GET",
            url: URL_API_ADMINISTRATIVE + `/event-in-semester/all-organizer`,
        });
    };

    static getListExport = (data) => {
        return request({
            method: "GET",
            url: URL_API_ADMINISTRATIVE + `/event-in-semester`,
            params: {
                name: data?.name,
                semester: data?.semester,
                organizer: data?.organizer,
                page: data?.page,
                size: data?.size,
            }
        });
    };
}