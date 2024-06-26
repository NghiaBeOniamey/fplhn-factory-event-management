package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author HoangDV
 */
@Getter
@Setter
@ToString
public class OedUpdateEventRequest extends OedBaseEventRequest {

    @NotBlank
    private String id;

    private String backgroundPath;

    private String bannerPath;

    private String standeePath;


    private Boolean isHireDesign  = false;


    private Boolean isHireLocation;


    private Boolean isHireDesignBanner;


    private Boolean isHireDesignBg;


    private Boolean isHireDesignStandee;


    private String semesterId;


    private Boolean block;

}
