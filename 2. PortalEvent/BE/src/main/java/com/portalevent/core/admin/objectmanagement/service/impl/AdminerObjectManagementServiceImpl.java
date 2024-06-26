package com.portalevent.core.admin.objectmanagement.service.impl;

import com.portalevent.core.admin.objectmanagement.model.request.AdminerObjectManagementCreatRequest;
import com.portalevent.core.admin.objectmanagement.model.request.AdminerObjectManagementListRequest;
import com.portalevent.core.admin.objectmanagement.model.response.AdminerObjectManagementListResponse;
import com.portalevent.core.admin.objectmanagement.repository.AdminerObjectManagementRepository;
import com.portalevent.core.admin.objectmanagement.service.AdminerObjectManagementService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Object;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.util.CompareUtils;
import com.portalevent.util.LoggerUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminerObjectManagementServiceImpl implements AdminerObjectManagementService {

    private final AdminerObjectManagementRepository repository;

    private final LoggerUtil loggerUtil;

    public AdminerObjectManagementServiceImpl(AdminerObjectManagementRepository repository, LoggerUtil loggerUtil) {
        this.repository = repository;
        this.loggerUtil = loggerUtil;
    }

    /**
     * @param request lấy data từ FE để search
     * @return list object and search
     */
    @Override
    public PageableObject<AdminerObjectManagementListResponse> getListObject(AdminerObjectManagementListRequest request) {
        request.setName(request.getName().replaceAll("\\s+", " "));
        return new PageableObject<AdminerObjectManagementListResponse>(repository.getObjectList(PageRequest.of(request.getPage(),
                request.getSize()), request));
    }

    /**
     * @param id lấy ra object theo id
     * @return hiển thị 1 object trong object
     */
    @Override
    public ResponseObject getDetailObject(String id) {
        return new ResponseObject(repository.findById(id));
    }

    /**
     * @param request hứng data từ FE để add object
     * @return thêm object
     */
    @Override
    public Object postObject(@Valid AdminerObjectManagementCreatRequest request) {
        request.setName(request.getName().replaceAll("\\s+", " "));
        if (request.getName().length() >= 100) {
            throw new RestApiException(Message.OBJECT_NAME_LESS_THAN_100_CHARACTERS);
        }
        List<AdminerObjectManagementListResponse> objectListResponses = repository.findNameObject(request.getName());
        if (!objectListResponses.isEmpty()) {
            throw new RestApiException(Message.OBJECT_NAME_ALREADY_EXSIT);
        }
        Object object = new Object();

        object.setName(request.getName());
        StringBuffer message = new StringBuffer();
        message.append("Đã tạo thành công đối tượng ");
        message.append(request.getName() != null ? request.getName().trim() : null);
        loggerUtil.sendLog(message.toString(), null);
        return repository.save(object);
    }

    /**
     * @param id      hứng id từ object
     * @param request hứng data từ FE
     * @return cập nhật object
     */
    @Override
    public Object updateObject(String id, @Valid AdminerObjectManagementCreatRequest request) {
        request.setName(request.getName().replaceAll("\\s+", " "));
        Object object = repository.findById(id).get();
        if (request.getName().length() >= 100) {
            throw new RestApiException(Message.OBJECT_NAME_LESS_THAN_100_CHARACTERS);
        }
        List<AdminerObjectManagementListResponse> objectListResponses = repository.findNameObject(request.getName());
        if (!objectListResponses.isEmpty()) {
            throw new RestApiException(Message.OBJECT_NAME_ALREADY_EXSIT);
        }
        if (object != null) {
            if (!object.getName().equals(request.getName())) {
                loggerUtil.sendLog(CompareUtils.getMessageProperyChange("Đã cập nhật tên đối tượng từ", object.getName(), request.getName(), "Chưa có tên đối tượng"), null);
                object.setName(request.getName() != null ? request.getName().trim() : null);
                return repository.save(object);
            } else {
                throw new RestApiException(Message.NOTHING_TO_SAVE);
            }
        }
        return null;
    }

    /**
     * @param id hứng từ object
     * @return xóa object
     */
    @Override
    public Boolean deleteObject(String id) {
        Object object = repository.findById(id).get();
        if (object != null) {
            repository.delete(object);
            StringBuffer message = new StringBuffer();
            message.append("Đã xóa object ");
            message.append(object.getName());
            loggerUtil.sendLog(message.toString(), null);
            return true;
        }
        return false;
    }

}
