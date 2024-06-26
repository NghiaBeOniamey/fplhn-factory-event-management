package com.portalevent.core.organizer.hireDesignList.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * Update images event hire design
 */

@Getter
@Setter
public class OhdlUpdateImageRequest {

    private MultipartFile file;

    private int type;

    private String backgroundPath;

    private String bannerPath;

    private String standeePath;

}
