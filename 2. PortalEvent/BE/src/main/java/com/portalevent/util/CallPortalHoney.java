package com.portalevent.util;//package com.portalevent.util;
//
//import com.portalevent.core.common.ConversionHoneyRequest;
//import com.portalevent.core.common.HoneyCategoryResponse;
//import com.portalevent.core.common.SimpleResponse;
//import com.portalevent.entity.Event;
//import com.portalevent.infrastructure.apiconstant.ApiConstants;
//import com.portalevent.infrastructure.constant.Message;
//import com.portalevent.infrastructure.exeption.rest.RestApiException;
//import com.portalevent.infrastructure.session.PortalEventsSession;
//import com.portalevent.repository.EventRepository;
//import com.portalevent.repository.ParticipantRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author SonPT
// */
//
//@Component
//public class CallPortalHoney {
//
//    @Autowired
//    @Qualifier(ParticipantRepository.NAME)
//    private ParticipantRepository participantRepository;
//
//    @Autowired
//    @Qualifier(EventRepository.NAME)
//    private EventRepository eventRepository;
//
//    @Autowired
//    private CallApiIdentity callApiIdentity;
//
//    @Autowired
//    private PortalEventsSession session;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Value("${domain.portal.honey}")
//    private String domainPortalHoney;
//
//    private static String moduleCode = "MODULE_LAB_PORTAL_EVENTS";
//
//    public Boolean sendConversionHoney(String eventId, Integer numberHoney, String honeyCategoryId) {
//        Optional<Event> event = eventRepository.findById(eventId);
//        if (!event.isPresent()) {
//            throw new RestApiException(Message.EVENT_NOT_EXIST);
//        }
//        List<String> listUserId = participantRepository.getUserIdByEventId(eventId);
//        if (!listUserId.isEmpty()) {
//            List<SimpleResponse> listUser = callApiIdentity.handleCallApiGetListUserByListId(listUserId);
//            if (listUser != null) {
//                if (!listUser.isEmpty()) {
//                    List<ConversionHoneyRequest.User> listUserRequest = new ArrayList<>();
//                    for (SimpleResponse item : listUser) {
//                        ConversionHoneyRequest.User user = new ConversionHoneyRequest.User();
//                        user.setId(item.getUserCode());
//                        user.setEmail(item.getEmailFE());
//                        listUserRequest.add(user);
//                    }
//                    ConversionHoneyRequest conversionHoneyRequest = new ConversionHoneyRequest();
//                    conversionHoneyRequest.setNumberHoney(numberHoney);
//                    conversionHoneyRequest.setCategoryId(honeyCategoryId);
//                    conversionHoneyRequest.setModuleCode(moduleCode);
//                    conversionHoneyRequest.setListUser(listUserRequest);
//                    conversionHoneyRequest.setNameEvent(event.get().getName());
//                    try {
//                        String apiUrl = domainPortalHoney;
//                        HttpHeaders headers = new HttpHeaders();
//                        headers.setContentType(MediaType.APPLICATION_JSON);
//                        String authorizationToken = "Bearer " + session.getToken();
//                        System.out.println(authorizationToken);
//                        headers.set("Authorization", authorizationToken);
//                        HttpEntity<ConversionHoneyRequest> requestEntity = new HttpEntity<>(conversionHoneyRequest, headers);
//
//                        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(domainPortalHoney + ApiConstants.API_SEND_CONVERSION_HONEY, HttpMethod.POST, requestEntity,
//                                new ParameterizedTypeReference<Boolean>() {
//                                });
//                        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                            event.get().setIsConversionHoneyRequest(true);
//                            eventRepository.save(event.get());
//                        }
//                        return true;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return false;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    public List<HoneyCategoryResponse> getHoneyCategory() {
//        String apiUrl = domainPortalHoney + ApiConstants.API_GET_HONEY_CATEGORY;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String authorizationToken = "Bearer " + session.getToken();
//        headers.set("Authorization", authorizationToken);
//        List<HoneyCategoryResponse> response = null;
//		try {
//            ResponseEntity<List<HoneyCategoryResponse>> responseEntity =
//                    restTemplate.exchange(apiUrl, HttpMethod.GET, null,
//                            new ParameterizedTypeReference<List<HoneyCategoryResponse>>() {
//                            });
//
//            response = responseEntity.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//        return response;
//    }
//
//}
