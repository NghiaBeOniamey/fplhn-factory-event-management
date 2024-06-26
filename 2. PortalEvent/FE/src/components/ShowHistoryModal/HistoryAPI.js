import {request} from "../../helper/Request.helper";
import {URL_API_SYSTEM} from "../../pages/ApiUrl";

export class HistoryAPI {

    static dowloadLog = (displayName, eventId) => {
        return request({
          method: "GET",
          url: URL_API_SYSTEM + `/download-history-file`,
          params: {
            displayName: displayName,
            eventId: eventId,
          },
          responseType: "blob",
        });
    };

    static showHistory = (data) => {
        return request({
            method: "POST",
            url: URL_API_SYSTEM + `/show-history`,
            data: data
        });
    };

}