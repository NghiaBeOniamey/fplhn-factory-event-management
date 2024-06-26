package com.portalevent.core.common;

import com.portalevent.infrastructure.session.PortalEventsSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenFindRequest {

    private String toKen;

    private String currentIdUser;

    private String currentEmail;

    private String currentUserName;

    private String currentName;

    private List<String> currentUserRole;

    private String currentUserCode;

    private String currentMajorCode;

    private String currentSubjectCode; // GV và CNBM

    private String currentTrainingFacilityCode; // TBDTCS và BDTCS Token campusCode

}
