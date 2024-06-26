package com.portalevent.core.system.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.system.model.ShowHistoryRequest;
import com.portalevent.core.system.service.SystemService;
import com.portalevent.entity.SystemOption;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.logger.LoggerObject;
import com.portalevent.util.CallApiConsumer;
import com.portalevent.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SonPT
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_API_HISTORY)
public class SystemController {

    private final LoggerUtil loggerUtil;

    private final CallApiConsumer callApiConsumer;

    private final SystemService service;

    public SystemController(LoggerUtil loggerUtil, CallApiConsumer callApiConsumer, SystemService service) {
        this.loggerUtil = loggerUtil;
        this.callApiConsumer = callApiConsumer;
        this.service = service;
    }

    @PostMapping("/show-history")
    public ResponseEntity<?> showHistory(
                                         @RequestBody ShowHistoryRequest request) {
        String pathFile = loggerUtil.getPathFileForView(request.getDisplayName(), request.getEventId());
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, request.getPage(), request.getSize()), HttpStatus.OK);
    }

    @GetMapping("/download-history-file")
    public ResponseEntity<Resource> downloadCsv(ShowHistoryRequest request) {
        String pathFile = loggerUtil.getPathFileForView(request.getDisplayName(), request.getEventId());
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

	@GetMapping("/get-system-option")
    public ResponseObject getSystemOption() {
        return new ResponseObject(service.getSystemOption());
    }

    @PutMapping("/update-system-option")
    public ResponseObject updateSystemOption (@RequestBody SystemOption systemOption) {
        return new ResponseObject(service.saveSystemOption(systemOption));
    }
}
