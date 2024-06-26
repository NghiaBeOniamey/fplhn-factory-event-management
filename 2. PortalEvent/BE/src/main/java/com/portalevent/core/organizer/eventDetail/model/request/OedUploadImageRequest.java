package com.portalevent.core.organizer.eventDetail.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class OedUploadImageRequest {

    private MultipartFile file;

    private int type;

}
