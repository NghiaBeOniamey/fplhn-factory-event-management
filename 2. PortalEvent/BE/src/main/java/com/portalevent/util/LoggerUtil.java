package com.portalevent.util;

import com.portalevent.infrastructure.constant.ConfigurationsConstant;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.logger.LogService;
import com.portalevent.infrastructure.logger.LoggerObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author todo thangncph26123
 */
@Service
public class LoggerUtil {

    @Autowired
    private LogService logService;

    @Autowired
    private WriteFileCSV writeFileCSV;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * Gửi log và ghi vào các file tương ứng dựa theo request mapping của từng API
     * @param content: nội dung message ghi vào file log
     * @param eventId: ID của sự kiện đối với những màn ghi file liên quan đến thông tin của SK
     */
    public void sendLog(String content, String eventId) {
        try {
            String api = httpServletRequest.getRequestURI();
            String pathFileLogDisplayFunction = null;
            String pathFileLogEvent = null;
            String eventFileName = eventId != null ? eventId + ".csv" : new Date().getTime() + ".csv";
            String pathTemplate = writeFileCSV.getPropertiesRead(ConfigurationsConstant.PATH_FOLDER_TEMPLATE_PORTAL_EVENT);
            if (api.startsWith(Constants.UrlPath.URL_API_APPROVER_MANAGEMENT)) {
                if (api.contains(Constants.UrlPath.URL_API_APPROVER_CATEGORY_MANAGEMENT)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_CATEGORY_MANAGEMENT;
                }
                if (api.contains(Constants.UrlPath.URL_API_APPROVER_OBJECT_MANAGEMENT)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_OBJECT_MANAGEMENT;
                }
                if (api.contains(Constants.UrlPath.URL_API_APPROVER_MAJOR_MANAGEMENT)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_MAJOR_MANAGEMENT;
                }
                if (api.contains(Constants.UrlPath.URL_API_APPROVER_SEMESTER_MANAGEMENT)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_SEMESTER_MANAGEMENT;
                }
                if (api.contains(Constants.UrlPath.URL_API_APPROVER_EVENT_DETAIL)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_EVENT_DETAIL;
                    pathFileLogEvent = pathTemplate + ConfigurationsConstant.PATH_FOLDER_EVENT + eventFileName;
                }
            }

            if (api.startsWith(Constants.UrlPath.URL_API_ORGANIZER_MANAGEMENT)) {
                if (api.contains(Constants.UrlPath.URL_API_ORGANIZER_EVENT_REGISTER)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_EVENT_REGISTER;
                }
                if (api.contains(Constants.UrlPath.URL_API_ORGANIZER_EVENT_DETAIL)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.PATH_FOLDER_EVENT + eventFileName;
                }
                if (api.contains(Constants.UrlPath.URL_API_ADMINISTRATIVE_HIRE_DESIGN_LIST)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_ORGANIZER_HIRE_DESIGN_LIST;
                    pathFileLogEvent = pathTemplate + ConfigurationsConstant.PATH_FOLDER_EVENT + eventFileName;
                }
                if (api.contains(Constants.UrlPath.URL_API_ORGANIZER_MULTIPLE_REGISTER)) {
                    pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.NAME_FILE_ORGANIZER_MULTIPLE_REGISTER;
                    pathFileLogEvent = pathTemplate + ConfigurationsConstant.PATH_FOLDER_EVENT + eventFileName;
                }
            }
            if (api.startsWith(Constants.UrlPath.URL_API_PARTICIPANT)) {
                pathFileLogDisplayFunction = pathTemplate + ConfigurationsConstant.PATH_FOLDER_EVENT + eventFileName;
            }
            if (pathFileLogDisplayFunction != null) {
                LoggerObject loggerObject = writeFileCSV.createLoggerObject(content, pathFileLogDisplayFunction);
                logService.sendLogMessage(loggerObject);
            }
            if (pathFileLogEvent != null) {
                LoggerObject loggerObject = writeFileCSV.createLoggerObject(content, pathFileLogEvent);
                logService.sendLogMessage(loggerObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPathFileForView(String displayName, String eventId) {
        try {
            String pathFile = "";
            String pathTemplate = writeFileCSV.getPropertiesRead(ConfigurationsConstant.PATH_FOLDER_TEMPLATE_PORTAL_EVENT);
            String eventFileName = eventId + ".csv";
            if (Constants.DisplayName.APPROVER_MAJOR_MANAGEMENT.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_MAJOR_MANAGEMENT;
            }
            if (Constants.DisplayName.APPROVER_CATEGORY_MANAGEMENT.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_CATEGORY_MANAGEMENT;
            }
            if (Constants.DisplayName.APPROVER_OBJECT_MANAGEMENT.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_OBJECT_MANAGEMENT;
            }
            if (Constants.DisplayName.APPROVER_SEMESTER_MANAGEMENT.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.NAME_FILE_APPROVER_SEMESTER_MANAGEMENT;
            }
            if (Constants.DisplayName.APPROVER_EVENT_DETAIL.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.PATH_FOLDER_EVENT + eventFileName;
            }

            if (Constants.DisplayName.ORGANIZER_EVENT_REGISTER.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.NAME_FILE_EVENT_REGISTER;
            }
            if (Constants.DisplayName.ORGANIZER_EVENT_DETAIL.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.PATH_FOLDER_EVENT + eventId + ".csv";
            }
            if (Constants.DisplayName.ADMINISTRATIVE_HIRE_DESIGN_LIST.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.NAME_FILE_ORGANIZER_HIRE_DESIGN_LIST;
            }
            if (Constants.DisplayName.ORGANIZER_MULTIPLE_REGISTER.equals(displayName)) {
                pathFile = pathTemplate + ConfigurationsConstant.NAME_FILE_ORGANIZER_MULTIPLE_REGISTER;
            }


//            if (Constants.DisplayName.APPROVER_CATEGORY_MANAGEMENT.equals(displayName)) {
//                pathFile = pathTemplate + ConfigurationsConstant.PATH_FOLDER_PARTICIPANT + eventId + ".csv";
//            }
            return pathFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
