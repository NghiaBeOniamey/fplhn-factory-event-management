import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT } from "../../ApiUrl";

const MULTIPLE_REGISTER_API = URL_API_ORGANIZER_MANAGEMENT +  '/multiple-register';

export default class ORMultipleRegisterApi {

    static getAllForCalendar = () => {
        return request({
            method: "GET",
            url: MULTIPLE_REGISTER_API + '/get-all-for-calendar',
        });
    };

    static multipleRegisters = (data) => {
        return request({
            method: "POST",
            url: MULTIPLE_REGISTER_API,
            data: data
        });
    };

    static getAllInfo = () => {
        return request({
            method: "GET",
            url: MULTIPLE_REGISTER_API + '/get-all-info'
        });
    };

    static getDetailEvent = (id) => {
        return request({
            method: "GET",
            url: MULTIPLE_REGISTER_API + '/detail-event/' + id,
        });
    };

}