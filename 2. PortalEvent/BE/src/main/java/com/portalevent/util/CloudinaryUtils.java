package com.portalevent.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SonPT
 */

@Component
public class CloudinaryUtils {

    @Autowired
    private Cloudinary cloudinary;

    /**
     *
     * @param file: file ảnh lấy từ controller
     * @param id: id của sự kiện
     * @param type: 0 - upload background, 1 - upload banner, 2 - upload standee
     * @return String - Đường dẫn ảnh được lưu trên cloud
     * 			null - lưu thất bại
     */
    public String uploadImage(MultipartFile file, String id, int type) {
        try {
            String folder = "";
            if (type == 0) {
                folder = "Background/";
            } else if (type == 1) {
                folder = "Banner/";
            } else if (type == 2) {
                folder = "Standee/";
            } else if (type == 3) {
                folder = "Evidence/";
            } else {
                folder = "";
            }
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("public_id", folder + id));
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(Message.UPLOAD_IMAGE_FAIL);
        }
    }

    /**
     * @param type: 0 - cần xóa backround, 1 - cần xóa banner, 2 - cần xóa standee
     * @param id: id của sự kiện có ảnh cần xóa
     * @return true: Xóa thành công
     *      	false: Xóa thất bại
     */
    public boolean deleteImage(int type, String id) {
        try {
            String publicId = "";
            if (type == 0) {
                publicId = "Background/" + id;
            } else if (type == 1) {
                publicId = "Banner/" + id;
            } else if (type == 2) {
                publicId = "Standee/" + id;
            } else if (type == 3) {
                publicId = "Evidence/" + id;
            }
            Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (deleteResult.containsKey("result") && deleteResult.get("result").equals("ok")) {
                return true;
            } else {
                throw new RestApiException(Message.DELETE_IMAGE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(Message.DELETE_IMAGE_FAIL);
        }
    }

    public String publicIdFromURL(String cloudinaryURL) {
        String regex = "/([^/]+)\\.[a-z]{3,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cloudinaryURL);

        // Kiểm tra nếu có kết quả và lấy public_id
        if (matcher.find()) {
            String public_id = matcher.group(1);
            return public_id;
        } else {
            return "";
        }
    }

}