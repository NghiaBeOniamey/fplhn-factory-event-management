package com.portalevent.core.organizer.mutipleregister.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author SonPT
 */

@Getter
@Setter
public class OmrFastRegisterRequest {

    private String name;

    private Long startTime;

    private Long endTime;

    private String categoryId;

    private Short expectedParticipants;

    private Integer eventType;

    private List<String> objectId;

    private List<String> majorId;

    private String semesterId;

    private Boolean blockNumber;

}
