import { request } from "../../helper/Request.helper";
import { URL_API_PARTICIPANT } from "../ApiUrl";

export class PAHomeApi {

      static fetchAll = () => {
        return request({
          method: "GET",
          url: URL_API_PARTICIPANT + '/get-all',
        });
      };

      static detail = (id) => {
        return request({
          method: "GET",
          url: URL_API_PARTICIPANT + '/event-detail/' + id
        })
      }

      static register = (data) => {
        return request({
          method: "POST",
          url: URL_API_PARTICIPANT + `/register`,
          data: data
        });
      };

      // API lưu ảnh
      static uploadImage = (image) => {
        return request({
          method: "POST",
          url: URL_API_PARTICIPANT + '/upload-image',
          data: image
        })
      }

      static listOrganizer = () => {
        return request({
            method: "GET",
            url: URL_API_PARTICIPANT + '/get-list-organizer'
          })
      }

      static registerEvent = (eventId) => {
        return request({
            method: "POST",
            url: URL_API_PARTICIPANT + '/register-event',
            params:{
                eventId:eventId
            }
          })
      }

    static checkRegisterToParticipate = (eventId) => {
        return request({
            method: "GET",
            url: URL_API_PARTICIPANT + '/check-register-to-participate',
            params:{
                eventId:eventId
            }
        })
    }

      static rollCallEvent = (eventId) => {
        return request({
            method: "POST",
            url: URL_API_PARTICIPANT + '/roll-call',
            params:{
                eventId:eventId
            }
          })
      }

    static checkRollCallEvent = (eventId) => {
        return request({
            method: "GET",
            url: URL_API_PARTICIPANT + '/check-roll-call',
            params:{
                eventId:eventId
            }
        })
    }

      static getCommentByIdEvent = (id, page) => {
        return request({
            method: "GET",
            url: URL_API_PARTICIPANT + `/get-comment/` + id,
            params: {
              pageNumber: page
            }
        });
      };

      static postComment = ({
        eventId,
        comment
      }) => {
        return request({
          method: "POST",
          url: URL_API_PARTICIPANT + `/post-comment`,
          data: {
            eventId,
            comment
          },
        });
      };

    static postReplyComment = ({
        eventId,
        comment,
        replyId
      }) => {
        return request({
          method: "POST",
          url: URL_API_PARTICIPANT + `/reply-comment`,
          data: {
            eventId,
            comment,
            replyId
          },
        });
      };

    static deleteComment = ({commentId}) => {
        return request({
          method: "DELETE",
          url: URL_API_PARTICIPANT + `/delete-comment`,
          data: {
            commentId
          },
        });
    };

    static getEventComingUp = () => {
        return request({
            method: "GET",
            url: URL_API_PARTICIPANT + '/get-event-coming-up'
        })
    }

}