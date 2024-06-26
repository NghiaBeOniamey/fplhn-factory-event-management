import {request} from "../../../helper/Request.helper";
import {URL_API_APRROVER_MANAGEMENT} from "../../ApiUrl";

const api = URL_API_APRROVER_MANAGEMENT + `/event-detail`;

export class APEventDetailApi {
    static detailWaiting = (idEvent) => {
        return request({
            method: "GET",
            //Muốn thêm Path Variable thì điền trực tiếp ?<<tên biên>=<<value>> đường link của thuộc tính URL
            url: api + `/waiting-approval/detail/` + idEvent,
        });
    };

    static register = (data) => {
        return request({
            method: "POST",
            url: api + `/event-detail`,
            data: data,
        });
    };

    static countEventInTime = (id, startTime, endTime) => {
        return request({
            method: 'GET',
            url: api + '/get-event-in-time',
            params: {
                id: id,
                startTime: startTime,
                endTime: endTime
            }
        });
    };

    static approvalEvent = ({id, reason, status}) => {
        return request({
            method: "PUT",
            url: api + `/approve-event`,
            data: {
                id,
                reason,
                status,
            },
        });
    };

    static getEvidenceByIdEvent = (idEvent) => {
        return request({
            method: "GET",
            url: api + `/get-evidence/` + idEvent,
        });
    };

    static getCommentByIdEvent = (idEvent, pageNumber) => {
        return request({
            method: "GET",
            url: api + `/get-comment/` + idEvent,
            params: {
                pageNumber: pageNumber,
            },
        });
    };

    static postComment = ({eventId, participantId, comment}) => {
        return request({
            method: "POST",
            url: api + `/post-comment`,
            data: {
                eventId,
                participantId,
                comment,
            },
        });
    };

    static postReplyComment = ({eventId, participantId, comment, replyId}) => {
        return request({
            method: "POST",
            url: api + `/reply-comment`,
            data: {
                eventId,
                participantId,
                comment,
                replyId,
            },
        });
    };

    static deleteComment = ({
                                commentId,
                                // userId
                            }) => {
        return request({
            method: "DELETE",
            url: api + `/delete-comment`,
            data: {
                commentId,
                // userId
            },
        });
    };

    static getAgendaItem = (idEvent) => {
        return request({
            method: "GET",
            url: api + `/get-agenda-item/` + idEvent,
        });
    };

    static getResoure = (idEvent) => {
        return request({
            method: "GET",
            url: api + `/get-resource/` + idEvent,
        });
    };

    static getLocation = (idEvent) => {
        return request({
            method: "GET",
            url: api + `/get-location/` + idEvent,
        });
    };

    static getObject = (idEvent) => {
        return request({
            method: "GET",
            url: api + `/get-object/` + idEvent,
        });
    };

    static getMajor = (idEvent) => {
        return request({
            method: "GET",
            url: api + `/get-major/` + idEvent,
        });
    };

    static approverPeriodicEvent = (idEvent) => {
        return request({
            method: "PUT",
            url: api + `/approver-periodic-event?id=` + idEvent,
        });
    };

    static noApproverPeriodicEvent = (idEvent, reason) => {
        return request({
            method: "PUT",
            url: api + `/approver-periodic-event?id=` + idEvent + "&reason=" + reason,
        });
    };

    static getListOrganizerByIdEvent = (idEvent) => {
        return request({
            method: "GET",
            url: api + `/get-list-organizer/` + idEvent,
        });
    };
}
