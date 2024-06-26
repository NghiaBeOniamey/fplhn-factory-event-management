package com.portalevent.core.adminho.semestermanagement.service.impl;

import com.portalevent.core.adminho.semestermanagement.model.request.AdminHOSemesterRequest;
import com.portalevent.core.adminho.semestermanagement.model.respone.AdminHOewIdSemesterInEventResponse;
import com.portalevent.core.adminho.semestermanagement.model.respone.AdminHOewNameSemesterResponse;
import com.portalevent.core.adminho.semestermanagement.model.respone.AdminHOewSemesterResponse;
import com.portalevent.core.adminho.semestermanagement.repository.AdminHOewSemesterRepository;
import com.portalevent.core.adminho.semestermanagement.service.AdminHOewSemesterService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.Semester;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CompareUtils;
import com.portalevent.util.LoggerUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AdminHOewSemesterServiceImpl implements AdminHOewSemesterService {

    private final AdminHOewSemesterRepository repository;

    private final LoggerUtil loggerUtil;

    private final PortalEventsSession portalEventsSession;

    public AdminHOewSemesterServiceImpl(AdminHOewSemesterRepository repository, LoggerUtil loggerUtil, PortalEventsSession portalEventsSession) {
        this.repository = repository;
        this.loggerUtil = loggerUtil;
        this.portalEventsSession = portalEventsSession;
    }

    @Override
    public List<Semester> getAll1() {
        return repository.findAll();
    }

    @Override
    public PageableObject<AdminHOewSemesterResponse> getListSemester(Integer page, String searchName) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return new PageableObject<>(repository.getList(pageRequest, searchName));
    }

    @Override
    public Semester getOne(String id) {
        return null;
    }

    private void validationSemester(AdminHOSemesterRequest request, String id) {
        request.setName(request.getName().replaceAll("\\s+", " "));
        if (request.getName().length() > 100) {
            throw new RestApiException(Message.SEMESTER_NAME_LESS_THAN_100_CHARACTERS);
        }
        if (request.getStartTime() > request.getEndTime()) {
            throw new RestApiException(Message.INVALID_START_TIME);
        }
        if (request.getStartTimeFirstBlock() > request.getEndTimeFirstBlock()
                || request.getStartTimeFirstBlock() < request.getStartTime()
                || request.getStartTimeFirstBlock() > request.getEndTime()
                || request.getEndTimeFirstBlock() < request.getStartTime()
                || request.getEndTimeFirstBlock() > request.getEndTime()) {
            throw new RestApiException(Message.INVALID_TIME_FIRST_BLOCK);
        }
        if (request.getStartTimeSecondBlock() > request.getEndTimeSecondBlock()
                || request.getStartTimeSecondBlock() < request.getStartTime()
                || request.getStartTimeSecondBlock() > request.getEndTime()
                || request.getEndTimeSecondBlock() < request.getStartTime()
                || request.getEndTimeSecondBlock() > request.getEndTime()) {
            throw new RestApiException(Message.INVALID0_TIME_SECONCD_BLOCK);
        }
        if (request.getStartTimeFirstBlock() > request.getStartTimeSecondBlock()
                || request.getEndTimeFirstBlock() > request.getStartTimeSecondBlock()) {
            throw new RestApiException(Message.INVALID_TIME_FIRST_BLOCK);
        }
        List<Semester> listSemester = repository.findAll();
//        listSemester.forEach(item -> {
//            if (item.getStartTime() < request.getEndTime() && item.getEndTime() > request.getStartTime()) {
//                if (id != null) {
//                    if (!item.getId().equals(id)) {
//                        Message.OVERLAP_SEMESTER_TIME.setMessage("Thời gian diễn ra trùng với học kỳ: " + item.getName());
//                        throw new RestApiException(Message.OVERLAP_SEMESTER_TIME);
//                    }
//                } else {
//                    Message.OVERLAP_SEMESTER_TIME.setMessage("Thời gian diễn ra trùng với học kỳ: " + item.getName());
//                    throw new RestApiException(Message.OVERLAP_SEMESTER_TIME);
//                }
//            }
//        }
//        );
    }

    @Override
    public Semester add(AdminHOSemesterRequest request) {
        request.setName(request.getName().replaceAll("\\s+", " "));
        List<AdminHOewNameSemesterResponse> findName = repository.findNameSemester(request.getName());
        if (!findName.isEmpty()) {
            throw new RestApiException(Message.SEMESTER_NAME_ALREADY_EXIST);
        }
        validationSemester(request, null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startTimeTemp = new Date(request.getStartTime());
        Date endTimeTemp = new Date(request.getEndTime());
        Date endTimeBlock1Temp = new Date(request.getEndTimeFirstBlock());
        //set lại giờ cho đúng
        Date startTime = new Date(startTimeTemp.getYear(), startTimeTemp.getMonth(), startTimeTemp.getDate());
        Date endTime = new Date(endTimeTemp.getYear(), endTimeTemp.getMonth(), endTimeTemp.getDate(),23,59,59);
        Date startTimeBlock1 = startTime;
        Date endTimeBlock1 = new Date(endTimeBlock1Temp.getYear(), endTimeBlock1Temp.getMonth(), endTimeBlock1Temp.getDate(),23,59,59);
        Date endTimeBlock2 = endTime;
        Long startTimeSecondBlock = new Date(endTimeBlock1Temp.getYear(), endTimeBlock1Temp.getMonth(), endTimeBlock1Temp.getDate()+1).getTime();
        StringBuffer message = new StringBuffer();
        message.append("Đã tạo thành công học kỳ: ");
        message.append(request.getName());
        message.append(" với thời gian bắt đầu: ");
        message.append(dateFormat.format(startTime));
        message.append(" và thời gian kết thúc: ");
        message.append(dateFormat.format(endTime));
        message.append(" và thời gian bắt đầu block 1: ");
        message.append(dateFormat.format(startTimeBlock1));
        message.append(" và thời gian kết thúc block 1: ");
        message.append(dateFormat.format(endTimeBlock1));
        message.append(" và thời gian bắt đầu block 2: ");
        message.append(dateFormat.format(new Date(startTimeSecondBlock)));
        message.append(" và thời gian kết thúc block 2: ");
        message.append(dateFormat.format(endTimeBlock2));
        loggerUtil.sendLog(message.toString(), null);
//        Semester s = semester.request(new Semester());
        Semester newSemester = new Semester();
        newSemester.setStartTime(startTime.getTime());
        newSemester.setEndTime(endTime.getTime());
        newSemester.setName(request.getName().trim());
        newSemester.setStartTimeFirstBlock(startTimeBlock1.getTime());
        newSemester.setEndTimeFirstBlock(endTimeBlock1.getTime());
        newSemester.setStartTimeSecondBlock(startTimeSecondBlock);
        newSemester.setEndTimeSecondBlock(endTimeBlock2.getTime());
        return repository.save(newSemester);
    }

    @Override
    public Semester update(AdminHOSemesterRequest request, String id) {
        request.setName(request.getName().replaceAll("\\s+", " "));
        Optional<Semester> findById = repository.findById(id);
        if (findById.isEmpty()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        validationSemester(request, id);
        StringBuffer message = new StringBuffer();
        Integer numberUpdate = 0;
        message.append("Đã sửa thành công ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startTime1 = new Date(findById.get().getStartTime());
        Date endTime1 = new Date(findById.get().getEndTime());
        Date startTimeBlock11 = new Date(findById.get().getStartTimeFirstBlock());
        Date endTimeBlock11 = new Date(findById.get().getEndTimeSecondBlock());
        Date startTimeBlock21 = new Date(findById.get().getStartTimeSecondBlock());
        Date endTimeBlock21 = new Date(findById.get().getEndTimeSecondBlock());

        //set thời gian mới
        Date startTimeTemp = new Date(request.getStartTime());
        Date endTimeTemp = new Date(request.getEndTime());
        Date endTimeBlock1Temp = new Date(request.getEndTimeFirstBlock());

        Date startTime = new Date(startTimeTemp.getYear(), startTimeTemp.getMonth(), startTimeTemp.getDate());
        Date endTime = new Date(endTimeTemp.getYear(), endTimeTemp.getMonth(), endTimeTemp.getDate(),23,59,59);
        Date startTimeBlock1 = startTime;
        Date endTimeBlock1 = new Date(endTimeBlock1Temp.getYear(), endTimeBlock1Temp.getMonth(), endTimeBlock1Temp.getDate(),23,59,59);
        Date startTimeBlock2 = new Date(endTimeBlock1Temp.getYear(), endTimeBlock1Temp.getMonth(), endTimeBlock1Temp.getDate()+1);
        Date endTimeBlock2 = endTime;

        if (!findById.get().getName().equals(request.getName())) {
            message.append(CompareUtils.getMessageProperyChange("tên học kỳ", findById.get().getName(), request.getName(), "chưa có tên học kỳ"));
            numberUpdate++;
        }
        if (!findById.get().getStartTime().equals(request.getStartTime())) {
            message.append(CompareUtils.getMessageProperyChange("thời gian bắt đầu", dateFormat.format(startTime1), dateFormat.format(startTime), "chưa có thời gian bắt đầu"));
            numberUpdate++;
        }
        if (!findById.get().getEndTime().equals(request.getEndTime())) {
            message.append(CompareUtils.getMessageProperyChange("thời gian kết thúc", dateFormat.format(endTime1), dateFormat.format(endTime), "chưa có thời gian kết thúc"));
            numberUpdate++;
        }
        if (!findById.get().getStartTimeFirstBlock().equals(request.getStartTimeFirstBlock())) {
            message.append(CompareUtils.getMessageProperyChange("thời gian bắt đầu Block 1", dateFormat.format(startTimeBlock11), dateFormat.format(startTimeBlock1), "chưa có thời gian bắt đầu Block 1"));
            numberUpdate++;
        }
        if (!findById.get().getEndTimeFirstBlock().equals(request.getEndTimeFirstBlock())) {
            message.append(CompareUtils.getMessageProperyChange("thời gian kết thúc Block 1", dateFormat.format(endTimeBlock1), dateFormat.format(endTimeBlock11), "chưa có thời gian kết thúc Block 1"));
            numberUpdate++;
        }
        if (!findById.get().getStartTimeSecondBlock().equals(request.getStartTimeSecondBlock())) {
            message.append(CompareUtils.getMessageProperyChange("thời gian bắt đầu Block 2", dateFormat.format(startTimeBlock21), dateFormat.format(startTimeBlock2), "chưa có thời gian bắt đầu Block 2"));
            numberUpdate++;
        }
        if (!findById.get().getEndTimeSecondBlock().equals(request.getEndTimeSecondBlock())) {
            message.append(CompareUtils.getMessageProperyChange("thời gian kết thúc Block 1", dateFormat.format(endTimeBlock21), dateFormat.format(endTimeBlock2), "chưa có thời gian kết thúc Block 2"));
            numberUpdate++;
        }
        if (numberUpdate > 0) {
            loggerUtil.sendLog(message.toString(), null);
            Semester se = findById.get();
            se.setName(request.getName());
            se.setStartTime(startTime.getTime());
            se.setEndTime(endTime.getTime());
            se.setStartTimeFirstBlock(startTimeBlock1.getTime());
            se.setEndTimeFirstBlock(endTimeBlock1.getTime());
            se.setStartTimeSecondBlock(startTimeBlock2.getTime());
            se.setEndTimeSecondBlock(endTimeBlock2.getTime());
//            se.setUserCode(portalEventsSession.getCurrentUserCode()); // Người cập nhật học kỳ
            return repository.save(se);
        } else {
            throw new RestApiException(Message.NOTHING_TO_SAVE);
        }
    }

    @Override
    public boolean remove(String id) {
        Optional<Semester> findById = repository.findById(id);
        List<AdminHOewIdSemesterInEventResponse> findId = repository.findIdSemester(id);

        if (findById.isEmpty()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        if (!findId.isEmpty()) {
            throw new RestApiException(Message.SEMESTER_IS_ALREADY_IN_EVENT_EXIT);
        }
        if (findById.isPresent()) {
            Semester semester = findById.get();
            repository.delete(semester);
            StringBuffer message = new StringBuffer();
            message.append("Đã xóa thành công học kỳ ");
            message.append(findById.get().getName());
            loggerUtil.sendLog(message.toString(), null);
            return true;
        }
        return false;
    }


}
