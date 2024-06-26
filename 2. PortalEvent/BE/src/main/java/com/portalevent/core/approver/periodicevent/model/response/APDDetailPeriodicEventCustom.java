package com.portalevent.core.approver.periodicevent.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class APDDetailPeriodicEventCustom {

    private String id;

    private String name;

    private Integer eventType;

    private Short expectedParticipants;

    private String categoryId;

    private String description;

    private List<String> listMajor;

    private List<String> listObject;
}
