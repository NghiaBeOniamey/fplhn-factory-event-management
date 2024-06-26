//package com.portalevent.core.organizer.eventInSemester.service;
//
//import com.portalevent.core.common.PageableObject;
//import com.portalevent.core.common.SimpleResponse;
//import com.portalevent.core.organizer.eventInSemester.model.request.OeisEventInSemesterRequest;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisEventInSemesterCustomResponse;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisEventInSemesterResponse;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisOrganizerResponse;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisSemesterResponse;
//import com.portalevent.core.organizer.eventInSemester.repository.OeisEventInSemesterRepository;
//import com.portalevent.core.organizer.eventInSemester.repository.OeisSemesterRepository;
//import com.portalevent.infrastructure.apiconstant.ActorConstants;
//import com.portalevent.infrastructure.session.PortalEventsSession;
//import com.portalevent.util.CallApiIdentity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringJoiner;
//
//@Service
//public class OeisEventInSemesterServiceImpl implements OeisEventInSemesterService {
//
//    private final OeisEventInSemesterRepository repository;
//    private final OeisSemesterRepository semesterRepository;
//    private final CallApiIdentity callApiIdentity;
//    private final PortalEventsSession session;
//
//    public OeisEventInSemesterServiceImpl(OeisEventInSemesterRepository repository, OeisSemesterRepository semesterRepository, CallApiIdentity callApiIdentity, PortalEventsSession session) {
//        this.repository = repository;
//        this.semesterRepository = semesterRepository;
//        this.callApiIdentity = callApiIdentity;
//        this.session = session;
//    }
//
//    @Override
//    public PageableObject<OeisEventInSemesterCustomResponse> getAll(OeisEventInSemesterRequest request) {
////        String regexTrim = "\\s+";
////        request.setName(request.getName().replaceAll(regexTrim, " "));
////        Page<OeisEventInSemesterResponse> page = repository.getAll(request, PageRequest.of(request.getPage(), request.getSize()),session.getData());
////        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV, id);
////        PageableObject<OeisEventInSemesterCustomResponse> custom = new PageableObject<>();
////        List<OeisEventInSemesterCustomResponse> customList = getOeisEventInSemesterCustomResponses(page, simpleResponses);
////        custom.setData(customList);
////        custom.setCurrentPage(custom.getCurrentPage());
////        custom.setTotalPages(page.getTotalPages());
//        return null;
//    }
//
//    private static List<OeisEventInSemesterCustomResponse> getOeisEventInSemesterCustomResponses(Page<OeisEventInSemesterResponse> page, List<SimpleResponse> simpleResponses) {
//        List<OeisEventInSemesterCustomResponse> customList = new ArrayList<>();
//        for (OeisEventInSemesterResponse x : page.getContent()) {
//            OeisEventInSemesterCustomResponse obj = new OeisEventInSemesterCustomResponse();
//            String[] listOrganizer = x.getOrganizer().split(",");
//            StringJoiner organizer = new StringJoiner(", ");
//            for (String o : listOrganizer) {
//                for (SimpleResponse simpleResponse : simpleResponses) {
//                    if (simpleResponse.getId().equals(o)) {
//                        organizer.add(simpleResponse.getUserName());
//                    }
//                }
//            }
//            obj.setOrganizer(organizer.toString());
//            obj.setId(x.getId());
//            obj.setIndex(x.getIndex());
//            obj.setName(x.getName());
//            obj.setStatus(x.getStatus());
//            obj.setCategory(x.getCategory());
//            obj.setObject(x.getObject());
//            if (x.getFormality().contains(",")) {
//                obj.setFormality("ONLINE, OFFLINE");
//            } else {
//                if (x.getFormality().equals("0")) {
//                    obj.setFormality("ONLINE");
//                } else {
//                    obj.setFormality("OFFLINE");
//                }
//            }
//            obj.setExpectedParticipant(x.getExpectedParticipant());
//            obj.setNumberParticipant(x.getNumberParticipant());
//            customList.add(obj);
//        }
//        return customList;
//    }
//
//    @Override
//    public List<OeisSemesterResponse> getAllSemester() {
//        return semesterRepository.getAllSemester();
//    }
//
//    @Override
//    public List<OeisOrganizerResponse> getAllOrganizer() {
//        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV);
//        List<OeisOrganizerResponse> list = new ArrayList<>();
//        for (SimpleResponse x : simpleResponses) {
//            OeisOrganizerResponse response = new OeisOrganizerResponse();
//            response.setValue(x.getUserCode());
//            response.setLabel(x.getName() + " (" + x.getUserName() + ")");
//            list.add(response);
//        }
//        return list;
//    }
//}
