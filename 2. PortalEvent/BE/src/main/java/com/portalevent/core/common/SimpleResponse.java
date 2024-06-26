package com.portalevent.core.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
public class SimpleResponse {

    private String id;

    private String userCode;

    private String name;

    private String userName;

    private String emailFPT;

    private String emailFE;

    private String email;

    private String picture;

    private String userSubjectCode;

    private String userTrainingFacilityCode;

    private Double meetingHour;

}
