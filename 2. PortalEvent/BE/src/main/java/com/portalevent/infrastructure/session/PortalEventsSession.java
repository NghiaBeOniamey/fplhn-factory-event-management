package com.portalevent.infrastructure.session;

import com.portalevent.core.common.TokenFindRequest;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface PortalEventsSession {

    String getToken();

    String getCurrentIdUser();

    String getCurrentEmail();

    String getCurrentUserName();

    String getCurrentName();

    List<String> getCurrentUserRole();

    String getCurrentUserCode();

    String getCurrentMajorCode();

    String getCurrentSubjectCode();

    String getCurrentTrainingFacilityCode(); // code cơ sở

    String getCurrentPictureURL();

    TokenFindRequest getData();

}
