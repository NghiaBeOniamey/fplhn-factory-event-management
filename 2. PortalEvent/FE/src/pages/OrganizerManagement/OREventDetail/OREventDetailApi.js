import { request } from "../../../helper/Request.helper";
import { URL_API_ORGANIZER_MANAGEMENT, URL_API_SYSTEM } from "../../ApiUrl";
import { v4 as uuidv4 } from "uuid";
import Cookies from "js-cookie";

const api = URL_API_ORGANIZER_MANAGEMENT + `/event-detail`;

export default class OREventDetailApi {
  static eventDetail = (eventId) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/" + eventId,
    });
  };

  static getCategory = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-category",
    });
  };

  static getLocationByIdEvent = (id) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-event-location/" + id,
    });
  };
  static getSemester = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-semester",
    });
  };
  static getBlock = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-block",
    });
  };

  static getMajor = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-major",
    });
  };

  static getDepartmentCampus = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-department-campus",
    });
  };

  static getDepartmentCampusByIdEvent = (idEvent) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/get-department-campus/${idEvent}`,
    });
  };

  static getMajorCampus = (idDepartment) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/get-major-campus-by-idDepartment/${idDepartment}`,
    });
  };

  static getMajorByIdEvent = (eventId) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-major/" + eventId,
    });
  };

  static getMajorCampusByIdEvent = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-major-campus/" +
        eventId,
    });
  };

  static getOrganizersByIdEvent = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-list-organizer/" +
        eventId,
    });
  };

  static checkOrganizerRole = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/check-organizer-role/" +
        eventId,
    });
  };

  static checkOrganizerRoleCoHost = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/check-organizer-role-co-host/" +
        eventId,
    });
  };

  static getAgendasByIdEvent = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-list-agenda/" +
        eventId,
    });
  };

  static addEventLocation = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/add-event-location",
      data: data,
    });
  };

  static getAllResourceByIdEvent = (id) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-resource/" + id,
    });
  };
  static createResource = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/create-resource",
      data: data,
    });
  };
  static updateResource = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-resource",
      data: data,
    });
  };

  static deleteResource = (id) => {
    return request({
      method: "DELETE",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/delete-resource/" + id,
    });
  };

  static getAllObject = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-object",
    });
  };

  static updateStatusEvent = (id) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/update-status-event/" +
        id,
    });
  };

  static getTimeSemester = (startTime, endTime) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-time-semester?startTime=" +
        startTime +
        "&endTime=" +
        endTime,
    });
  };

  static getObjectByIdEvent = (idEvent) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-object/" + idEvent,
    });
  };
  static updateEventLocation = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-event-location",
      data: data,
    });
  };

  static deleteOrganizer = (data) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/delete-event-organizer",
      data: data,
    });
  };

  static deleteEventLocationByIdEvent = (id) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/delete-event-location/" +
        id,
    });
  };

  static getOrganizersNotInEvent = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-list-organizer-not-in-event/" +
        eventId,
    });
  };

  static createEventOrganizer = (data) => {
    return request({
      method: "POST",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/create-event-organizer",
      data: data,
    });
  };

  static updateEventOrganizer = (data) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-event-organizer",
      data: data,
    });
  };

  static deleteAgenda = (id) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/delete-agenda?id=" + id,
    });
  };

  static getCommentByIdEvent = (id, page) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/get-comment/` + id,
      params: {
        pageNumber: page,
      },
    });
  };

  static updateEvent = (event) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-event",
      data: event,
    });
  };

  static postComment = ({ eventId, comment }) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/post-comment`,
      data: {
        eventId,
        comment,
      },
    });
  };

  static importExcelEventOrganizers = (obj) => {
    return request({
      method: "POST",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/import-event-organizer",
      data: obj,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };

  static exportOrganizersInEvent = (idEvent) => {
    fetch(
      URL_API_ORGANIZER_MANAGEMENT +
        `/event-detail/export-event-organizer/${idEvent}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + Cookies.get("token"),
        },
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.blob();
      })
      .then((blob) => {
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement("a");
        a.href = url;
        a.download = uuidv4() + ".xlsx";
        a.click();
      })
      .catch((error) => {
        console.error("Error:", error);
        // Xử lý lỗi tại đây (thông báo cho người dùng hoặc thực hiện hành động khác)
      });
  };

  static exportTemplateOrganizersInEvent = () => {
    fetch(
      URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/export-template-event-organizer",
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + Cookies.get("token"),
        },
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.blob();
      })
      .then((blob) => {
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement("a");
        a.href = url;
        a.download = uuidv4() + ".xlsx";
        a.click();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  static getSystemOption = () => {
    return request({
      method: "GET",
      url: URL_API_SYSTEM + "/get-system-option",
    });
  };

  static checkOrganizerRole = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/check-organizer-role/" +
        eventId,
    });
  };

  static checkOrganizerRoleCoHost = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/check-organizer-role-co-host/" +
        eventId,
    });
  };

  static getAgendasByIdEvent = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-list-agenda/" +
        eventId,
    });
  };

  static addEventLocation = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/add-event-location",
      data: data,
    });
  };

  static getAllResourceByIdEvent = (id) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-resource/" + id,
    });
  };
  static createResource = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/create-resource",
      data: data,
    });
  };
  static updateResource = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-resource",
      data: data,
    });
  };

  static deleteResource = (id) => {
    return request({
      method: "DELETE",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/delete-resource/" + id,
    });
  };

  static getAllObject = () => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-all-object",
    });
  };

  static updateStatusEvent = (id) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/update-status-event/" +
        id,
    });
  };

  static getTimeSemester = (startTime, endTime) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-time-semester?startTime=" +
        startTime +
        "&endTime=" +
        endTime,
    });
  };

  static getObjectByIdEvent = (idEvent) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-object/" + idEvent,
    });
  };
  static updateEventLocation = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-event-location",
      data: data,
    });
  };

  static deleteOrganizer = (data) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/delete-event-organizer",
      data: data,
    });
  };

  static deleteEventLocationByIdEvent = (id) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/delete-event-location/" +
        id,
    });
  };

  static getOrganizersNotInEvent = (eventId) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-list-organizer-not-in-event/" +
        eventId,
    });
  };

  static createEventOrganizer = (data) => {
    return request({
      method: "POST",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/create-event-organizer",
      data: data,
    });
  };

  static importExcelEventOrganizers = (obj) => {
    return request({
      method: "POST",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/import-event-organizer",
      data: obj,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };

  static exportOrganizersInEvent = (idEvent) => {
    fetch(
      URL_API_ORGANIZER_MANAGEMENT +
        `/event-detail/export-event-organizer/${idEvent}`,
      {
        method: "GET",
        headers: {
          Authorization: "Bearer " + Cookies.get("token"),
        },
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.blob();
      })
      .then((blob) => {
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement("a");
        a.href = url;
        a.download = uuidv4() + ".xlsx";
        a.click();
      })
      .catch((error) => {
        console.error("Error:", error);
        // Xử lý lỗi tại đây (thông báo cho người dùng hoặc thực hiện hành động khác)
      });
  };

  static updateEventOrganizer = (data) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-event-organizer",
      data: data,
    });
  };

  static deleteAgenda = (id) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/delete-agenda?id=" + id,
    });
  };

  static getCommentByIdEvent = (id, page) => {
    return request({
      method: "GET",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/get-comment/` + id,
      params: {
        pageNumber: page,
      },
    });
  };

  static updateEvent = (event) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/update-event",
      data: event,
    });
  };

  static postComment = ({ eventId, comment }) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/post-comment`,
      data: {
        eventId,
        comment,
      },
    });
  };

  static postReplyComment = ({ eventId, comment, replyId }) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/reply-comment`,
      data: {
        eventId,
        comment,
        replyId,
      },
    });
  };

  static deleteComment = ({ commentId }) => {
    return request({
      method: "DELETE",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/delete-comment`,
      data: {
        commentId,
      },
    });
  };

  static closeEvent = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/close-event`,
      data: data,
    });
  };

  static saveListAgenda = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + `/event-detail/save_list_agenda`,
      data: data,
    });
  };

  static uploadImage = (data, id) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/upload-image/" + id,
      data: data,
    });
  };

  static deleteImage = (id) => {
    return request({
      method: "DELETE",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/delete-image/" + id,
    });
  };

  static getEvidenceByIdEvent = (idEvent) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-list-evidence/" +
        idEvent,
    });
  };

  static createEvidence = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/create-evidence",
      data: data,
    });
  };

  static updateEvidence = (idEvidence, data) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/update-evidence/" +
        idEvidence,
      data: data,
    });
  };

  static addEvidenceImg = (data, idEvent) => {
    return request({
      method: "POST",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/add-evidence/" + idEvent,
      data: data,
    });
  };

  static deleteEvidence = (idEvidence) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/delete-evidence/" +
        idEvidence,
    });
  };

  static openOrCloseRegister = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/open-register",
      data: data,
    });
  };

  static openOrCloseAttendance = (data) => {
    return request({
      method: "PUT",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/open-attendance",
      data: data,
    });
  };

  static updateNumberParticipant = (data) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/update-number-participant",
      data: data,
    });
  };

  static sendMailEvidence = (data) => {
    return request({
      method: "POST",
      url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/send-mail-evidence",
      data: data,
    });
  };

  static approvePeriodicEvent = (data) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT + "/event-detail/approve-periodic-event",
      data: data,
    });
  };

  static eventReorganization = (idEvent, data) => {
    return request({
      method: "POST",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/event_reorganization/" +
        idEvent,
      data: data,
    });
  };

  static sendMailResource = (idEvent) => {
    return request({
      method: "POST",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/send_mail_resource/" +
        idEvent,
    });
  };

  static getListInvitationTime = (idEvent) => {
    return request({
      method: "GET",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/get-list-invitation-time/" +
        idEvent,
    });
  };

  static saveListInvitationTime = (data) => {
    return request({
      method: "PUT",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        `/event-detail/save_list_invitation_time`,
      data: data,
    });
  };

  static deleteInvitationTime = (idInvitationTime) => {
    return request({
      method: "DELETE",
      url:
        URL_API_ORGANIZER_MANAGEMENT +
        "/event-detail/delete-invitation-time/" +
        idInvitationTime,
    });
  };

  // static getListHoneyCategory = () => {
  //     return request({
  //         method: "GET",
  //         url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/get-honey-category"
  //     })
  // }

  // static sendConversionRequest = (data) => {
  //     return request({
  //         method: "POST",
  //         url: URL_API_ORGANIZER_MANAGEMENT + "/event-detail/send-conversion-request",
  //         data: data,
  //     })
  // }

  static approvalEvent = ({ id, reason, status }) => {
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
}
