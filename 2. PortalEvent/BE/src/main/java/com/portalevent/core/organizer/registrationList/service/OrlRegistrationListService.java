package com.portalevent.core.organizer.registrationList.service;

import com.portalevent.core.adminho.registrationlistmanagement.model.response.AdminHORegistrationResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
import com.portalevent.core.organizer.registrationList.model.response.OrlRegistrationResponse;
import com.portalevent.entity.Event;

import java.util.List;

/**
 * @author SonPT
 */
public interface OrlRegistrationListService {

    Event detail(String id);

    /***
     *
     * @param req
     * @return Danh sách người tham gia
     */
    PageableObject<OrlQuestionResponse> getListResgistration(final OrlFindQuestionsRequest req);

    Integer countAllSearchQuestion(final OrlFindQuestionsRequest req);

    List<OrlRegistrationResponse> getRegistrationList(String idEvent);

}
