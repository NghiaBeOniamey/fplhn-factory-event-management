package com.portalevent.util;

import com.portalevent.infrastructure.constant.ConfigurationsConstant;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.logger.LoggerObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author todo thangncph26123
 */
@Service
public class WriteFileCSV {

    @Autowired
    private PortalEventsSession session;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public LoggerObject writerLoggerObjectIsNotData(LoggerObject loggerObject) {
        Field[] fields = loggerObject.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(loggerObject) == null) {
                    field.set(loggerObject, ConfigurationsConstant.KHONG_CO_DU_LIEU);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return loggerObject;
    }

    public String switchAuthor(String api) {
        if (api.startsWith(Constants.UrlPath.URL_API_APPROVER_MANAGEMENT)) {
            return "Người phê duyệt";
        }
        if (api.startsWith(Constants.UrlPath.URL_API_ORGANIZER_MANAGEMENT)) {
            return "Người tổ chức";
        }
        return "";
    }

    public LoggerObject createLoggerObject(String content, String pathFile) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String formattedDate = simpleDateFormat.format(new Date());

        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        loggerObject.setContent(content);
        loggerObject.setIp(httpServletRequest.getRemoteAddr());
        loggerObject.setMail(session.getCurrentEmail() == null ? "" : session.getCurrentEmail());
        loggerObject.setCreateDate(formattedDate);
        loggerObject.setMethod(httpServletRequest.getMethod());
        loggerObject.setAuthor(switchAuthor(httpServletRequest.getRequestURI()));
        writerLoggerObjectIsNotData(loggerObject);
        return loggerObject;
    }

    public String getPropertiesRead(String constant) {
        PropertiesReader po = new PropertiesReader();
        return po.getPropertyConfig(constant);
    }

    public static String removeDiacritics(String text) {
        text = text.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A")
                .replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E")
                .replaceAll("[ÌÍỊỈĨ]", "I")
                .replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O")
                .replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U")
                .replaceAll("[ỲÝỴỶỸ]", "Y")
                .replaceAll("[Đ]", "D");
        return text;
    }

//    public String covertFileName(String fileOld) {
//        String chuoiKhongDau = removeDiacritics(fileOld);
//        String chuoiChuThuong = chuoiKhongDau.toLowerCase();
//        String output = chuoiChuThuong.replaceAll("[^a-zA-Z0-9]", "_");
//        String fileNew = output.replace(" ", "_");
//
//        return fileNew;
//    }

    /**
     * Convert tên học kỳ thành tên folder (không có dấu)
     * @param tenHocKy
     * @return
     */
//    public String getPathFileByHocKy(String tenHocKy) {
//        String path = covertFileName(tenHocKy) + "/";
//        return path;
//    }

    /**
     * Lấy đường file dựa theo RequestMapping của các API
     * @param eventId
     * @return
     */
//    public String getPathFileActiveWithApi(String eventId) {
//        String api = httpServletRequest.getRequestURI();
//        String pathFile = "";
//        String pathTemplate = getPropertiesRead(ConfigurationsConstant.PATH_FILE_TEMPLATE_PORTAL_EVENT);
//        if (api.startsWith(Constants.UrlPath.URL_API_APPROVER_MANAGEMENT)) {
//            if (api.contains(Constants.UrlPath.URL_API_APPROVER_CATEGORY_MANAGEMENT)) {
//                pathFile = pathTemplate + getPropertiesRead(ConfigurationsConstant.NAME_FILE_APPROVER_CATEGORY_MANAGEMENT);
//            }
//            if (api.contains(Constants.UrlPath.URL_API_APPROVER_OBJECT_MANAGEMENT)) {
//                pathFile = pathTemplate + getPropertiesRead(ConfigurationsConstant.NAME_FILE_APPROVER_OBJECT_MANAGEMENT);
//            }
//            if (api.contains("/major-manager")) {
//                pathFile = pathTemplate + getPropertiesRead(ConfigurationsConstant.NAME_FILE_APPROVER_MAJOR_MANAGEMENT);
//            }
//            if (api.contains(Constants.UrlPath.URL_API_APPROVER_SEMESTER_MANAGEMENT)) {
//                pathFile = pathTemplate + getPropertiesRead(ConfigurationsConstant.NAME_FILE_APPROVER_SEMESTER_MANAGEMENT);
//            }
//        }
//
//        if (api.startsWith(Constants.UrlPath.URL_API_ORGANIZER_MANAGEMENT)) {
//            if (api.contains("/event-register")) {
//                pathFile = pathTemplate + getPropertiesRead(ConfigurationsConstant.NAME_FILE_EVENT_REGISTER);
//            }
//            if (api.contains(Constants.UrlPath.URL_API_ORGANIZER_EVENT_DETAIL)) {
//				pathFile = pathTemplate + getPropertiesRead(ConfigurationsConstant.PATH_FILE_EVENT) + eventId + ".csv";
//            }
//        }
//        System.out.println("========= pathFile: " + pathFile);
//        return pathFile;
//    }

}
